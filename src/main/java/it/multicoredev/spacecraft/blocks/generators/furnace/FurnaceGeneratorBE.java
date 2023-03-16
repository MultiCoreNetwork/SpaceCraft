package it.multicoredev.spacecraft.blocks.generators.furnace;

import it.multicoredev.spacecraft.setup.config.FurnaceGeneratorConfig;
import it.multicoredev.spacecraft.setup.registries.ModRegistry;
import it.multicoredev.spacecraft.utils.EnergyUtil;
import it.multicoredev.spacecraft.utils.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

/**
 * BSD 3-Clause License
 * <p>
 * Copyright (c) 2023, Lorenzo Magni
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * <p>
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * <p>
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * <p>
 * 3. Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class FurnaceGeneratorBE extends BlockEntity {
    private final ItemStackHandler itemHandler = createItemHandler();
    private final LazyOptional<ItemStackHandler> items = LazyOptional.of(() -> itemHandler);

    private final ModEnergyStorage energyStorage = createEnergyStorage();
    private final LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);

    private int burnTime;
    private int maxBurnTime;

    public FurnaceGeneratorBE(BlockPos pos, BlockState state) {
        super(ModRegistry.FURNACE_GENERATOR_BE.get(), pos, state);
    }

    public int getBurnTime() {
        return burnTime;
    }

    public int getMaxBurnTime() {
        return maxBurnTime;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        items.invalidate();
        energy.invalidate();
    }

    public void tickServer() {
        if (burnTime < maxBurnTime) {
            energyStorage.addEnergy(FurnaceGeneratorConfig.GENERATION.get());
            burnTime += FurnaceGeneratorConfig.GENERATION.get();
            setChanged();
        }

        if (burnTime >= maxBurnTime) {
            ItemStack stack = itemHandler.getStackInSlot(0);
            int newBurnTime = ForgeHooks.getBurnTime(stack, RecipeType.SMELTING);

            if (newBurnTime > 0) {
                itemHandler.extractItem(0, 1, false);
                maxBurnTime = newBurnTime;
            } else {
                maxBurnTime = 0;
            }

            burnTime = 0;
            setChanged();
        }

        BlockState state = level.getBlockState(worldPosition);
        if (state.getValue(BlockStateProperties.POWERED) != burnTime < maxBurnTime) {
            level.setBlock(worldPosition, state.setValue(BlockStateProperties.POWERED, burnTime < maxBurnTime), Block.UPDATE_ALL);
        }

        EnergyUtil.sendEnergy(this, energyStorage);
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("Inventory")) itemHandler.deserializeNBT(tag.getCompound("Inventory"));
        if (tag.contains("Energy")) energyStorage.deserializeNBT(tag.get("Energy"));
        if (tag.contains("Info")) {
            CompoundTag infoTag = tag.getCompound("Info");
            burnTime = infoTag.getInt("BurnTime");
            maxBurnTime = infoTag.getInt("MaxBurnTime");
        }

        super.load(tag);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.put("Inventory", itemHandler.serializeNBT());
        tag.put("Energy", energyStorage.serializeNBT());

        CompoundTag infoTag = new CompoundTag();
        infoTag.putInt("BurnTime", burnTime);
        infoTag.putInt("MaxBurnTime", maxBurnTime);
        tag.put("Info", infoTag);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) return items.cast();
        if (cap == ForgeCapabilities.ENERGY) return energy.cast();

        return super.getCapability(cap, side);
    }

    private ModEnergyStorage createEnergyStorage() {
        return new ModEnergyStorage(FurnaceGeneratorConfig.CAPACITY.get(), FurnaceGeneratorConfig.MAX_TRANSFER.get()) {
            @Override
            protected void onEnergyChanged() {
                setChanged();
            }
        };
    }

    private ItemStackHandler createItemHandler() {
        return new ItemStackHandler(1) {

            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) <= 0) return stack;
                return super.insertItem(slot, stack, simulate);
            }
        };
    }
}

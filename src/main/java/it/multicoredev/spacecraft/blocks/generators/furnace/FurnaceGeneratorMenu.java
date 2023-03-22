package it.multicoredev.spacecraft.blocks.generators.furnace;

import it.multicoredev.spacecraft.setup.registries.ModRegistry;
import it.multicoredev.spacecraft.utils.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

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
public class FurnaceGeneratorMenu extends AbstractContainerMenu {
    private final BlockEntity blockEntity;
    private final Player player;
    private final IItemHandler playerInventory;

    public FurnaceGeneratorMenu(int windowId, BlockPos pos, Inventory playerInventory, Player player) {
        super(ModRegistry.FURNACE_GENERATOR_MENU.get(), windowId);

        this.blockEntity = player.getCommandSenderWorld().getBlockEntity(pos);
        this.player = player;
        this.playerInventory = new InvWrapper(playerInventory);

        if (blockEntity != null) {
            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> addSlot(new SlotItemHandler(h, 0, 26, 26)));
        }

        layoutPlayerInventorySlots(8, 84);
        trackData();
    }

    private void trackData() {
        // Energy
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getEnergy() & 0xffff;
            }

            @Override
            public void set(int value) {
                blockEntity.getCapability(ForgeCapabilities.ENERGY).ifPresent(es -> {
                    int energyStored = es.getEnergyStored() & 0xffff0000;
                    ((ModEnergyStorage) es).setEnergy(energyStored + (value & 0xffff));
                });
            }
        });

        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (getEnergy() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                blockEntity.getCapability(ForgeCapabilities.ENERGY).ifPresent(es -> {
                    int energyStored = es.getEnergyStored() & 0x0000ffff;
                    ((ModEnergyStorage) es).setEnergy(energyStored | (value << 16));
                });
            }
        });

        // Max Energy
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getMaxEnergy() & 0xffff;
            }

            @Override
            public void set(int value) {
            }
        });

        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (getMaxEnergy() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
            }
        });

        // BurnTime
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getBurnTime() & 0xffff;
            }

            @Override
            public void set(int value) {
            }
        });

        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (getBurnTime() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
            }
        });

        // MaxBurnTime
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getMaxBurnTime() & 0xffff;
            }

            @Override
            public void set(int value) {
            }
        });

        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (getMaxBurnTime() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
            }
        });
    }

    public int getEnergy() {
        return blockEntity.getCapability(ForgeCapabilities.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }

    public int getMaxEnergy() {
        return blockEntity.getCapability(ForgeCapabilities.ENERGY).map(IEnergyStorage::getMaxEnergyStored).orElse(0);
    }

    public boolean isLit() {
        return blockEntity.getBlockState().getValue(BlockStateProperties.LIT);
    }

    public int getBurnTime() {
        return ((FurnaceGeneratorBE) blockEntity).getBurnTime();
    }

    public int getMaxBurnTime() {
        return ((FurnaceGeneratorBE) blockEntity).getMaxBurnTime();
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), this.player, ModRegistry.FURNACE_GENERATOR.getBlock());
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack is = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            is = stack.copy();

            if (index == 0) {
                if (!this.moveItemStackTo(stack, 1, 37, true)) return ItemStack.EMPTY;
                slot.onQuickCraft(stack, is);
            } else {
                if (ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0) {
                    if (!this.moveItemStackTo(stack, 0, 1, false)) return ItemStack.EMPTY;
                } else if (index < 28) {
                    if (!this.moveItemStackTo(stack, 28, 37, false)) return ItemStack.EMPTY;
                } else if (index < 37 && !this.moveItemStackTo(stack, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();

            if (stack.getCount() == is.getCount()) return ItemStack.EMPTY;

            slot.onTake(playerIn, stack);
        }

        return is;
    }


    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }

        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }

        return index;
    }

    private void layoutPlayerInventorySlots(int xPosition, int yPosition) {
        // Player inventory
        addSlotBox(playerInventory, 9, xPosition, yPosition, 9, 18, 3, 18);

        // Hotbar
        yPosition += 58;
        addSlotRange(playerInventory, 0, xPosition, yPosition, 9, 18);
    }
}

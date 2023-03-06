package it.multicoredev.spacecraft.blocks.generators.creative;

import it.multicoredev.spacecraft.SpaceCraft;
import it.multicoredev.spacecraft.data.WirelessEnergyStorage;
import it.multicoredev.spacecraft.setup.registries.ModRegistry;
import it.multicoredev.spacecraft.utils.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
public class CreativeGeneratorBE extends BlockEntity {
    private final ModEnergyStorage energyStorage = new ModEnergyStorage(4096, 512);
    private final LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);

    public CreativeGeneratorBE(BlockPos pos, BlockState state) {
        super(ModRegistry.CREATIVE_GENERATOR_BE.get(), pos, state);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        energy.invalidate();
    }

    public void tickServer() {
        energyStorage.addEnergy(4);
        setChanged();
        sendEnergy();
    }

    private void sendEnergy() {
        AtomicInteger capacity = new AtomicInteger(energyStorage.getEnergyStored());
        AtomicInteger energyTransfer = new AtomicInteger(energyStorage.getMaxTransfer());

        List<BlockEntity> users = WirelessEnergyStorage.get().getUsers();
        Collections.shuffle(users);

        if (users.isEmpty()) return;

        int index = 0;

        while (capacity.get() > 0 && energyTransfer.get() > 0) {
            if (index >= users.size()) index = 0;

            BlockEntity be = users.get(index);
            if (be == null) {
                index++;
                continue;
            }

            try {
                sendEnergy(be, capacity, energyTransfer);
            } catch (Exception e) {
                SpaceCraft.LOGGER.error("Failed to send energy", e);
            }

            index++;
        }
    }

    private void sendEnergy(BlockEntity be, AtomicInteger capacity, AtomicInteger energyTransfer) {
        for (Direction side : Direction.values()) {
            boolean doContinue = be.getCapability(ForgeCapabilities.ENERGY, side).map(handler -> {
                        if (handler.canReceive()) {
                            int received = handler.receiveEnergy(Math.min(capacity.get(), energyTransfer.get()), false);
                            capacity.addAndGet(-received);
                            energyTransfer.addAndGet(-received);
                            energyStorage.consumeEnergy(received);
                            setChanged();
                            return false;
                        } else {
                            return true;
                        }
                    }
            ).orElse(true);

            if (!doContinue) break;
        }
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("Energy")) energyStorage.deserializeNBT(tag.getCompound("Energy"));

        super.load(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("Energy", energyStorage.serializeNBT());
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) return energy.cast();
        return super.getCapability(cap, side);
    }
}

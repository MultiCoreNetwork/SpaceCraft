package it.multicoredev.spacecraft.utils;

import it.multicoredev.spacecraft.SpaceCraft;
import it.multicoredev.spacecraft.data.WirelessEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * BSD 3-Clause License
 * <p>
 * Copyright (c) 2023, Lorenzo Magni, Kevin Delugan, Isaia Tonini, Valerio Collura
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
public class EnergyUtil {

    public static BlockEntity canReceiveEnergy(BlockState state, LevelAccessor level, BlockPos pos) {
        if (!state.hasBlockEntity()) return null;

        BlockEntity be = level.getBlockEntity(pos);
        if (be == null) return null;
        if (!be.getCapability(ForgeCapabilities.ENERGY).isPresent()) return null;

        AtomicBoolean canReceive = new AtomicBoolean(false);

        for (Direction side : Direction.values()) {
            be.getCapability(ForgeCapabilities.ENERGY, side).map(handler -> {
                if (handler.canReceive()) canReceive.set(true);
                return true;
            });

            if (canReceive.get()) break;
        }

        if (canReceive.get()) return be;
        return null;
    }

    public static void sendEnergy(BlockEntity generator, ModEnergyStorage energyStorage) {
        /*AtomicInteger capacity = new AtomicInteger(energyStorage.getEnergyStored());
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
                sendEnergy(generator, energyStorage, be, capacity, energyTransfer);
            } catch (Exception e) {
                SpaceCraft.LOGGER.error("Failed to send energy", e);
            }

            index++;
        }*/
    }

    private static void sendEnergy(BlockEntity generator, ModEnergyStorage energyStorage, BlockEntity be, AtomicInteger capacity, AtomicInteger energyTransfer) {
        for (Direction side : Direction.values()) {
            boolean doContinue = be.getCapability(ForgeCapabilities.ENERGY, side).map(handler -> {
                        if (handler.canReceive()) {
                            int received = handler.receiveEnergy(Math.min(capacity.get(), energyTransfer.get()), false);
                            capacity.addAndGet(-received);
                            energyTransfer.addAndGet(-received);
                            energyStorage.consumeEnergy(received);
                            generator.setChanged();
                            return false;
                        } else {
                            return true;
                        }
                    }
            ).orElse(true);

            if (!doContinue) break;
        }
    }

}

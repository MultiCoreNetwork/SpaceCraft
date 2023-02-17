package it.multicoredev.spacecraft.data;

import it.multicoredev.spacecraft.utils.EnergyUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
public class WirelessEnergyStorage {
    private static WirelessEnergyStorage instance;
    private static Random rand = new Random();
    private final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(64);
    private Map<BlockPos, BlockEntity> energyUsers = new HashMap<>();

    private WirelessEnergyStorage() {
    }

    public static WirelessEnergyStorage get() {
        if (instance == null) instance = new WirelessEnergyStorage();
        return instance;
    }

    public void addUser(BlockPos pos, BlockEntity entity) {
        energyUsers.put(pos, entity);
    }

    public void removeUser(BlockPos pos) {
        energyUsers.remove(pos);
    }

    public void loadFromChunk(ChunkAccess chunk, LevelAccessor level) {
        SCHEDULER.schedule(() -> {
            Set<BlockPos> bePositions = chunk.getBlockEntitiesPos();

            bePositions.forEach(pos -> {
                BlockEntity be = EnergyUtil.canReceiveEnergy(chunk.getBlockState(pos), level, pos);
                if (be == null) return;

                energyUsers.put(pos, be);
            });
        }, 0, TimeUnit.NANOSECONDS);
    }

    public void unloadFromChunk(ChunkAccess chunk, LevelAccessor level) {
        SCHEDULER.schedule(() -> {
            Set<BlockPos> bePositions = chunk.getBlockEntitiesPos();

            bePositions.forEach(pos -> {
                BlockEntity be = EnergyUtil.canReceiveEnergy(chunk.getBlockState(pos), level, pos);
                if (be == null) return;

                energyUsers.remove(pos);
            });
        }, 0, TimeUnit.NANOSECONDS);
    }

    public Collection<BlockEntity> getUsers() {
        return energyUsers.values();
    }

    @Nullable
    public BlockEntity getRandomUser() {
        if (energyUsers.isEmpty()) return null;
        return (BlockEntity) energyUsers.values().toArray()[rand.nextInt(energyUsers.size())];
    }

}

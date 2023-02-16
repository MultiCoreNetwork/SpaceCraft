package it.multicoredev.spacecraft.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;

import java.util.HashSet;

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
public class OxygenUtil {
//    public static boolean isAABBInBreathableAirBlock(LivingEntity entity) {
//        double y = entity.getY() + entity.getEyeHeight();
//        double x = entity.getX();
//        double z = entity.getZ();
//
//        double sx = entity.getBoundingBox().maxX - entity.getBoundingBox().minX;
//        double sy = entity.getBoundingBox().maxY - entity.getBoundingBox().minY;
//        double sz = entity.getBoundingBox().maxZ - entity.getBoundingBox().minZ;
//
//        //A good first estimate of head size is that it's the smallest of the entity's 3 dimensions (e.g. front to back, for Steve)
//        double smin = Math.min(sx, Math.min(sy, sz)) / 2;
//        double offsetXZ = 0.0;
//
//        // If entity is within air lock wall, check adjacent blocks for oxygen
//        // The value is equal to the max distance from an adjacent oxygen block to the edge of a sealer wall
//        if (entity.getLevel().getBlockState(entity.blockPosition()).getBlock() == AIR_LOCK) {
//            offsetXZ = 0.75F;
//        }
//
//        return OxygenUtil.isInOxygenBlock(entity.getLevel(), new AABB(x - smin - offsetXZ, y - smin, z - smin - offsetXZ, x + smin + offsetXZ, y + smin, z + smin + offsetXZ));
//    }
//
//    private static HashSet<BlockPos> checked;
//    public static boolean isInOxygenBlock(Level world, AABB bb){
//        int xm = Mth.floor(bb.minX);
//        int xM = Mth.floor(bb.maxX);
//        int ym = Mth.floor(bb.minY);
//        int yM = Mth.floor(bb.maxY);
//        int zm = Mth.floor(bb.minZ);
//        int zM = Mth.floor(bb.maxZ);
//
//        OxygenUtil.checked = new HashSet<>();
//        if(world.hasChunksAt(xm, ym, zm, xM, yM, zM)) {
//            for (int x = xm; x <= xM; ++x) {
//                for (int z = zm; z <= zM; ++z) {
//                    for (int y = ym; y <= yM; ++y) {
//                        BlockPos pos = new BlockPos(x, y, z);
//                        Block block = world.getBlockState(pos).getBlock();
//                        if (OxygenUtil.testContactWithBreathableAir(world, block, pos, 0) >= 0) {
//                            return true;
//                        }
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//    /*
//     * Test whether the given block at (x,y,z) coordinates is either:
//     * - breathable air (returns true)
//     * - solid, or air which is not breathable (returns false)
//     * - an air-permeable block, for example a torch, in which case test the surrounding
//     * air-reachable blocks (up to 5 blocks away) and return true if breathable air is found
//     * in one of them, or false if not.
//     */
//    private static synchronized int testContactWithBreathableAir(Level world, Block block, BlockPos pos, int limitCount){
//        checked.add(pos);
//        if(block == ARIA_RESPIRABILE){
//            return 1;
//        }
//
//        if(block == null || world.getBlockState(pos).isAir()){
//            return -1;
//        }
//
//        //Test for non-sided permeable or solid blocks first
//        boolean permeableFlag = false;
//
//        // TODO
//    }

}

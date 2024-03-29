package it.multicoredev.spacecraft.setup.config.server;

import net.minecraftforge.common.ForgeConfigSpec;

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
public class FurnaceGeneratorConfig {
    public static ForgeConfigSpec.IntValue CAPACITY;
    public static ForgeConfigSpec.IntValue GENERATION;
    public static ForgeConfigSpec.IntValue MAX_TRANSFER;


    public static void registerServerConfig(ForgeConfigSpec.Builder SERVER_BUILDER) {
        SERVER_BUILDER.comment("Furnace Generator Settings").push("furnace_generator");

        CAPACITY = SERVER_BUILDER
                .comment("The maximum amount of energy that the furnace generator can store.")
                .defineInRange("capacity", 8192, 0, Integer.MAX_VALUE);
        GENERATION = SERVER_BUILDER
                .comment("The amount of energy that the furnace generator generates per tick.")
                .defineInRange("generation", 32, 0, Integer.MAX_VALUE);
        MAX_TRANSFER = SERVER_BUILDER
                .comment("The maximum amount of energy that the furnace generator can transfer per tick.")
                .defineInRange("max_transfer", 512, 0, Integer.MAX_VALUE);

        SERVER_BUILDER.pop();
    }
}

package it.multicoredev.spacecraft.setup.config.client;

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
public class OverlayConfig {
    public static ForgeConfigSpec.IntValue TERRAFORMING_HUD_X;
    public static ForgeConfigSpec.IntValue TERRAFORMING_HUD_Y;
    public static ForgeConfigSpec.IntValue TERRAFORMING_HUD_COLOR;

    public static void registerClientConfig(ForgeConfigSpec.Builder CLIENT_BUILDER) {
        CLIENT_BUILDER.comment("Overlay settings").push("overlay");

        TERRAFORMING_HUD_X = CLIENT_BUILDER
                .comment("X location of the Terraforming HUD")
                .defineInRange("terraforming_hud_x", 10, -1, Integer.MAX_VALUE);

        TERRAFORMING_HUD_Y = CLIENT_BUILDER
                .comment("Y location of the Terraforming HUD")
                .defineInRange("terraforming_hud_y", 10, -1, Integer.MAX_VALUE);

        TERRAFORMING_HUD_COLOR = CLIENT_BUILDER
                .comment("Color of the Terraforming HUD")
                .defineInRange("terraforming_hud_color", 0xFFFFFFFF, Integer.MIN_VALUE, Integer.MAX_VALUE);

        CLIENT_BUILDER.pop();
    }
}

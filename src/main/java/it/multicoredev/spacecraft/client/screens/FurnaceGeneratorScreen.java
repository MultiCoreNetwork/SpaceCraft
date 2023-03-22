package it.multicoredev.spacecraft.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import it.multicoredev.spacecraft.SpaceCraft;
import it.multicoredev.spacecraft.blocks.generators.furnace.FurnaceGeneratorMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

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
public class FurnaceGeneratorScreen extends AbstractContainerScreen<FurnaceGeneratorMenu> {
    private final ResourceLocation GUI = new ResourceLocation(SpaceCraft.MODID, "textures/gui/furnace_generator.png");

    public FurnaceGeneratorScreen(FurnaceGeneratorMenu container, Inventory inventory, Component name) {
        super(container, inventory, name);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
        drawString(matrixStack, Minecraft.getInstance().font, title, 4, 4, 0xffffff);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, 176, 166);

        // this.blit(matrixStack, xCoord, yCoord, uCoord, vCoord, width, height);
        if (menu.isLit()) {
            int burnTime = getBurnTimeScaled(14);
            this.blit(matrixStack, relX + 27, relY + 46 + burnTime, 176, 0, 14, 14 - burnTime);
        } else {
            this.blit(matrixStack, relX + 27, relY + 46, 176, 0, 0, 0);
        }

        int energy = getEnergyScaled(42);
        this.blit(matrixStack, relX + 137, relY + 22 + (42 - energy), 176, 14 + (42 - energy), 14, energy);
    }

    private int getBurnTimeScaled(int pixels) {
        int burnTime = menu.getBurnTime();
        int maxBurnTime = menu.getMaxBurnTime();

        return maxBurnTime != 0 ? burnTime * pixels / maxBurnTime : 0;
    }

    private int getEnergyScaled(int pixels) {
        int energy = menu.getEnergy();
        int maxEnergy = menu.getMaxEnergy();

        return maxEnergy != 0 ? energy * pixels / maxEnergy : 0;
    }
}

package it.multicoredev.spacecraft.datagen;

import it.multicoredev.spacecraft.SpaceCraft;
import it.multicoredev.spacecraft.setup.registries.BlockBase;
import it.multicoredev.spacecraft.setup.registries.BlockRegistry;
import it.multicoredev.spacecraft.utils.RegistryHelper;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

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
public class ModBlockStates extends BlockStateProvider {

    public ModBlockStates(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, SpaceCraft.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        RegistryHelper.getDataGenFields()
                .stream()
                .filter(br -> br instanceof BlockRegistry)
                .forEach(br -> ((BlockRegistry) br).registerBlockstates(this));
    }

    public void generatorBlock(BlockBase<?> block) {
        getVariantBuilder(block.getBlock()).forAllStates(state -> {
            boolean lit = state.getValue(BlockStateProperties.LIT);

            String litSuffix = lit ? "_on" : "";

            ModelFile model = models().withExistingParent(block.getRegistryName() + litSuffix, "block/orientable")
                    .texture("front", "block/generators/" + block.getRegistryName() + "/front" + litSuffix)
                    .texture("side", "block/generators/" + block.getRegistryName() + "/side" + litSuffix)
                    .texture("top", "block/generators/" + block.getRegistryName() + "/top" + litSuffix)
                    .texture("bottom", "block/generators/" + block.getRegistryName() + "/bottom" + litSuffix);

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .build();
        });
    }
}
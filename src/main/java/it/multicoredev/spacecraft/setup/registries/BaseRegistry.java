package it.multicoredev.spacecraft.setup.registries;

import it.multicoredev.spacecraft.datagen.ModLanguageProvider;
import it.multicoredev.spacecraft.datagen.ModLootTables;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Copyright © 2022 by Lorenzo Magni
 * This file is part of Geology.
 * Geology is under "The 3-Clause BSD License", you can find a copy <a href="https://opensource.org/licenses/BSD-3-Clause">here</a>.
 * <p>
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
public interface BaseRegistry {
    BaseRegistry setName(String name);

    BaseRegistry addRecipes(RecipeBuilder... recipes);

    BaseRegistry addRecipes(Collection<? extends RecipeBuilder> recipes);

    BaseRegistry addBlockTags(TagKey<Block>... tags);

    BaseRegistry addItemTags(TagKey<Item>... tags);


    void registerBlockstates(BlockStateProvider provider);

    void registerModels(ItemModelProvider provider);

    void addAllRecipes(Consumer<FinishedRecipe> consumer);

    void registerToLanguage(ModLanguageProvider provider);

    Map<TagKey<Block>, Block> getBlockTags();

    Map<TagKey<Item>, Item> getItemTags();

    void registerLootTables(ModLootTables generator);
}

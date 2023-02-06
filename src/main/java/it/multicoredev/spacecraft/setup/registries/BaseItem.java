package it.multicoredev.spacecraft.setup.registries;

import it.multicoredev.spacecraft.SpaceCraft;
import it.multicoredev.spacecraft.datagen.ModBlockTags;
import it.multicoredev.spacecraft.datagen.ModItemTags;
import it.multicoredev.spacecraft.datagen.ModLanguageProvider;
import it.multicoredev.spacecraft.datagen.ModLootTables;
import it.multicoredev.spacecraft.setup.ModSetup;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;
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
//public class BaseItem implements BaseRegistry {
//    protected final String name;
//    protected final DeferredRegister<Item> ITEMS;
//    private final RegistryObject<Item> item;
//    private final Map<String, String> lang = new HashMap<>();
//    private final List<RecipeBuilder> recipes = new ArrayList<>();
//
//    public BaseItem(String name, DeferredRegister<Item> ITEMS, Item.Properties properties) {
//        this.name = name;
//        this.ITEMS = ITEMS;
//
//        this.item = ITEMS.register(name, () -> new Item(properties.tab(ModSetup.SPACECRAFT_TAB)));
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public RegistryObject<Item> getItemRegistry() {
//        return item;
//    }
//
//    public Item getItem() {
//        return item.get();
//    }
//
//    public BaseItem addRecipe(RecipeBuilder builder) {
//        this.recipes.add(builder);
//        return this;
//    }
//
//    public BaseItem addRecipes(Collection<? extends RecipeBuilder> builders) {
//        this.recipes.addAll(builders);
//        return this;
//    }
//
//    protected void addAllRecipes() {
//    }
//
//    @Override
//    public void registerBlockstates(BlockStateProvider provider) {
//    }
//
//    @Override
//    public void registerModels(ItemModelProvider provider) {
//        provider.singleTexture(name, provider.mcLoc("item/generated"), "layer0", provider.modLoc("item/" + name));
//    }
//
//    @Override
//    public void addAllRecipes(Consumer<FinishedRecipe> consumer) {
//        addAllRecipes();
//
//        Map<String, Integer> shaped = new HashMap<>();
//        Map<String, Integer> shapeless = new HashMap<>();
//        Map<String, Integer> smelting = new HashMap<>();
//        Map<String, Integer> stonecutter = new HashMap<>();
//
//        recipes.forEach(recipe -> {
//            String path;
//
//            if (recipe instanceof ShapedRecipeBuilder) {
//                String name = recipe.getResult().getRegistryName().getPath();
//
//                if (shaped.containsKey(name)) {
//                    int i = shaped.get(name);
//                    shaped.put(name, i + 1);
//                    path = name + "_shaped_" + i;
//                } else {
//                    shaped.put(name, 1);
//                    path = name + "_shaped";
//                }
//            } else if (recipe instanceof ShapelessRecipeBuilder) {
//                String name = recipe.getResult().getRegistryName().getPath();
//
//                if (shapeless.containsKey(name)) {
//                    int i = shapeless.get(name);
//                    shapeless.put(name, i + 1);
//                    path = name + "_shapeless_" + i;
//                } else {
//                    shapeless.put(name, 1);
//                    path = name + "_shapeless";
//                }
//            } else if (recipe instanceof SimpleCookingRecipeBuilder) {
//                String name = recipe.getResult().getRegistryName().getPath();
//
//                if (smelting.containsKey(name)) {
//                    int i = smelting.get(name);
//                    smelting.put(name, i + 1);
//                    path = name + "_smelting_" + i;
//                } else {
//                    smelting.put(name, 1);
//                    path = name + "_smelting";
//                }
//            } else {
//                String name = recipe.getResult().getRegistryName().getPath();
//
//                if (stonecutter.containsKey(name)) {
//                    int i = stonecutter.get(name);
//                    stonecutter.put(name, i + 1);
//                    path = name + "_stonecutter_" + i;
//                } else {
//                    stonecutter.put(name, 1);
//                    path = name + "_stonecutter";
//                }
//            }
//
//            recipe.save(consumer, new ResourceLocation(SpaceCraft.MODID, path));
//        });
//    }
//
//    @Override
//    public void registerToLanguage(ModLanguageProvider provider) {
//        if (!lang.containsKey(provider.getLocale())) return;
//        provider.add(getItem(), lang.get(provider.getLocale()));
//    }
//
//    @Override
//    public void getBlockTags(ModBlockTags provider) {
//    }
//
//    @Override
//    public void registerItemTags(ModItemTags provider) {
//    }
//
//    @Override
//    public void registerLootTables(ModLootTables generator) {
//    }
//}

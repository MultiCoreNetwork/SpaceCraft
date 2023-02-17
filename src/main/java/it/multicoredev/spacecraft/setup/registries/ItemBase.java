package it.multicoredev.spacecraft.setup.registries;

import it.multicoredev.spacecraft.SpaceCraft;
import it.multicoredev.spacecraft.datagen.ModLanguageProvider;
import it.multicoredev.spacecraft.setup.ModSetup;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;
import java.util.function.Consumer;

import static it.multicoredev.spacecraft.setup.registries.Registration.ITEMS;

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
public class ItemBase implements BaseRegistry {
    protected final String name;
    protected final RegistryObject<Item> item;
    protected final List<String> lang = new ArrayList<>();
    protected final List<RecipeBuilder> recipes = new ArrayList<>();
    protected final Map<TagKey<Item>, Item> itemTags = new HashMap<>();

    public ItemBase(String name, Item.Properties properties) {
        this.name = name;

        this.item = ITEMS.register(name, () -> new Item(properties.tab(ModSetup.SPACECRAFT_TAB)));
    }

    public RegistryObject<Item> getItemRegistry() {
        return item;
    }

    public String getRegistryName() {
        return name;
    }

    public Item getItem() {
        return item.get();
    }

    @Override
    public ItemBase setName(String itemName) {
        lang.add(itemName);
        return this;
    }

    @Override
    public ItemBase addRecipes(RecipeBuilder... builders) {
        this.recipes.addAll(Arrays.asList(builders));
        return this;
    }

    @Override
    public ItemBase addRecipes(Collection<? extends RecipeBuilder> builders) {
        this.recipes.addAll(builders);
        return this;
    }

    @Override
    public ItemBase addBlockTags(TagKey<Block>... tags) {
        throw new UnsupportedOperationException("Cannot add block tags to an item");
    }

    @Override
    public ItemBase addItemTags(TagKey<Item>... tags) {
        for (TagKey<Item> tag : tags) itemTags.put(tag, getItem());
        return this;
    }


    @Override
    public void registerBlockstates(BlockStateProvider provider) {
        throw new UnsupportedOperationException("Cannot register blockstates for an item");
    }

    @Override
    public void registerModels(ItemModelProvider provider) {
        provider.singleTexture(name, provider.mcLoc("item/generated"), "layer0", provider.modLoc("item/" + name));
    }

    @Override
    public void addAllRecipes(Consumer<FinishedRecipe> consumer) {
        Map<String, Integer> shaped = new HashMap<>();
        Map<String, Integer> shapeless = new HashMap<>();
        Map<String, Integer> smelting = new HashMap<>();
        Map<String, Integer> stonecutter = new HashMap<>();

        recipes.forEach(recipe -> {
            String path;

            if (recipe instanceof ShapedRecipeBuilder) {
                String name = ForgeRegistries.ITEMS.getKey(recipe.getResult()).getPath();

                if (shaped.containsKey(name)) {
                    int i = shaped.get(name);
                    shaped.put(name, i + 1);
                    path = name + "_shaped_" + i;
                } else {
                    shaped.put(name, 1);
                    path = name + "_shaped";
                }
            } else if (recipe instanceof ShapelessRecipeBuilder) {
                if (shapeless.containsKey(name)) {
                    int i = shapeless.get(name);
                    shapeless.put(name, i + 1);
                    path = name + "_shapeless_" + i;
                } else {
                    shapeless.put(name, 1);
                    path = name + "_shapeless";
                }
            } else if (recipe instanceof SimpleCookingRecipeBuilder) {
                String name = ForgeRegistries.ITEMS.getKey(recipe.getResult()).getPath();

                if (smelting.containsKey(name)) {
                    int i = smelting.get(name);
                    smelting.put(name, i + 1);
                    path = name + "_smelting_" + i;
                } else {
                    smelting.put(name, 1);
                    path = name + "_smelting";
                }
            } else {
                String name = ForgeRegistries.ITEMS.getKey(recipe.getResult()).getPath();

                if (stonecutter.containsKey(name)) {
                    int i = stonecutter.get(name);
                    stonecutter.put(name, i + 1);
                    path = name + "_stonecutter_" + i;
                } else {
                    stonecutter.put(name, 1);
                    path = name + "_stonecutter";
                }
            }

            recipe.save(consumer, new ResourceLocation(SpaceCraft.MODID, path));
        });
    }

    @Override
    public void registerToLanguage(ModLanguageProvider provider) {
        if (lang.isEmpty()) return;

        provider.add(getItem(), lang.get(0));
    }

    @Override
    public Map<TagKey<Block>, Block> getBlockTags() {
        return new HashMap<>();
    }

    @Override
    public Map<TagKey<Item>, Item> getItemTags() {
        return itemTags;
    }

    @Override
    public void registerLootTables() {
    }
}

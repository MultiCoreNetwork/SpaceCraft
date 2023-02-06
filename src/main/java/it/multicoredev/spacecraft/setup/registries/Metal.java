package it.multicoredev.spacecraft.setup.registries;

import it.multicoredev.spacecraft.datagen.ModBlockTags;
import it.multicoredev.spacecraft.datagen.ModItemTags;
import it.multicoredev.spacecraft.datagen.ModLanguageProvider;
import it.multicoredev.spacecraft.setup.ModSetup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

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
//public class Metal extends BaseBlock {
//    private final String ingotName;
//    private final String nuggetName;
//    private final Map<String, String[]> lang = new HashMap<>();
//
//    private final RegistryObject<Item> ingot;
//    private final RegistryObject<Item> nugget;
//
//    public Metal(String name, DeferredRegister<Block> BLOCKS, DeferredRegister<Item> ITEMS, BlockBehaviour.Properties properties) {
//        super(name + "_block", BLOCKS, ITEMS, properties);
//
//        ingotName = name + "_ingot";
//        nuggetName = name + "_nugget";
//
//        ingot = ITEMS.register(ingotName, () -> new Item(new Item.Properties().tab(ModSetup.SPACECRAFT_TAB)));
//        nugget = ITEMS.register(nuggetName, () -> new Item(new Item.Properties().tab(ModSetup.SPACECRAFT_TAB)));
//    }
//
//    public RegistryObject<Item> getIngotRegistry() {
//        return ingot;
//    }
//
//    public RegistryObject<Item> getNuggetRegistry() {
//        return nugget;
//    }
//
//    public Item getIngot() {
//        return ingot.get();
//    }
//
//    public Item getNugget() {
//        return nugget.get();
//    }
//
//    public Metal addTranslation(String locale, String block, String ingot, String nugget) {
//        lang.put(locale, new String[]{block, ingot, nugget});
//        return this;
//    }
//
//    @Override
//    protected void addAllRecipes() {
//        addRecipes(ModRecipeBuilder.nineRecipe(getBlock(), getIngot()));
//        addRecipes(ModRecipeBuilder.nineRecipe(getIngot(), getNugget()));
//    }
//
//    @Override
//    public void registerBlockstates(BlockStateProvider provider) {
//        super.registerBlockstates(provider);
//    }
//
//    @Override
//    public void registerModels(ItemModelProvider provider) {
//        super.registerModels(provider);
//
//        provider.singleTexture(ingotName, provider.mcLoc("item/generated"), "layer0", provider.modLoc("item/" + ingotName));
//        provider.singleTexture(nuggetName, provider.mcLoc("item/generated"), "layer0", provider.modLoc("item/" + nuggetName));
//    }
//
//    @Override
//    public void registerToLanguage(ModLanguageProvider provider) {
//        if (!lang.containsKey(provider.getLocale())) return;
//        String[] names = lang.get(provider.getLocale());
//
//        provider.add(getBlock(), names[0]);
//        provider.add(getIngot(), names[1]);
//        provider.add(getNugget(), names[2]);
//    }
//
//    @Override
//    public void getBlockTags(ModBlockTags provider) {
//        provider.addTag(BlockTags.MINEABLE_WITH_PICKAXE).add(getBlock());
//        provider.addTag(BlockTags.NEEDS_STONE_TOOL).add(getBlock());
//    }
//
//    @Override
//    public void registerItemTags(ModItemTags provider) {
//        provider.addTag(Tags.Items.INGOTS).add(getIngot());
//        provider.addTag(Tags.Items.NUGGETS).add(getNugget());
//    }
//}

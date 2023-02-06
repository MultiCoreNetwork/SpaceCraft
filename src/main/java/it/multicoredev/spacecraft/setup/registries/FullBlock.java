package it.multicoredev.spacecraft.setup.registries;

import it.multicoredev.spacecraft.datagen.ModBlockTags;
import it.multicoredev.spacecraft.datagen.ModLanguageProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
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
//public class FullBlock extends BaseBlock {
//    private final String secondaryName;
//    private String doubleSlabTexture;
//    private final Map<String, String[]> lang = new HashMap<>();
//
//    private final RegistryObject<SlabBlock> slab;
//    private final RegistryObject<Item> slabItem;
//    private final RegistryObject<StairBlock> stairs;
//    private final RegistryObject<Item> stairsItem;
//    private final RegistryObject<WallBlock> wall;
//    private final RegistryObject<Item> wallItem;
//
//    public FullBlock(String name, String secondaryName, DeferredRegister<Block> BLOCKS, DeferredRegister<Item> ITEMS, BlockBehaviour.Properties properties) {
//        super(name, BLOCKS, ITEMS, properties);
//        this.secondaryName = secondaryName;
//        this.doubleSlabTexture = name;
//
//        slab = BLOCKS.register(secondaryName + "_slab", () -> new SlabBlock(properties));
//        stairs = BLOCKS.register(secondaryName + "_stairs", () -> new StairBlock(getBlock()::defaultBlockState, properties));
//        wall = BLOCKS.register(secondaryName + "_wall", () -> new WallBlock(properties));
//
//        slabItem = fromBlock(slab);
//        stairsItem = fromBlock(stairs);
//        wallItem = fromBlock(wall);
//    }
//
//    public FullBlock(String name, DeferredRegister<Block> BLOCKS, DeferredRegister<Item> ITEMS, BlockBehaviour.Properties properties) {
//        this(name, name, BLOCKS, ITEMS, properties);
//    }
//
//    public String getSecondaryName() {
//        return secondaryName;
//    }
//
//    public RegistryObject<SlabBlock> getSlabRegistry() {
//        return slab;
//    }
//
//    public RegistryObject<StairBlock> getStairsRegistry() {
//        return stairs;
//    }
//
//    public RegistryObject<WallBlock> getWallRegistry() {
//        return wall;
//    }
//
//    public RegistryObject<Item> getSlabItemRegistry() {
//        return slabItem;
//    }
//
//    public RegistryObject<Item> getStairsItemRegistry() {
//        return stairsItem;
//    }
//
//    public RegistryObject<Item> getWallItemRegistry() {
//        return wallItem;
//    }
//
//    public SlabBlock getSlab() {
//        return slab.get();
//    }
//
//    public StairBlock getStairs() {
//        return stairs.get();
//    }
//
//    public WallBlock getWall() {
//        return wall.get();
//    }
//
//    public Item getSlabItem() {
//        return slabItem.get();
//    }
//
//    public Item getStairsItem() {
//        return stairsItem.get();
//    }
//
//    public Item getWallItem() {
//        return wallItem.get();
//    }
//
//    public FullBlock setDoubleSlabTexture(String texture) {
//        doubleSlabTexture = texture;
//        return this;
//    }
//
//    public FullBlock addTranslation(String locale, String block, String slab, String stairs, String wall) {
//        lang.put(locale, new String[]{block, slab, stairs, wall});
//        return this;
//    }
//
//    @Override
//    protected void addAllRecipes() {
//        addRecipes(ModRecipeBuilder.slabAll(getSlab(), getBlock()));
//        addRecipes(ModRecipeBuilder.stairsAll(getStairs(), getBlock()));
//        addRecipes(ModRecipeBuilder.wallAll(getWall(), getBlock()));
//    }
//
//    @Override
//    public void registerBlockstates(BlockStateProvider provider) {
//        super.registerBlockstates(provider);
//        provider.slabBlock(getSlab(), provider.modLoc("block/" + doubleSlabTexture), provider.modLoc("block/" + name));
//        provider.stairsBlock(getStairs(), provider.modLoc("block/" + name));
//        provider.wallBlock(getWall(), provider.modLoc("block/" + name));
//    }
//
//    @Override
//    public void registerModels(ItemModelProvider provider) {
//        super.registerModels(provider);
//        provider.slab(
//                secondaryName + "_slab",
//                provider.modLoc("block/" + name),
//                provider.modLoc("block/" + name),
//                provider.modLoc("block/" + doubleSlabTexture)
//        );
//        provider.stairs(
//                secondaryName + "_stairs",
//                provider.modLoc("block/" + name),
//                provider.modLoc("block/" + name),
//                provider.modLoc("block/" + name)
//        );
//        provider.wallInventory(secondaryName + "_wall", provider.modLoc("block/" + name));
//    }
//
//    @Override
//    public void registerToLanguage(ModLanguageProvider provider) {
//        if (!lang.containsKey(provider.getLocale())) return;
//        String[] names = lang.get(provider.getLocale());
//
//        provider.add(getBlock(), names[0]);
//        provider.add(getSlab(), names[1]);
//        provider.add(getStairs(), names[2]);
//        provider.add(getWall(), names[3]);
//    }
//
//    @Override
//    public void getBlockTags(ModBlockTags provider) {
//        provider.addTag(BlockTags.MINEABLE_WITH_PICKAXE)
//                .add(getBlock())
//                .add(getSlab())
//                .add(getStairs())
//                .add(getWall());
//
//        provider.addTag(BlockTags.SLABS).add(getSlab());
//        provider.addTag(BlockTags.STAIRS).add(getStairs());
//        provider.addTag(BlockTags.WALLS).add(getWall());
//    }
//}

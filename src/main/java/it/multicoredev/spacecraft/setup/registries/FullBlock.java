package it.multicoredev.spacecraft.setup.registries;

import it.multicoredev.spacecraft.datagen.BaseLootTableProvider;
import it.multicoredev.spacecraft.datagen.ModLanguageProvider;
import it.multicoredev.spacecraft.utils.RecipeBuilderUtil;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

import static it.multicoredev.spacecraft.setup.registries.ModRegistry.BLOCKS;
import static it.multicoredev.spacecraft.setup.registries.ModRegistry.fromBlock;

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
public class FullBlock extends BlockBase<Block> {
    private final String secondaryName;
    private String doubleSlabTexture;
    private final RegistryObject<SlabBlock> slab;
    private final RegistryObject<Item> slabItem;
    private final RegistryObject<StairBlock> stairs;
    private final RegistryObject<Item> stairsItem;
    private final RegistryObject<WallBlock> wall;
    private final RegistryObject<Item> wallItem;

    public FullBlock(String name, String secondaryName, BlockBehaviour.Properties properties) {
        super(name, () -> new Block(properties));

        this.secondaryName = secondaryName;
        this.doubleSlabTexture = name;

        this.slab = BLOCKS.register(secondaryName + "_slab", () -> new SlabBlock(properties));
        this.stairs = BLOCKS.register(secondaryName + "_stairs", () -> new StairBlock(getBlock()::defaultBlockState, properties));
        this.wall = BLOCKS.register(secondaryName + "_wall", () -> new WallBlock(properties));


        slabItem = fromBlock(slab);
        stairsItem = fromBlock(stairs);
        wallItem = fromBlock(wall);
    }

    public FullBlock(String name, BlockBehaviour.Properties properties) {
        this(name, name, properties);
    }

    public RegistryObject<SlabBlock> getSlabRegistry() {
        return slab;
    }

    public RegistryObject<Item> getSlabItemRegistry() {
        return slabItem;
    }

    public RegistryObject<StairBlock> getStairsRegistry() {
        return stairs;
    }

    public RegistryObject<Item> getStairsItemRegistry() {
        return stairsItem;
    }

    public RegistryObject<WallBlock> getWallRegistry() {
        return wall;
    }

    public RegistryObject<Item> getWallItemRegistry() {
        return wallItem;
    }

    public SlabBlock getSlab() {
        return slab.get();
    }

    public Item getSlabItem() {
        return slabItem.get();
    }

    public StairBlock getStairs() {
        return stairs.get();
    }

    public Item getStairsItem() {
        return stairsItem.get();
    }

    public WallBlock getWall() {
        return wall.get();
    }

    public Item getWallItem() {
        return wallItem.get();
    }

    public FullBlock setName(String blockName, String secondaryName) {
        this.lang.add(blockName);
        this.lang.add(secondaryName);
        return this;
    }

    public FullBlock addSlabTags(TagKey<Block>... tags) {
        for (TagKey<Block> tag : tags) blockTags.put(tag, slab);
        return this;
    }

    public FullBlock addStairsTags(TagKey<Block>... tags) {
        for (TagKey<Block> tag : tags) blockTags.put(tag, stairs);
        return this;
    }

    public FullBlock addWallTags(TagKey<Block>... tags) {
        for (TagKey<Block> tag : tags) blockTags.put(tag, wall);
        return this;
    }

    public FullBlock addSlabItemTags(TagKey<Item>... tags) {
        for (TagKey<Item> tag : tags) itemTags.put(tag, slabItem);
        return this;
    }

    public FullBlock addStairsItemTags(TagKey<Item>... tags) {
        for (TagKey<Item> tag : tags) itemTags.put(tag, stairsItem);
        return this;
    }

    public FullBlock addWallItemTags(TagKey<Item>... tags) {
        for (TagKey<Item> tag : tags) itemTags.put(tag, wallItem);
        return this;
    }

    public FullBlock setDoubleSlabTexture(String texture) {
        doubleSlabTexture = texture;
        return this;
    }


    @Override
    public void registerBlockstates(BlockStateProvider provider) {
        super.registerBlockstates(provider);

        provider.slabBlock(getSlab(), provider.modLoc("block/" + doubleSlabTexture), provider.modLoc("block/" + name));
        provider.stairsBlock(getStairs(), provider.modLoc("block/" + name));
        provider.wallBlock(getWall(), provider.modLoc("block/" + name));
    }

    @Override
    public void registerModels(ItemModelProvider provider) {
        super.registerModels(provider);

        provider.slab(
                secondaryName + "_slab",
                provider.modLoc("block/" + name),
                provider.modLoc("block/" + name),
                provider.modLoc("block/" + doubleSlabTexture)
        );

        provider.stairs(
                secondaryName + "_stairs",
                provider.modLoc("block/" + name),
                provider.modLoc("block/" + name),
                provider.modLoc("block/" + name)
        );

        provider.wallInventory(secondaryName + "_wall", provider.modLoc("block/" + name));
    }

    @Override
    public void addAllRecipes(Consumer<FinishedRecipe> consumer) {
        addRecipes(RecipeBuilderUtil.slabAll(getSlab(), getBlock()));
        addRecipes(RecipeBuilderUtil.stairsAll(getStairs(), getBlock()));
        addRecipes(RecipeBuilderUtil.wallAll(getWall(), getBlock()));

        super.addAllRecipes(consumer);
    }

    @Override
    public void registerToLanguage(ModLanguageProvider provider) {
        if (lang.isEmpty()) return;
        String secondaryName = lang.size() > 1 ? lang.get(1) : lang.get(0);

        provider.add(getBlock(), lang.get(0));
        provider.add(getSlab(), secondaryName + " Slab");
        provider.add(getStairs(), secondaryName + " Stairs");
        provider.add(getWall(), secondaryName + " Wall");
    }

    @Override
    public void registerLootTables() {
        super.registerLootTables();

        BaseLootTableProvider.createSimpleTable(slab.getId().getPath(), getSlab());
        BaseLootTableProvider.createSimpleTable(stairs.getId().getPath(), getStairs());
        BaseLootTableProvider.createSimpleTable(wall.getId().getPath(), getWall());
    }
}

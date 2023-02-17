package it.multicoredev.spacecraft.setup.registries;

import it.multicoredev.spacecraft.utils.RecipeBuilderUtil;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Consumer;

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
public class Metal extends BlockBase<Block> {
    private final String ingotName;
    private final String nuggetName;
    private final String dustName;
    private final ItemBase ingot;
    private final ItemBase nugget;
    private final ItemBase dust;

    public Metal(String name, BlockBehaviour.Properties blockProperties, Item.Properties ingotProperties, Item.Properties nuggetProperties, Item.Properties dustProperties) {
        super(name + "_block", () -> new Block(blockProperties));

        ingotName = name + "_ingot";
        nuggetName = name + "_nugget";
        dustName = name + "_dust";

        ingot = new ItemBase(ingotName, ingotProperties);
        nugget = new ItemBase(nuggetName, nuggetProperties);
        dust = dustProperties != null ? new ItemBase(dustName, dustProperties) : null;
    }

    public Metal(String name, BlockBehaviour.Properties blockProperties, Item.Properties ingotProperties, Item.Properties nuggetProperties) {
        this(name, blockProperties, ingotProperties, nuggetProperties, null);
    }

    public Metal(String name, BlockBehaviour.Properties blockProperties, Item.Properties itemProperties, boolean addDust) {
        super(name + "_block", () -> new Block(blockProperties));

        ingotName = name + "_ingot";
        nuggetName = name + "_nugget";
        dustName = name + "_dust";

        ingot = new ItemBase(ingotName, itemProperties);
        nugget = new ItemBase(nuggetName, itemProperties);
        dust = addDust ? new ItemBase(dustName, itemProperties) : null;
    }

    public Metal(String name, BlockBehaviour.Properties blockProperties, Item.Properties itemProperties) {
        this(name, blockProperties, itemProperties, false);
    }

    public ItemBase getIngot() {
        return ingot;
    }

    public ItemBase getNugget() {
        return nugget;
    }

    public ItemBase getDust() {
        return dust;
    }

    public boolean hasDust() {
        return dust != null;
    }

    @Override
    public Metal setName(String name) {
        super.setName(name);
        ingot.setName(ingotName);
        nugget.setName(nuggetName);
        if (dust != null) dust.setName(dustName);

        return this;
    }

    public Metal addIngotTags(TagKey<Item>... tags) {
        ingot.addItemTags(tags);
        return this;
    }

    public Metal addNuggetTags(TagKey<Item>... tags) {
        nugget.addItemTags(tags);
        return this;
    }

    public Metal addDustTags(TagKey<Item>... tags) {
        if (dust != null) dust.addItemTags(tags);
        return this;
    }

    @Override
    public void addAllRecipes(Consumer<FinishedRecipe> consumer) {
        addRecipes(RecipeBuilderUtil.nineRecipe(getBlock(), ingot.getItem()));
        addRecipes(RecipeBuilderUtil.nineRecipe(ingot.getItem(), nugget.getItem()));

        super.addAllRecipes(consumer);
    }
}

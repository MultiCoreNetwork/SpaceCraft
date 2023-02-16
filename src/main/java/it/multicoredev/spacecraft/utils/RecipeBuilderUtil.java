package it.multicoredev.spacecraft.utils;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class RecipeBuilderUtil {
    public static List<SimpleCookingRecipeBuilder> smelting(List<ItemLike> ingredients, ItemLike result, float experience, int cookingTime, String group) {
        List<SimpleCookingRecipeBuilder> recipes = new ArrayList<>();
        for (ItemLike ingredient : ingredients) {
            recipes.add(SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), result, experience, cookingTime).group(group).unlockedBy(ForgeRegistries.ITEMS.getKey(ingredient.asItem()).getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(ingredient.asItem())));
        }

        return recipes;
    }

    public static List<SimpleCookingRecipeBuilder> blasting(List<ItemLike> ingredients, ItemLike result, float experience, int cookingTime, String group) {
        List<SimpleCookingRecipeBuilder> recipes = new ArrayList<>(ingredients.size());
        for (ItemLike ingredient : ingredients) {
            recipes.add(SimpleCookingRecipeBuilder.blasting(Ingredient.of(ingredient), result, experience, cookingTime).group(group).unlockedBy(ForgeRegistries.ITEMS.getKey(ingredient.asItem()).getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(ingredient.asItem())));
        }

        return recipes;
    }

    public static List<SimpleCookingRecipeBuilder> smoking(List<ItemLike> ingredients, ItemLike result, float experience, int cookingTime, String group) {
        List<SimpleCookingRecipeBuilder> recipes = new ArrayList<>(ingredients.size());
        for (ItemLike ingredient : ingredients) {
            recipes.add(SimpleCookingRecipeBuilder.smoking(Ingredient.of(ingredient), result, experience, cookingTime).group(group).unlockedBy(ForgeRegistries.ITEMS.getKey(ingredient.asItem()).getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(ingredient.asItem())));
        }

        return recipes;
    }

    public static ShapelessRecipeBuilder button(ItemLike button, ItemLike ingredient) {
        return ShapelessRecipeBuilder.shapeless(button)
                .requires(ingredient)
                .group(ForgeRegistries.ITEMS.getKey(ingredient.asItem()).getPath())
                .unlockedBy(ForgeRegistries.ITEMS.getKey(ingredient.asItem()).getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(ingredient.asItem()));
    }

    public static ShapedRecipeBuilder pressurePlate(ItemLike pressurePlate, ItemLike ingredient) {
        return ShapedRecipeBuilder.shaped(pressurePlate)
                .pattern("##")
                .define('#', ingredient)
                .group(ForgeRegistries.ITEMS.getKey(ingredient.asItem()).getPath())
                .unlockedBy(ForgeRegistries.ITEMS.getKey(ingredient.asItem()).getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(ingredient.asItem()));
    }

    public static ShapedRecipeBuilder slab(ItemLike slab, ItemLike ingredient) {
        return ShapedRecipeBuilder.shaped(slab, 6)
                .pattern("###")
                .define('#', ingredient)
                .group(ForgeRegistries.ITEMS.getKey(slab.asItem()).getPath())
                .unlockedBy(ForgeRegistries.ITEMS.getKey(ingredient.asItem()).getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(ingredient.asItem()));
    }

    public static SingleItemRecipeBuilder stonecutterSlab(ItemLike slab, ItemLike ingredient) {
        return SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), slab, 2)
                .unlockedBy(ForgeRegistries.ITEMS.getKey(ingredient.asItem()).getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(ingredient.asItem()));
    }

    public static List<RecipeBuilder> slabAll(ItemLike slab, ItemLike ingredient) {
        return Arrays.asList(slab(slab, ingredient), stonecutterSlab(slab, ingredient));
    }

    public static ShapedRecipeBuilder stairs(ItemLike stairs, ItemLike ingredient) {
        return ShapedRecipeBuilder.shaped(stairs, 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', ingredient)
                .group(ForgeRegistries.ITEMS.getKey(stairs.asItem()).getPath())
                .unlockedBy(ForgeRegistries.ITEMS.getKey(ingredient.asItem()).getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(ingredient.asItem()));
    }

    public static SingleItemRecipeBuilder stonecutterStairs(ItemLike stairs, ItemLike ingredient) {
        return SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), stairs, 1)
                .unlockedBy(ForgeRegistries.ITEMS.getKey(ingredient.asItem()).getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(ingredient.asItem()));
    }

    public static List<RecipeBuilder> stairsAll(ItemLike stairs, ItemLike ingredient) {
        return Arrays.asList(stairs(stairs, ingredient), stonecutterStairs(stairs, ingredient));
    }

    public static ShapedRecipeBuilder wall(ItemLike wall, ItemLike ingredient) {
        return ShapedRecipeBuilder.shaped(wall, 6)
                .pattern("###")
                .pattern("###")
                .define('#', ingredient)
                .group(ForgeRegistries.ITEMS.getKey(wall.asItem()).getPath())
                .unlockedBy(ForgeRegistries.ITEMS.getKey(ingredient.asItem()).getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(ingredient.asItem()));
    }

    public static SingleItemRecipeBuilder stonecutterWall(ItemLike wall, ItemLike ingredient) {
        return SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), wall, 1)
                .unlockedBy(ForgeRegistries.ITEMS.getKey(ingredient.asItem()).getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(ingredient.asItem()));
    }

    public static List<RecipeBuilder> wallAll(ItemLike wall, ItemLike ingredient) {
        return Arrays.asList(wall(wall, ingredient), stonecutterWall(wall, ingredient));
    }

    public static ShapedRecipeBuilder fourRecipe(ItemLike result, ItemLike ingredient, int amount) {
        return ShapedRecipeBuilder.shaped(result, amount)
                .pattern("##")
                .pattern("##")
                .define('#', ingredient)
                .group(ForgeRegistries.ITEMS.getKey(result.asItem()).getPath())
                .unlockedBy(ForgeRegistries.ITEMS.getKey(ingredient.asItem()).getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(ingredient.asItem()));
    }

    public static List<RecipeBuilder> nineRecipe(ItemLike one, ItemLike nine) {
        return Arrays.asList(
                ShapedRecipeBuilder.shaped(one)
                        .pattern("###")
                        .pattern("###")
                        .pattern("###")
                        .define('#', nine)
                        .group(ForgeRegistries.ITEMS.getKey(one.asItem()).getPath())
                        .unlockedBy(ForgeRegistries.ITEMS.getKey(nine.asItem()).getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(nine.asItem())),
                ShapelessRecipeBuilder.shapeless(nine, 9)
                        .requires(one)
                        .group(ForgeRegistries.ITEMS.getKey(nine.asItem()).getPath())
                        .unlockedBy(ForgeRegistries.ITEMS.getKey(nine.asItem()).getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(nine.asItem()))
        );
    }
}

package it.multicoredev.spacecraft.setup.registries;

import it.multicoredev.spacecraft.blocks.generators.creative.CreativeGenerator;
import it.multicoredev.spacecraft.blocks.generators.creative.CreativeGeneratorBE;
import it.multicoredev.spacecraft.blocks.generators.furnace.FurnaceGenerator;
import it.multicoredev.spacecraft.blocks.generators.furnace.FurnaceGeneratorBE;
import it.multicoredev.spacecraft.blocks.generators.furnace.FurnaceGeneratorMenu;
import it.multicoredev.spacecraft.datagen.ModBlockStates;
import it.multicoredev.spacecraft.setup.ModSetup;
import it.multicoredev.spacecraft.utils.DataGen;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static it.multicoredev.spacecraft.SpaceCraft.MODID;

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
public class ModRegistry {
    static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
        MENUS.register(bus);
    }

    static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(ModSetup.SPACECRAFT_TAB)));
    }

    //@DataGen
    //public static final BlockBase<Block> TEST = BlockBase.create("test", BlockBehaviour.Properties.copy(Blocks.STONE)).setName("Test").addBlockTags(BlockTags.MINEABLE_WITH_PICKAXE);

    public static TagKey<Item> OXYGEN_TANK_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("curios", "oxygen_tank"));
    @DataGen
    public static final ItemBase OXYGEN_TANK = new ItemBase("oxygen_tank", new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)).setName("Oxygen Tank").addItemTags(OXYGEN_TANK_TAG);

    //@DataGen
    public static final BlockBase<CreativeGenerator> CREATIVE_GENERATOR = new BlockBase<>("creative_generator", CreativeGenerator::new).setName("Creative Generator");
    public static final RegistryObject<BlockEntityType<CreativeGeneratorBE>> CREATIVE_GENERATOR_BE = BLOCK_ENTITIES.register(CREATIVE_GENERATOR.getRegistryName(), () -> BlockEntityType.Builder.of(CreativeGeneratorBE::new, CREATIVE_GENERATOR.getBlock()).build(null));

    @DataGen
    public static final BlockBase<FurnaceGenerator> FURNACE_GENERATOR = new BlockBase<>("furnace_generator", FurnaceGenerator::new) {
        @Override
        public void registerBlockstates(ModBlockStates provider) {
            provider.generatorBlock(this);
        }
    }.setName("Furnace Generator");
    public static final RegistryObject<BlockEntityType<FurnaceGeneratorBE>> FURNACE_GENERATOR_BE = BLOCK_ENTITIES.register(FURNACE_GENERATOR.getRegistryName(), () -> BlockEntityType.Builder.of(FurnaceGeneratorBE::new, FURNACE_GENERATOR.getBlock()).build(null));
    public static final RegistryObject<MenuType<FurnaceGeneratorMenu>> FURNACE_GENERATOR_MENU = MENUS.register(FURNACE_GENERATOR.getRegistryName(), () -> IForgeMenuType.create((windowId, inv, data) -> new FurnaceGeneratorMenu(windowId, data.readBlockPos(), inv, inv.player)));
}
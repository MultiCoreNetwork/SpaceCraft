package it.multicoredev.spacecraft.setup.registries;

import it.multicoredev.spacecraft.blocks.generators.creative.CreativeGenerator;
import it.multicoredev.spacecraft.blocks.generators.creative.CreativeGeneratorBE;
import it.multicoredev.spacecraft.inventory.OxygenInventoryMenu;
import it.multicoredev.spacecraft.setup.ModSetup;
import it.multicoredev.spacecraft.utils.DataGen;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
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

    public static final RegistryObject<MenuType<OxygenInventoryMenu>> OXYGEN_INVENTORY_MENU = MENUS.register("oxygen_inventory", () -> IForgeMenuType.create((w, i, b) -> {
        throw new UnsupportedOperationException();
    }));
    public static final RegistryObject<MenuType<OxygenInventoryMenu>> TEST_INVENTORY_MENU = MENUS.register("test_inventory", () -> IForgeMenuType.create((w, i, b) -> {
        throw new UnsupportedOperationException();
    }));

    @DataGen
    public static final BlockBase<Block> TEST = BlockBase.create("test", BlockBehaviour.Properties.copy(Blocks.STONE)).setName("Test");

    @DataGen
    public static final BlockBase<CreativeGenerator> CREATIVE_GENERATOR = new BlockBase<>("creative_generator", CreativeGenerator::new) {
        @Override
        public void registerBlockstates(BlockStateProvider provider) {
            //TODO register blockstates
        }
    }
            .setName("Creative Generator");
    public static final RegistryObject<BlockEntityType<CreativeGeneratorBE>> CREATIVE_GENERATOR_BE = BLOCK_ENTITIES.register(CREATIVE_GENERATOR.getRegistryName(), () -> BlockEntityType.Builder.of(CreativeGeneratorBE::new, CREATIVE_GENERATOR.getBlock()).build(null));
}
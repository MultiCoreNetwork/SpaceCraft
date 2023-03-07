package it.multicoredev.spacecraft.setup.client;

import it.multicoredev.spacecraft.SpaceCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = SpaceCraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEvents {

//    @Mod.EventBusSubscriber(modid = SpaceCraft.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
//    public static class ModBusEvents {
//        @SubscribeEvent
//        public static void textureStitch(TextureStitchEvent.Pre event) {
//            event.addSprite(new ResourceLocation(SpaceCraft.MODID, "gui/empty_oxygen_tank_slot"));
//        }
//    }
}

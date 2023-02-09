package it.multicoredev.spacecraft.events;

import it.multicoredev.spacecraft.SpaceCraft;
import it.multicoredev.spacecraft.setup.config.WorldConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SpaceCraft.MODID)
public class ForgeEventBusEvents {

    @SubscribeEvent
    public static void onTick(final LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            Level level = player.getLevel();

            if (!level.isClientSide()) {
                if (WorldConfig.OXYGEN_REQUIRED_IN.get().contains(level.dimension().location().toString()) && level.getGameTime() % 20 == 1) {
                    if (!player.isCreative() && !player.isSpectator()) {
                        player.sendSystemMessage(Component.translatable("spacecraft.warning.oxygen_needed").withStyle(ChatFormatting.GRAY));
                        player.hurt(DamageSource.ANVIL, 2);
                    }
                }
            }
        }
    }
}

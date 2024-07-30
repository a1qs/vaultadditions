package io.github.a1qs.vaultadditions.events;

import io.github.a1qs.vaultadditions.VaultAdditions;
import io.github.a1qs.vaultadditions.data.DataManager;
import io.github.a1qs.vaultadditions.data.WorldBorderData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

public class OnPlayerLogInEvent {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {

        Level level = event.getPlayer().getLevel();
        if(level instanceof ServerLevel serverLevel) {
            WorldBorderData data = DataManager.get(serverLevel);
            MinecraftServer srv = ServerLifecycleHooks.getCurrentServer();
            WorldBorder border = srv.overworld().getWorldBorder();
            if(data.getWorldBorderSize() != border.getSize() && data.getWorldBorderSize() != 0) {
                border.setSize(data.getWorldBorderSize());
                VaultAdditions.LOGGER.info("Reset Border to its last saved value!");
            }
        }
    }
}

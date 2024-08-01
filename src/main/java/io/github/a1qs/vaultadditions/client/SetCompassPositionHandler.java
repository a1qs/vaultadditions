package io.github.a1qs.vaultadditions.client;


import io.github.a1qs.vaultadditions.init.ModKeyBinds;
import io.github.a1qs.vaultadditions.network.CompassTargetRequestPacket;
import io.github.a1qs.vaultadditions.init.ModNetwork;
import io.github.a1qs.vaultadditions.util.CurioUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class SetCompassPositionHandler {
    private static boolean isKeyPressed = false;
    private static long keyPressStartTime = 0;
    private static boolean targetSet = false;
    private static long lastSoundTime = 0;
    private static int pitch = 0;

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (ModKeyBinds.setCompassPosition.isDown()) {
            if (!isKeyPressed) {
                isKeyPressed = true;
                targetSet = false;
                keyPressStartTime = System.currentTimeMillis();
                pitch = 0;
                lastSoundTime = 0;
            }
        } else {
            if (isKeyPressed) {
                isKeyPressed = false;
                keyPressStartTime = 0;
                targetSet = false;
                lastSoundTime = 0;
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (isKeyPressed && !targetSet) {
            long currentTime = System.currentTimeMillis();
            Minecraft minecraft = Minecraft.getInstance();
            Player player = minecraft.player;
            if (player != null && CurioUtils.hasVaultCompass(player)) {
                if (player.getLevel().dimension().location() != ClientLevel.OVERWORLD.location() && player.getLevel().dimension().location() != ClientLevel.NETHER.location() && player.getLevel().dimension().location() != ClientLevel.END.location()) {
                    playSoundWhileUsing(player);
                }
            }
            if (currentTime - keyPressStartTime >= 2000) {
                if (player != null) {
                    ModNetwork.CHANNEL.sendToServer(new CompassTargetRequestPacket(minecraft.player.getUUID()));
                }
                targetSet = true;
            }
        }
    }

    private void playSoundWhileUsing(Player player) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastSoundTime >= 250) {
            player.playSound(SoundEvents.NOTE_BLOCK_BELL, 1.0F, (float) pitch / 40.0F);
            lastSoundTime = currentTime;
            pitch += 5;
        }
    }
}

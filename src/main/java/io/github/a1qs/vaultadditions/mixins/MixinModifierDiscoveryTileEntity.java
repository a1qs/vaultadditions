package io.github.a1qs.vaultadditions.mixins;

import io.github.a1qs.vaultadditions.util.DiscoveryModifierHelper;
import iskallia.vault.block.entity.ModifierDiscoveryTileEntity;
import iskallia.vault.core.random.JavaRandom;
import iskallia.vault.core.vault.Vault;
import iskallia.vault.core.vault.modifier.spi.VaultModifier;
import iskallia.vault.core.vault.player.Listener;
import iskallia.vault.world.data.ServerVaults;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ModifierDiscoveryTileEntity.class, remap = false)
public class MixinModifierDiscoveryTileEntity {
    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;sendMessage(Lnet/minecraft/network/chat/Component;Ljava/util/UUID;)V", shift = At.Shift.AFTER))
    public void use(ServerPlayer player, CallbackInfo ci) {
        Vault vault = ServerVaults.get(player.getLevel()).orElse(null);
        System.out.println(player.getLevel());
        if(vault != null) {
            VaultModifier<?> modifier = DiscoveryModifierHelper.getRandomPositiveModifier();
            vault.get(Vault.MODIFIERS).addModifier(modifier, 1, true, JavaRandom.ofInternal(0));
            TextComponent text = new TextComponent("");
            Component modifierText = modifier.getChatDisplayNameComponent(1);
            text.append(player.getDisplayName());
            text.append(" has unlocked all Crafting modifiers and added ");
            text.append(modifierText);
            text.append(" to the Vault instead!");

            for (Listener listener : vault.get(Vault.LISTENERS).getAll()) {
                listener.getPlayer().ifPresent((other) -> {
                    player.getLevel().playSound(null, other.getX(), other.getY(), other.getZ(), SoundEvents.NOTE_BLOCK_BELL, SoundSource.PLAYERS, 0.9F, 1.2F);
                    other.displayClientMessage(text, false);
                });
            }
        }
    }
}

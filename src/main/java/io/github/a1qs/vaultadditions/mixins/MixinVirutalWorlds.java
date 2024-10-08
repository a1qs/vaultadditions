package io.github.a1qs.vaultadditions.mixins;

import iskallia.vault.world.data.VirtualWorlds;
import net.minecraft.world.level.border.BorderChangeListener;
import net.minecraft.world.level.border.WorldBorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = VirtualWorlds.class)
public class MixinVirutalWorlds {
    // Ignore the WorldBorder Listener set for the Vault :3
    @Redirect(method = "load(Liskallia/vault/core/world/storage/VirtualWorld;)Liskallia/vault/core/world/storage/VirtualWorld;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/border/BorderChangeListener$DelegateBorderChangeListener;<init>(Lnet/minecraft/world/level/border/WorldBorder;)V"))
    private static void redirectListener(BorderChangeListener.DelegateBorderChangeListener instance, WorldBorder pWorldBorder) { }
}

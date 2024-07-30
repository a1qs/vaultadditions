package io.github.a1qs.vaultadditions.util;

import iskallia.vault.init.ModItems;
import net.minecraft.world.entity.player.Player;
import top.theillusivec4.curios.api.CuriosApi;

public class CurioUtils {
    public static boolean hasVaultCompass(Player player) {
        return CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.VAULT_COMPASS).isPresent();
    }
}

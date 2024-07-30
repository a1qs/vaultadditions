package io.github.a1qs.vaultadditions.mixins;

import io.github.a1qs.vaultadditions.config.CommonConfigs;
import io.github.a1qs.vaultadditions.init.ModItems;
import iskallia.vault.core.random.ChunkRandom;
import iskallia.vault.core.vault.Vault;
import iskallia.vault.core.vault.objective.AwardCrateObjective;
import iskallia.vault.core.vault.player.Listener;
import iskallia.vault.core.vault.stat.StatCollector;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(value = AwardCrateObjective.class, remap = false)
public class MixinAwardCrateObjective {

    @Inject(method = "awardCrate", at = @At("RETURN"))
    private void addBorderShardReward(Vault vault, Listener listener, ChunkRandom random, CallbackInfo ci) {
        StatCollector stats = vault.get(Vault.STATS).get(listener.get(Listener.ID));

        stats.get(StatCollector.REWARD).add(new ItemStack(ModItems.BORDER_SHARD.get(), random.nextInt(CommonConfigs.VAULT_COMPLETION_GRANT_BORDER_SHARD_AMOUNT_MIN.get(), CommonConfigs.VAULT_COMPLETION_GRANT_BORDER_SHARD_AMOUNT_MAX.get())));
    }

}

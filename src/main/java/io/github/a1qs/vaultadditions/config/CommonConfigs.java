package io.github.a1qs.vaultadditions.config;

import iskallia.vault.block.entity.AlchemyArchiveTileEntity;
import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;


    public static final ForgeConfigSpec.ConfigValue<Integer> BORDER_SHARD_INCREASE;
    public static final ForgeConfigSpec.ConfigValue<Integer> INVERTED_BORDER_SHARD_DECREASE;
    public static final ForgeConfigSpec.ConfigValue<Integer> VAULT_COMPLETION_GRANT_BORDER_SHARD_AMOUNT_MIN;
    public static final ForgeConfigSpec.ConfigValue<Integer> VAULT_COMPLETION_GRANT_BORDER_SHARD_AMOUNT_MAX;

    static {
        BUILDER.push("VaultAdditions Common Configs");


        BORDER_SHARD_INCREASE = BUILDER.comment("Modify the amount of blocks the Border Shard increases with its usage")
                        .define("BORDER_SHARD_INCREASE", 5);

        INVERTED_BORDER_SHARD_DECREASE = BUILDER.comment("Modify the amount of blocks the Inverted Border Shard decreases with its usage")
                .define("INVERTED_BORDER_SHARD_DECREASE", -5);

        VAULT_COMPLETION_GRANT_BORDER_SHARD_AMOUNT_MIN = BUILDER.comment("How many Border Shards should be granted upon Vault Completion\nMay not be greater than the Maximum Border Shard Grant Amount")
                .defineInRange("VAULT_COMPLETION_GRANT_BORDER_SHARD_AMOUNT_MIN", 1, 0, 64);

        VAULT_COMPLETION_GRANT_BORDER_SHARD_AMOUNT_MAX = BUILDER.comment("How many maximum Border Shards should be granted upon Vault Completion\nMay not be lesser than the Minimum Border Shard Grant Amount")
                .defineInRange("VAULT_COMPLETION_GRANT_BORDER_SHARD_AMOUNT_MAX", 1, 0, 64);



        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}

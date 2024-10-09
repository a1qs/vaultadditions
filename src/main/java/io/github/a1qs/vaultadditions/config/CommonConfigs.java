package io.github.a1qs.vaultadditions.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;


    public static final ForgeConfigSpec.ConfigValue<Integer> BORDER_GEMSTONE_INCREASE;
    public static final ForgeConfigSpec.ConfigValue<Integer> NETHER_BORDER_INCREASE;
    public static final ForgeConfigSpec.ConfigValue<Integer> END_BORDER_INCREASE;
    static {
        BUILDER.push("VaultAdditions Common Configs");

        BORDER_GEMSTONE_INCREASE = BUILDER.comment("Modify the amount of blocks the Border Gemstone increases with its usage")
                        .define("BORDER_GEMSTONE_INCREASE", 5);

        NETHER_BORDER_INCREASE = BUILDER.comment("The multiplier when increasing the border inside the Nether")
                .define("NETHER_BORDER_INCREASE", 8);

        END_BORDER_INCREASE = BUILDER.comment("The multiplier when increasing the border inside the Nether")
                .define("END_BORDER_INCREASE", 4);



        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}

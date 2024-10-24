package io.github.a1qs.vaultadditions.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;


    public static final ForgeConfigSpec.ConfigValue<Integer> BORDER_GEMSTONE_INCREASE;
    public static final ForgeConfigSpec.ConfigValue<Integer> NETHER_BORDER_INCREASE;
    public static final ForgeConfigSpec.ConfigValue<Integer> END_BORDER_INCREASE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> LIMIT_TIME_FOR_EXPANSION;
    public static final ForgeConfigSpec.ConfigValue<String> STOP_ACCEPTING_GEMSTONES_DATE;

    static {
        BUILDER.push("VaultAdditions Common Configs");

        BORDER_GEMSTONE_INCREASE = BUILDER.comment("Modify the amount of blocks the Border Gemstone increases with its usage")
                        .define("BORDER_GEMSTONE_INCREASE", 5);

        NETHER_BORDER_INCREASE = BUILDER.comment("The multiplier when increasing the border inside the Nether")
                .define("NETHER_BORDER_INCREASE", 8);

        END_BORDER_INCREASE = BUILDER.comment("The multiplier when increasing the border inside the Nether")
                .define("END_BORDER_INCREASE", 4);

        LIMIT_TIME_FOR_EXPANSION = BUILDER.comment("Whether there should be a time limit on when to prevent Border Expansion")
                .define("LIMIT_TIME_FOR_EXPANSION", false);

        STOP_ACCEPTING_GEMSTONES_DATE = BUILDER.comment("The date when Gemstones will no longer be accepted by the Globe Expander, based off of Local server time. (TT/MM/YYYY, HH:MM:SS)")
                .define("STOP_ACCEPTING_GEMSTONES_DATE", "01/01/2030 20:00:00");

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}

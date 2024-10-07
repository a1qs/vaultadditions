package io.github.a1qs.vaultadditions.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;


    public static final ForgeConfigSpec.ConfigValue<Integer> BORDER_GEMSTONE_INCREASE;
    static {
        BUILDER.push("VaultAdditions Common Configs");

        BORDER_GEMSTONE_INCREASE = BUILDER.comment("Modify the amount of blocks the Border Gemstone increases with its usage")
                        .define("BORDER_GEMSTONE_INCREASE", 5);



        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}

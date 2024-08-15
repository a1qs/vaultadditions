package io.github.a1qs.vaultadditions.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> COMPASS_X_OFFSET;
    public static final ForgeConfigSpec.ConfigValue<Integer> COMPASS_Y_OFFSET;

    static {
        BUILDER.push("Vaultadditions Client Configs");

        COMPASS_X_OFFSET = BUILDER.comment("Offset the Compass from the default position (above XP bar) on the x-axis").define("COMPASS_X_OFFSET", 0);
        COMPASS_Y_OFFSET = BUILDER.comment("Offset the Compass from the default position (above XP bar) on the y-axis").define("COMPASS_Y_OFFSET", 0);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}

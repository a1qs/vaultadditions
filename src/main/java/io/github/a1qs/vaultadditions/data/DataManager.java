package io.github.a1qs.vaultadditions.data;

import net.minecraft.server.level.ServerLevel;

public class DataManager {
    private static final String FILE_NAME = "vaultadditions_data";

    public static WorldBorderData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(WorldBorderData::load, WorldBorderData::new, FILE_NAME);
    }
}

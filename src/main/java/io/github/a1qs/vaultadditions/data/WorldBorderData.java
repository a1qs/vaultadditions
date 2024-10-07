package io.github.a1qs.vaultadditions.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

public class WorldBorderData extends SavedData {
    private static final String FILE_NAME = "vaultadditions_data";
    private double worldBorderSize;

    public WorldBorderData() {
        this.worldBorderSize = 0;
    }

    public static WorldBorderData load(CompoundTag compoundTag) {
        WorldBorderData data = new WorldBorderData();
        data.worldBorderSize = compoundTag.getInt("worldBorderSize");
        return data;
    }

    @Override
    public @NotNull CompoundTag save(CompoundTag tag) {
        tag.putDouble("worldBorderSize", worldBorderSize);
        return tag;
    }

    public double getWorldBorderSize() {
        return worldBorderSize;
    }

    public void setWorldBorderSize(double size) {
        this.worldBorderSize = size;
        setDirty();
    }

    public static WorldBorderData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(WorldBorderData::load, WorldBorderData::new, FILE_NAME);
    }



}

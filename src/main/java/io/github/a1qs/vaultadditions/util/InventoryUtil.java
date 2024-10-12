package io.github.a1qs.vaultadditions.util;

import io.github.a1qs.vaultadditions.init.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class InventoryUtil {

    public static boolean hasTreasureRing(Player player) {
        for (int i = 0; player.getInventory().getContainerSize() > i; ++i) {
            ItemStack stack = player.getInventory().getItem(i);
            if(stack.getItem() == ModItems.TREASURE_KEY_RING.get()) return true;
        }
        return false;
    }
    public static ItemStack getTreasureRing(Player player) {
        for (int i = 0; player.getInventory().getContainerSize() > i; ++i) {
            ItemStack stack = player.getInventory().getItem(i);
            if(stack.getItem() == ModItems.TREASURE_KEY_RING.get()) return stack;
        }
        return null;
    }

    public static void serializeInventory(ItemStackHandler itemHandler, ItemStack stack) {
        CompoundTag nbt = stack.hasTag() ? stack.getTag() : new CompoundTag();
        nbt.put("treasure_keys", itemHandler.serializeNBT());
        stack.setTag(nbt);
    }

    public static ItemStackHandler createStackInventoryHandler(int slots, ItemStack stack) {
        ItemStackHandler handler = new ItemStackHandler(slots);
        CompoundTag nbt = stack.hasTag() ? stack.getTag() : new CompoundTag();
        handler.deserializeNBT(nbt.getCompound("treasure_keys"));

        return handler;
    }
}

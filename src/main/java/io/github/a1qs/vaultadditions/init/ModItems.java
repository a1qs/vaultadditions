package io.github.a1qs.vaultadditions.init;

import io.github.a1qs.vaultadditions.VaultAdditions;
import io.github.a1qs.vaultadditions.item.BorderGemstone;
import io.github.a1qs.vaultadditions.item.TreasureKeyRing;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, VaultAdditions.MOD_ID);

    public static final RegistryObject<Item> BORDER_GEMSTONE = ITEMS.register("border_gemstone",
            () -> new BorderGemstone(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    public static final RegistryObject<Item> TREASURE_KEY_RING = ITEMS.register("treasure_key_ring",
            () -> new TreasureKeyRing(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

}

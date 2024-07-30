package io.github.a1qs.vaultadditions.init;

import io.github.a1qs.vaultadditions.VaultAdditions;
import io.github.a1qs.vaultadditions.item.BorderShard;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, VaultAdditions.MOD_ID);


    public static final RegistryObject<Item> BORDER_SHARD = ITEMS.register("border_shard",
            () -> new BorderShard(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    public static final RegistryObject<Item> INVERTED_BORDER_SHARD = ITEMS.register("inverted_border_shard",
            () -> new BorderShard(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

}

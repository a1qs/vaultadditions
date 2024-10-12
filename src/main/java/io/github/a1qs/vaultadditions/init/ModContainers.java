package io.github.a1qs.vaultadditions.init;

import io.github.a1qs.vaultadditions.VaultAdditions;
import io.github.a1qs.vaultadditions.gui.container.TreasureKeyRingContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, VaultAdditions.MOD_ID);


    public static final RegistryObject<MenuType<TreasureKeyRingContainer>> TREASURE_KEY_RING_CONTAINER =
            registerMenuContainer((windowId, inv, data) -> new TreasureKeyRingContainer(windowId, inv, data.readItem()), "treasure_key_container");

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuContainer(IContainerFactory<T> factory, String name) {
        return CONTAINERS.register(name, () -> IForgeMenuType.create(factory));
    }
}

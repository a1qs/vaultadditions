package io.github.a1qs.vaultadditions.util;

import iskallia.vault.core.vault.modifier.registry.VaultModifierRegistry;
import iskallia.vault.core.vault.modifier.spi.VaultModifier;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiscoveryModifierHelper {
    private static final List<VaultModifier<?>> POSITIVE_MODIFIER_LIST = new ArrayList<>();

    public static VaultModifier<?> getRandomPositiveModifier() {
        Random random = new Random();
        return POSITIVE_MODIFIER_LIST.get(random.nextInt(POSITIVE_MODIFIER_LIST.size()));
    }

    static {
        POSITIVE_MODIFIER_LIST.add(VaultModifierRegistry.get(new ResourceLocation("the_vault", "energizing")));
        POSITIVE_MODIFIER_LIST.add(VaultModifierRegistry.get(new ResourceLocation("the_vault", "swift")));
        POSITIVE_MODIFIER_LIST.add(VaultModifierRegistry.get(new ResourceLocation("the_vault", "stronk")));
        POSITIVE_MODIFIER_LIST.add(VaultModifierRegistry.get(new ResourceLocation("the_vault", "item_quantity")));
        POSITIVE_MODIFIER_LIST.add(VaultModifierRegistry.get(new ResourceLocation("the_vault", "extended")));
        POSITIVE_MODIFIER_LIST.add(VaultModifierRegistry.get(new ResourceLocation("the_vault", "soul_boost")));
        POSITIVE_MODIFIER_LIST.add(VaultModifierRegistry.get(new ResourceLocation("the_vault", "pristine")));
    }

}

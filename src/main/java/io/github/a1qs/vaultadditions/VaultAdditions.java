package io.github.a1qs.vaultadditions;

import com.mojang.logging.LogUtils;
import io.github.a1qs.vaultadditions.block.blockentity.render.GlobeExpanderEntityRenderer;
import io.github.a1qs.vaultadditions.config.CommonConfigs;
import io.github.a1qs.vaultadditions.events.OnPlayerLogInEvent;
import io.github.a1qs.vaultadditions.init.ModBlockEntities;
import io.github.a1qs.vaultadditions.init.ModBlocks;
import io.github.a1qs.vaultadditions.init.ModItems;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(VaultAdditions.MOD_ID)
public class VaultAdditions {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "vaultadditions";
    public static boolean isWorldBorderFixerLoaded = ModList.get().isLoaded("worldborderfixer");

    public VaultAdditions() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(eventBus);
        ModBlocks.BLOCKS.register(eventBus);
        ModBlockEntities.register(eventBus);

        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::clientSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfigs.SPEC, "vaultadditions-common.toml");

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(OnPlayerLogInEvent::onPlayerLogin);

        if(FMLEnvironment.dist == Dist.DEDICATED_SERVER) {
            if(!isWorldBorderFixerLoaded) LOGGER.error("Worldborderfixer is not installed. Please install 'Multi World Borders Unofficial'!");
        }
    }


    public void commonSetup(final FMLCommonSetupEvent event) {
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        BlockEntityRenderers.register(ModBlockEntities.GLOBE_EXPANDER_ENTITY.get(), GlobeExpanderEntityRenderer::new);
    }
}

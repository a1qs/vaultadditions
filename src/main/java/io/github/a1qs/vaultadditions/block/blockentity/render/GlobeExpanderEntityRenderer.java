package io.github.a1qs.vaultadditions.block.blockentity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import io.github.a1qs.vaultadditions.block.blockentity.GlobeExpanderBlockEntity;
import io.github.a1qs.vaultadditions.init.ModItems;
import iskallia.vault.block.entity.ScavengerAltarTileEntity;
import iskallia.vault.block.render.AlchemyTableRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GlobeExpanderEntityRenderer implements BlockEntityRenderer<GlobeExpanderBlockEntity> {

    public GlobeExpanderEntityRenderer(BlockEntityRendererProvider.Context context) {}


    @Override
    public void render(GlobeExpanderBlockEntity blockEntity, float partialTicks, PoseStack matrixStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        if (!blockEntity.shouldAnimate()) {
            return;  // If it's not animating, don't render anything.
        }

        Level world = blockEntity.getLevel();
        if (world != null) {
            float lerpTicks = Mth.lerp(partialTicks, blockEntity.ticksSpinningOld, blockEntity.ticksSpinning);
            float percentConsumed = lerpTicks / (float) blockEntity.maxSpinTicks;

            ItemStack itemStack = new ItemStack(ModItems.BORDER_GEMSTONE.get());

            matrixStack.pushPose();
            this.renderItem(matrixStack, bufferSource, combinedLight, combinedOverlay, 1.5F - percentConsumed * percentConsumed * percentConsumed, (1.0F - percentConsumed * percentConsumed) * 0.4F + 0.1F, itemStack, blockEntity, partialTicks);
            matrixStack.popPose();
        }

    }

    private void renderItem(PoseStack matrixStack, MultiBufferSource buffer, int lightLevel, int overlay, float yOffset, float scale, ItemStack itemStack, GlobeExpanderBlockEntity blockEntity, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        matrixStack.pushPose();

        matrixStack.translate(0.5, yOffset, 0.5);
        matrixStack.scale(scale, scale, scale);

        float lerp = Mth.lerp(partialTicks, blockEntity.ticksSpinningOld, blockEntity.ticksSpinning);
        float spinSpeed = (40.0F - lerp) * (40.0F - lerp);
        double rotation = -10.0 * ((double) System.currentTimeMillis() / 1000.0 + (double)(spinSpeed / 25.0F)) % 360.0 * 0.017453292519943295;
        matrixStack.mulPose(Quaternion.fromXYZ(0.0F, (float) rotation, 0.0F));

        BakedModel bakedModel = minecraft.getItemRenderer().getModel(itemStack, null, null, 0);
        minecraft.getItemRenderer().render(itemStack, ItemTransforms.TransformType.FIXED, true, matrixStack, buffer, lightLevel, overlay, bakedModel);

        matrixStack.popPose();
    }
}




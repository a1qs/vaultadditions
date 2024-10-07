package io.github.a1qs.vaultadditions.block;

import io.github.a1qs.vaultadditions.block.blockentity.GlobeExpanderBlockEntity;
import io.github.a1qs.vaultadditions.config.CommonConfigs;
import io.github.a1qs.vaultadditions.data.WorldBorderData;
import io.github.a1qs.vaultadditions.init.ModBlockEntities;
import io.github.a1qs.vaultadditions.init.ModItems;
import io.github.a1qs.vaultadditions.item.BorderGemstone;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;

import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GlobeExpanderBlock extends BaseEntityBlock {
    private static final VoxelShape SHAPE = Shapes.box(.1, .1, .1, .9, .9, .9);

    public GlobeExpanderBlock(Properties properties) {
        super(properties);
    }
    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @javax.annotation.Nullable BlockGetter pLevel, List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        pTooltip.add(new TextComponent("Upon clicking with a Border Shard:").withStyle(ChatFormatting.YELLOW));
        pTooltip.add(new TextComponent("Expands the World Border by a configured amount").withStyle(ChatFormatting.YELLOW));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if(pLevel.isClientSide()) {
            return InteractionResult.PASS;
        }

        if(!(pPlayer.getMainHandItem().getItem() instanceof BorderGemstone && pHand == InteractionHand.MAIN_HAND)) {
            return InteractionResult.PASS;
        }

        if (!(pLevel instanceof ServerLevel serverLevel)) {
            return InteractionResult.PASS;
        }


        MinecraftServer srv = ServerLifecycleHooks.getCurrentServer();
        WorldBorder border = srv.overworld().getWorldBorder();
        WorldBorderData data = WorldBorderData.get(serverLevel);
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);

        if(blockEntity instanceof GlobeExpanderBlockEntity expanderEntity) {
            if(pPlayer.getMainHandItem().getItem() == ModItems.BORDER_GEMSTONE.get()) {
                int borderShardIncrease = CommonConfigs.BORDER_GEMSTONE_INCREASE.get();
                double blocksExpanded = pPlayer.getMainHandItem().getCount() * borderShardIncrease;
                double newSize = border.getSize() + blocksExpanded;
                border.lerpSizeBetween(border.getSize(), newSize, 1000);
                data.setWorldBorderSize(newSize);
                serverLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS, 0.75F, 0.9F);

                if (!pPlayer.getAbilities().instabuild) {
                    pPlayer.getMainHandItem().setCount(0);
                }

                expanderEntity.resetSpinTime();
                expanderEntity.setChanged();
                pLevel.sendBlockUpdated(pPos, expanderEntity.getBlockState(), expanderEntity.getBlockState(), 3);

                srv.getPlayerList().broadcastMessage(new TextComponent("[World Border] " + pPlayer.getDisplayName().getString() + " expanded the World border by " + blocksExpanded + " Blocks!").withStyle(ChatFormatting.YELLOW), ChatType.CHAT, Util.NIL_UUID);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide && pBlockEntityType == ModBlockEntities.GLOBE_EXPANDER_ENTITY.get()) {
            return GlobeExpanderBlockEntity::clientTick;
        }
        return pLevel.isClientSide() ? GlobeExpanderBlockEntity::clientTick : GlobeExpanderBlockEntity::serverTick;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new GlobeExpanderBlockEntity(blockPos, blockState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

}

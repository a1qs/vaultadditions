package io.github.a1qs.vaultadditions.block;

import io.github.a1qs.vaultadditions.config.CommonConfigs;
import io.github.a1qs.vaultadditions.data.DataManager;
import io.github.a1qs.vaultadditions.data.WorldBorderData;
import io.github.a1qs.vaultadditions.init.ModItems;
import io.github.a1qs.vaultadditions.item.BorderShard;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GlobeExpander extends Block {

    private static final VoxelShape SHAPE = Shapes.box(.1, .1, .1, .9, .9, .9);

    public GlobeExpander(Properties properties) {
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
        if(!pLevel.isClientSide()) {
            if(pPlayer.getMainHandItem().getItem() instanceof BorderShard && pHand == InteractionHand.MAIN_HAND) {
                MinecraftServer srv = ServerLifecycleHooks.getCurrentServer();

                WorldBorder border = srv.overworld().getWorldBorder();
                if(pLevel instanceof ServerLevel serverLevel) {
                    WorldBorderData data = DataManager.get(serverLevel);
                    if(pPlayer.getMainHandItem().getItem() == ModItems.BORDER_SHARD.get()) {
                        int borderShardIncrease = CommonConfigs.BORDER_SHARD_INCREASE.get();
                        double blocksExpanded = pPlayer.getMainHandItem().getCount() * borderShardIncrease;
                        double newSize = border.getSize() + blocksExpanded;
                        border.lerpSizeBetween(border.getSize(), newSize, 1000);
                        data.setWorldBorderSize(newSize);
                        serverLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS,  0.75F, 0.9F);

                        if(!pPlayer.getAbilities().instabuild) {
                            pPlayer.getMainHandItem().setCount(0);
                        }
                        srv.getPlayerList().broadcastMessage(new TextComponent( "[World Border] " + pPlayer.getDisplayName().getString() +  " expanded the World border by " + blocksExpanded + " Blocks!").withStyle(ChatFormatting.YELLOW), ChatType.CHAT, Util.NIL_UUID);
                    } else {
                        double blocksRemoved = (double) pPlayer.getMainHandItem().getCount() * CommonConfigs.INVERTED_BORDER_SHARD_DECREASE.get();
                        double newSize = border.getSize() + blocksRemoved;
                        if(newSize > 5) {
                            border.lerpSizeBetween(border.getSize(), newSize, 1000);
                            data.setWorldBorderSize(newSize);
                            serverLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS,  0.75F, 0.9F);

                            if(!pPlayer.getAbilities().instabuild) {
                                pPlayer.getMainHandItem().setCount(0);
                            }
                            srv.getPlayerList().broadcastMessage(new TextComponent( "[World Border] " + pPlayer.getDisplayName().getString() +  " shrunk the World border by " + blocksRemoved + " Blocks!").withStyle(ChatFormatting.RED), ChatType.CHAT, Util.NIL_UUID);

                        } else {
                            srv.getPlayerList().broadcastMessage(new TextComponent( "[World Border] " + pPlayer.getDisplayName().getString() +  " attempted to shrink the border to less than 5 Blocks! ").withStyle(ChatFormatting.RED), ChatType.CHAT, Util.NIL_UUID);
                        }
                    }



                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }


}

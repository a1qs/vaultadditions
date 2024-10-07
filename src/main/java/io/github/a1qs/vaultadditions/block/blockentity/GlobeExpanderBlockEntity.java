package io.github.a1qs.vaultadditions.block.blockentity;

import io.github.a1qs.vaultadditions.init.ModBlockEntities;
import iskallia.vault.block.ScavengerAltarBlock;
import iskallia.vault.block.entity.ScavengerAltarTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class GlobeExpanderBlockEntity extends BlockEntity {
    public int ticksSpinning = 0;  // Track the number of ticks the item has been spinning
    public int ticksSpinningOld = 0;  // Previous tick count for smooth interpolation
    public final int maxSpinTicks = 100;  // Max time the item should spin for (example)
    private boolean isAnimating = false;  // Track if the block should animate

    public GlobeExpanderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GLOBE_EXPANDER_ENTITY.get(), pos, state);
    }

    public static <T extends BlockEntity> void clientTick(Level world, BlockPos pos, BlockState state, T tile) {
        if (tile instanceof GlobeExpanderBlockEntity expanderEntity) {
            if (expanderEntity.isAnimating && expanderEntity.ticksSpinning < expanderEntity.maxSpinTicks) {
                expanderEntity.ticksSpinningOld = expanderEntity.ticksSpinning;
                expanderEntity.ticksSpinning++;
            }
        }
        //Minecraft.getInstance().getSoundManager().play(SoundEvents.END_PORTAL_FRAME_FILL);
    }

    public static <T extends BlockEntity> void serverTick(Level world, BlockPos pos, BlockState state, T tile) {
        if (tile instanceof GlobeExpanderBlockEntity expanderEntity) {
            if (expanderEntity.isAnimating && expanderEntity.ticksSpinning < expanderEntity.maxSpinTicks) {
                expanderEntity.ticksSpinningOld = expanderEntity.ticksSpinning;
                expanderEntity.ticksSpinning++;
                expanderEntity.setChanged();  // Mark that the entity has been updated
            } else if (expanderEntity.ticksSpinning >= expanderEntity.maxSpinTicks) {
                expanderEntity.stopAnimation();  // Stop animation on the server
                world.playSound(null, pos, SoundEvents.ENDER_EYE_DEATH, SoundSource.BLOCKS, 1.0f, 1.25f);

            }
        }
    }


    public void resetSpinTime() {
        this.ticksSpinningOld = 0;
        this.ticksSpinning = 0;
        this.isAnimating = true;  // Start the animation
        this.setChanged();  // Notify the system that this block entity has been updated
    }

    public void stopAnimation() {
        this.isAnimating = false;  // Stop the animation
        this.ticksSpinning = 0;
        this.ticksSpinningOld = 0;

        // Mark the block entity as changed to save data to NBT
        this.setChanged();

        // Force a block update to sync NBT data with the client
        if (this.level != null && !this.level.isClientSide()) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        }
    }

    public boolean shouldAnimate() {
        return isAnimating;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        tag.putBoolean("IsAnimating", this.isAnimating);
        tag.putInt("TicksSpinning", this.ticksSpinning);
        tag.putInt("TicksSpinningOld", this.ticksSpinningOld);
    }

    public void load(CompoundTag tag) {
        super.load(tag);

        // Read animation state and ticks from NBT
        this.isAnimating = tag.getBoolean("IsAnimating");
        this.ticksSpinning = tag.getInt("TicksSpinning");
        this.ticksSpinningOld = tag.getInt("TicksSpinningOld");
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

}

package io.github.a1qs.vaultadditions.block.blockentity;

import io.github.a1qs.vaultadditions.init.ModBlockEntities;
import iskallia.vault.block.ScavengerAltarBlock;
import iskallia.vault.block.entity.ScavengerAltarTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;

public class GlobeExpanderBlockEntity extends BlockEntity {
    public int ticksSpinning = 0;  // Track the number of ticks the item has been spinning
    public int ticksSpinningOld = 0;  // Previous tick count for smooth interpolation
    public final int maxSpinTicks = 65;  // Max time the item should spin for (example)
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

                BlockPos crystalPos = new BlockPos(pos.getX(), pos.getY() + 1.5, pos.getZ());
                world.levelEvent(2003, crystalPos,0);
//                double d0 = (double)pos.getX() + 0.5;
//                double d13 = pos.getY();
//                double d17 = (double)pos.getZ() + 0.5;
//
//                for(int i = 0; i < 8; ++i) {
//                    world.addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.ENDER_EYE)), d0, d13, d17, world.getRandom().nextGaussian() * 0.15, world.getRandom().nextDouble() * 0.2, world.getRandom().nextGaussian() * 0.15);
//                }
//
//                for(double d22 = 0.0; d22 < 6.283185307179586; d22 += 0.15707963267948966) {
//                    world.addParticle(ParticleTypes.PORTAL, d0 + Math.cos(d22) * 5.0, d13 - 0.4, d17 + Math.sin(d22) * 5.0, Math.cos(d22) * -5.0, 0.0, Math.sin(d22) * -5.0);
//                    world.addParticle(ParticleTypes.PORTAL, d0 + Math.cos(d22) * 5.0, d13 - 0.4, d17 + Math.sin(d22) * 5.0, Math.cos(d22) * -7.0, 0.0, Math.sin(d22) * -7.0);
//                }
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

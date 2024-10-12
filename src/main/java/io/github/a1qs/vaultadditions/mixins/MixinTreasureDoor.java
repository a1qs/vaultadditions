package io.github.a1qs.vaultadditions.mixins;

import io.github.a1qs.vaultadditions.util.InventoryUtil;
import iskallia.vault.block.TreasureDoorBlock;
import iskallia.vault.core.event.CommonEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TreasureDoorBlock.class, remap = false)
public class MixinTreasureDoor {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void openDoorViaTreasureRing(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        TreasureDoorBlock door = ((TreasureDoorBlock) (Object) this);
        if(InventoryUtil.hasTreasureRing(player)) {
            ItemStack keyRing = InventoryUtil.getTreasureRing(player);

            if (keyRing.hasTag() && keyRing.getTag().contains("treasure_keys")) {
                CompoundTag treasureKeysTag = keyRing.getTag().getCompound("treasure_keys");
                ListTag itemsList = treasureKeysTag.getList("Items", Tag.TAG_COMPOUND);
                Boolean isOpen = state.getValue(TreasureDoorBlock.OPEN);
                Item requiredKey = state.getValue(TreasureDoorBlock.TYPE).getKey();

                if (!isOpen) {
                    for (int i = 0; i < itemsList.size(); i++) {
                        CompoundTag itemTag = itemsList.getCompound(i);
                        ItemStack keyStack = ItemStack.of(itemTag);
                        int slot = itemTag.getInt("Slot");

                        if (keyStack.getItem() == requiredKey) {
                            keyStack.shrink(1);

                            if (keyStack.isEmpty()) {
                                itemsList.remove(i);
                            } else {
                                CompoundTag updatedTag = new CompoundTag();
                                keyStack.save(updatedTag);
                                updatedTag.putInt("Slot", slot);
                                itemsList.set(i, updatedTag);
                            }

                            treasureKeysTag.put("Items", itemsList);
                            keyRing.getTag().put("treasure_keys", treasureKeysTag);

                            door.setOpen(player, world, state, pos, true);
                            CommonEvents.TREASURE_ROOM_OPEN.invoke(world, player, pos);
                            cir.setReturnValue(InteractionResult.SUCCESS);
                            break;
                        }
                    }
                }
            }
        }
    }
}

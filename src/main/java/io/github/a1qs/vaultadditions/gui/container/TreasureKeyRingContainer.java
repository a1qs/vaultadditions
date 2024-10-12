package io.github.a1qs.vaultadditions.gui.container;

import io.github.a1qs.vaultadditions.init.ModContainers;
import io.github.a1qs.vaultadditions.item.TreasureKeyRing;
import io.github.a1qs.vaultadditions.util.InventoryUtil;
import iskallia.vault.item.ItemVaultKey;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class TreasureKeyRingContainer extends AbstractContainerMenu {


    public ItemStackHandler itemStackHandler;

    public TreasureKeyRingContainer(int windowId, Inventory playerInventory, ItemStack stack) {
        super(ModContainers.TREASURE_KEY_RING_CONTAINER.get(), windowId);
        this.itemStackHandler = InventoryUtil.createStackInventoryHandler(9, stack);

        this.addSlot(new KeySlot(this.itemStackHandler, 0, 57, 12, "the_vault:key_sparkletine"));
        this.addSlot(new KeySlot(this.itemStackHandler, 1, 80, 9, "the_vault:key_xenium"));
        this.addSlot(new KeySlot(this.itemStackHandler, 2, 103, 12, "the_vault:key_iskallium"));
        this.addSlot(new KeySlot(this.itemStackHandler, 3, 53, 33, "the_vault:key_gorginite"));
        this.addSlot(new KeySlot(this.itemStackHandler, 4, 80, 33, "the_vault:key_tubium"));
        this.addSlot(new KeySlot(this.itemStackHandler, 5, 107, 33, "the_vault:key_ashium"));
        this.addSlot(new KeySlot(this.itemStackHandler, 6, 57, 54, "the_vault:key_petzanite"));
        this.addSlot(new KeySlot(this.itemStackHandler, 7, 80, 57, "the_vault:key_upaline"));
        this.addSlot(new KeySlot(this.itemStackHandler, 8, 103, 54, "the_vault:key_bomignite"));


        for (int row = 0; row < 9; ++row) {
            int x = 8 + row * 18;
            int y = 56 + 86;
            if (row == getSlotForItem(playerInventory, stack)) {
                addSlot(new UnmodifiableSlot(playerInventory, row, x, y));
                continue;
            }

            addSlot(new Slot(playerInventory, row, x, y));
        }

        for (int row = 1; row < 4; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = 8 + col * 18;
                int y = row * 18 + (56 + 10);
                addSlot(new Slot(playerInventory, col + row * 9, x, y));
            }
        }
    }

    @Override
    public void removed(Player playerIn) {
        InteractionHand hand = TreasureKeyRing.getHandForRing(playerIn);
        if (hand == null) return;

        InventoryUtil.serializeInventory(this.itemStackHandler, playerIn.getItemInHand(hand));

        CompoundTag nbt = playerIn.getItemInHand(hand).getTag();
        int[] slots = new int[this.itemStackHandler.getSlots()];
        for (int i = 0; i < slots.length; i++) {
            ItemStack stack = this.itemStackHandler.getStackInSlot(i);
            if (!stack.isEmpty()) slots[i] = stack.getCount();
        }
        playerIn.getItemInHand(hand).setTag(nbt);

        super.removed(playerIn);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return InventoryUtil.hasTreasureRing(playerIn);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            stack = slotStack.copy();
            if (index < this.itemStackHandler.getSlots()) {
                if (!this.moveItemStackTo(slotStack, this.itemStackHandler.getSlots(), this.slots.size(), true))
                    ;
                return ItemStack.EMPTY;
            } else if (!this.moveItemStackTo(slotStack, 0, this.itemStackHandler.getSlots(), false)) {
                return ItemStack.EMPTY;
            }
            if (slotStack.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();
        }
        return stack;
    }

    private static class UnmodifiableSlot extends Slot {
        public UnmodifiableSlot(Inventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }

        @Override
        public boolean mayPickup(Player playerIn) {
            return false;
        }

    }

    private static class KeySlot extends SlotItemHandler {
        private final String allowedKey;
        public KeySlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, String allowedKey) {
            super(itemHandler, index, xPosition, yPosition);
            this.allowedKey = allowedKey;
        }

        @Override
        public boolean mayPlace(@Nonnull ItemStack stack) {
            if (stack.getItem() instanceof ItemVaultKey) {
                String registryName = stack.getItem().getRegistryName().toString();
                return registryName.equals(this.allowedKey);
            }
            return false;
        }
    }



    public int getSlotForItem(Inventory inventory, ItemStack stack) {
        for (int i = 0; i < inventory.items.size(); ++i) {
            if (!inventory.items.get(i).isEmpty() && stackEqualExact(stack, inventory.items.get(i))) {
                return i;
            }
        }
        return -1;
    }

    private boolean stackEqualExact(ItemStack stack1, ItemStack stack2) {
        return stack1.getItem() == stack2.getItem() && ItemStack.isSameItemSameTags(stack1, stack2);
    }
}

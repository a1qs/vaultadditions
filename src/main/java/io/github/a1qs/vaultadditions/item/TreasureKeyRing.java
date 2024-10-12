package io.github.a1qs.vaultadditions.item;

import io.github.a1qs.vaultadditions.gui.container.TreasureKeyRingContainer;
import io.github.a1qs.vaultadditions.init.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import java.util.List;

public class TreasureKeyRing extends Item implements MenuProvider {
    public TreasureKeyRing(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (hand != InteractionHand.MAIN_HAND)
            return InteractionResultHolder.fail(player.getItemInHand(hand));

        if (!level.isClientSide() && !player.isShiftKeyDown()) {
            NetworkHooks.openGui((ServerPlayer) player, this, friendlyByteBuf -> friendlyByteBuf.writeItem(player.getItemInHand(hand)));
        }

        return super.use(level, player, hand);
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("container.vaultadditions.treasure_key_ring");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        InteractionHand hand = getHandForRing(player);
        return new TreasureKeyRingContainer(id, inventory, hand != null ? player.getItemInHand(hand) : ItemStack.EMPTY);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableComponent("tooltip.vaultadditions.treasure_key_ring"));
    }

    public static InteractionHand getHandForRing(Player player) {
        if (player.getMainHandItem().getItem() == ModItems.TREASURE_KEY_RING.get())
            return InteractionHand.MAIN_HAND;
        else if (player.getOffhandItem().getItem() == ModItems.TREASURE_KEY_RING.get())
            return InteractionHand.OFF_HAND;

        return null;
    }
}

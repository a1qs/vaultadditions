package io.github.a1qs.vaultadditions.item;

import io.github.a1qs.vaultadditions.config.CommonConfigs;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BorderGemstone extends Item {

    public BorderGemstone(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TextComponent("Increases the World Border by ").append(CommonConfigs.BORDER_GEMSTONE_INCREASE.get().toString()).withStyle(ChatFormatting.YELLOW).append(" Blocks!"));
    }
}

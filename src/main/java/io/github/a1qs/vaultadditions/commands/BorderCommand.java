package io.github.a1qs.vaultadditions.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.github.a1qs.vaultadditions.data.WorldBorderData;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.border.WorldBorder;

public class BorderCommand {
    public BorderCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("vaultadditions")
                .requires(sender -> sender.hasPermission(this.getRequiredPermissionLevel()))
                .then(Commands.literal("border")
                        .then(Commands.literal("setBorder")
                                .then(Commands.argument("diameter", DoubleArgumentType.doubleArg(1, 5.9999968E7))
                                        .executes(this::setWorldBorder)
                                )
                        )
                )
        );
    }

    private int setWorldBorder(CommandContext<CommandSourceStack> context) {
        double diameter = DoubleArgumentType.getDouble(context, "diameter");
        WorldBorder border = context.getSource().getLevel().getWorldBorder();
        WorldBorderData data = WorldBorderData.get(context.getSource().getLevel());
        border.setSize(diameter);

        MutableComponent cmp0 = new TextComponent(String.valueOf(diameter)).withStyle(ChatFormatting.YELLOW);
        MutableComponent cmp1 = (data.getWorldBorderSize() == 0.0) ? new TextComponent("\nInitialized Border!").withStyle(ChatFormatting.YELLOW) : new TextComponent("");
        data.setWorldBorderSize(diameter);
        MutableComponent cmp = new TextComponent("Set the World border to ").append(cmp0).append(" Blocks!").append(cmp1);
        context.getSource().sendSuccess(cmp, true);

        return 0;
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

}

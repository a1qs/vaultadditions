package io.github.a1qs.vaultadditions.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.github.a1qs.vaultadditions.config.CommonConfigs;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class ConfigCommand {
    public ConfigCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("vaultadditions")
                .requires(sender -> sender.hasPermission(this.getRequiredPermissionLevel()))
                .then(Commands.literal("config")
                        .then(Commands.literal("setBorderShardIncrease")
                                .then(Commands.argument("increaseAmount", IntegerArgumentType.integer())
                                        .executes(this::setShardIncrease)
                                )
                        )
                )
        );
    }

    private int setShardIncrease(CommandContext<CommandSourceStack> context) {
        CommonConfigs.BORDER_GEMSTONE_INCREASE.set(IntegerArgumentType.getInteger(context, "increaseAmount"));
        return 0;
    }



    public int getRequiredPermissionLevel() {
        return 2;
    }
}

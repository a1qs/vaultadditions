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
                        .then(Commands.literal("setInvertedBorderShardDecreases")
                                .then(Commands.argument("decreaseAmount", IntegerArgumentType.integer())
                                        .executes(this::setShardDecrease)
                                )
                        )
                        .then(Commands.literal("setCompletionBorderShardGrantAmount")
                                .then(Commands.argument("min", IntegerArgumentType.integer())
                                        .then(Commands.argument("max", IntegerArgumentType.integer())
                                                .executes(this::setCompletionShardAmount)
                                        )

                                )
                        )
                )
        );
    }

    private int setShardIncrease(CommandContext<CommandSourceStack> context) {
        CommonConfigs.BORDER_SHARD_INCREASE.set(IntegerArgumentType.getInteger(context, "increaseAmount"));
        return 0;
    }

    private int setShardDecrease(CommandContext<CommandSourceStack> context) {
        CommonConfigs.INVERTED_BORDER_SHARD_DECREASE.set(IntegerArgumentType.getInteger(context, "decreaseAmount"));
        return 0;
    }

    private int setCompletionShardAmount(CommandContext<CommandSourceStack> context) {
        CommonConfigs.VAULT_COMPLETION_GRANT_BORDER_SHARD_AMOUNT_MIN.set(IntegerArgumentType.getInteger(context, "min"));
        CommonConfigs.VAULT_COMPLETION_GRANT_BORDER_SHARD_AMOUNT_MAX.set(IntegerArgumentType.getInteger(context, "max"));
        return 0;
    }


    public int getRequiredPermissionLevel() {
        return 2;
    }
}

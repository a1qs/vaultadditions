package io.github.a1qs.vaultadditions.commands;

import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.github.a1qs.vaultadditions.VaultAdditions;
import io.github.a1qs.vaultadditions.util.JsonUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.nio.file.Path;

public class VaultLevelCommand {
    private static final String CONFIG_RELATIVE_PATH = "the_vault/vault_levels.json";

    public VaultLevelCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("vaultadditions")
                .requires(sender -> sender.hasPermission(this.getRequiredPermissionLevel()))
                .then(Commands.literal("vaultLevel")
                        .then(Commands.literal("setMaxLevel")
                                .then(Commands.argument("level", IntegerArgumentType.integer())
                                        .executes(this::setMaxLevel)
                                )

                        )

                )
        );
    }

    private int setMaxLevel(CommandContext<CommandSourceStack> context) {
        int maxLevel = IntegerArgumentType.getInteger(context, "level");

        Path configDir = FMLPaths.CONFIGDIR.get();
        File configFile = configDir.resolve(CONFIG_RELATIVE_PATH).toFile();
        JsonObject jsonObject = JsonUtils.readJsonFile(configFile);
        if (jsonObject == null) {
            VaultAdditions.LOGGER.error("Failed to read config JSON file: " + CONFIG_RELATIVE_PATH);
            return 1;
        }

        jsonObject.addProperty("maxLevel", maxLevel);
        boolean success = JsonUtils.writeJsonFile(configFile, jsonObject);

        if(success) {
            VaultAdditions.LOGGER.info("Successfully modified config JSON file: " + CONFIG_RELATIVE_PATH);
            String command = "/the_vault reloadcfg";

            MutableComponent cmp0 = new TextComponent("Reload Configs?").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.UNDERLINE);
            MutableComponent cmp1 = new TextComponent("To Update the Max Vault Level, Configs need to be Reloaded.\n").withStyle(ChatFormatting.YELLOW).append(cmp0);
            cmp0.withStyle((style) ->
                    style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent("Click to reload Configs!"))).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command)));
            context.getSource().sendSuccess(cmp1, true);

        } else {
            VaultAdditions.LOGGER.error("Failed to modify config JSON file: " + CONFIG_RELATIVE_PATH);
            context.getSource().sendFailure(new TextComponent("Failed to set a new Max Vault Level, check Console logs for more info."));
        }


        return 0;
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }
}

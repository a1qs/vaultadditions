package io.github.a1qs.vaultadditions.init;

import io.github.a1qs.vaultadditions.VaultAdditions;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class ModKeyBinds {
    public static KeyMapping setCompassPosition;
    public static KeyMapping toggleHud;

    public static void registerKeyBinds() {
        setCompassPosition = registerKeyMapping("set_compass_position", GLFW.GLFW_KEY_KP_SUBTRACT);
        toggleHud = registerKeyMapping("toggle_compass_hud", GLFW.GLFW_KEY_KP_MULTIPLY);

        ClientRegistry.registerKeyBinding(setCompassPosition);
        ClientRegistry.registerKeyBinding(toggleHud);
    }

    private static KeyMapping registerKeyMapping(String name, int keyCode) {
        return new KeyMapping("key." + VaultAdditions.MOD_ID + "." + name, keyCode, "key.category." + VaultAdditions.MOD_ID);
    }
}

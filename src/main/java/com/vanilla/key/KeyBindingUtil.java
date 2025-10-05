package com.vanilla.key;

import com.vanilla.BetterParticles;
import com.vanilla.function.CreatePreviewOnTick;
import com.vanilla.util.SendMessageToPlayer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBindingUtil {

    private static String getKeyName(String key) {
       return  "key."+ BetterParticles.MOD_ID+"."+key;
    }

    private static final KeyBinding IS_PREVIEW = KeyBindingHelper.registerKeyBinding(
            new KeyBinding(
                    getKeyName("preview"),
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_K,
                    "category."+BetterParticles.MOD_ID+".controls"
            )
    );

    public static void initKeyBindings() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (IS_PREVIEW.wasPressed()) {
                isPreview();
                if(CreatePreviewOnTick.isPreview){
                    SendMessageToPlayer.sendMessageToPlayer("开启预览模式");
                }else{
                    SendMessageToPlayer.sendMessageToPlayer("关闭预览模式");
                }
            }
        });
    }

    private static void isPreview() {
        CreatePreviewOnTick.isPreview  = !CreatePreviewOnTick.isPreview;
    }
}

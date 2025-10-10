package com.vanilla.key;

import com.vanilla.BetterParticles;
import com.vanilla.function.CreatePreviewOnTick;
import com.vanilla.item.SoulGraphPen;
import com.vanilla.util.SaveJsonToText;
import com.vanilla.util.SendMessageToPlayer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.nio.file.Path;

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

    private static final KeyBinding IS_SAVED = KeyBindingHelper.registerKeyBinding(
            new KeyBinding(
                    getKeyName("save"),
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_V,
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

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (IS_SAVED.wasPressed()) {
                isSaved();
                if(SoulGraphPen.isSaved){
                    SendMessageToPlayer.sendMessageToPlayer("开始录制");
                }else{
                    SendMessageToPlayer.sendMessageToPlayer("结束录制");
                    Path path =  SaveJsonToText.getInstance().close();
                    SendMessageToPlayer.sendMessageToPlayer("已保存至:"+path.toString());
                }
            }
        });
    }

    private static void isPreview() {
        CreatePreviewOnTick.isPreview  = !CreatePreviewOnTick.isPreview;
    }

    private static  void isSaved(){
        SoulGraphPen.isSaved = !SoulGraphPen.isSaved;
    }
}

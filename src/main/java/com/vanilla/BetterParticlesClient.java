package com.vanilla;

import com.vanilla.atlas.AtlasGenerator;
import com.vanilla.command.ModCommand;
import com.vanilla.function.CreateCircle;
import com.vanilla.function.CreatePreviewOnTick;
import com.vanilla.item.ModItems;
import com.vanilla.item.SoulGraphPen;
import com.vanilla.key.KeyBindingUtil;
import com.vanilla.particle.ModParticleRegister;
import com.vanilla.gui.AtlasHudOverlay;
import com.vanilla.util.MouseScrollEvent;
import com.vanilla.util.PointListener;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class BetterParticlesClient implements ClientModInitializer {
        @Override
        public void onInitializeClient() {
            ModParticleRegister.initParticles();
            ModCommand.initCommand();
            CreatePreviewOnTick.initPreview();
            KeyBindingUtil.initKeyBindings();
            PointListener.initPointListener();
            HudRenderCallback.EVENT.register(new AtlasHudOverlay());
            ClientPlayConnectionEvents.JOIN.register((handler, sender, client)->{
                AtlasGenerator.generate();
                int shiftKey = GLFW.GLFW_KEY_LEFT_SHIFT;
                long window = MinecraftClient.getInstance().getWindow().getHandle();
                PlayerEntity player =  MinecraftClient.getInstance().player;
                MouseScrollEvent.EVENT.register((dx, dy) -> {
                    assert player != null;
                    System.out.println(player.getMainHandStack().getItem().toString());
                    if(!player.getMainHandStack().isOf(ModItems.SOUL_GRAPH_PEN)) return false;
                    if(!InputUtil.isKeyPressed(window, shiftKey)) {
                        if(SoulGraphPen.CurrentMode == SoulGraphPen.ParticleMode.CREATE_CIRCLE){
                            if(dy==1){
                                CreateCircle.commandPitchDeg++;
                            }else if(dy==-1){
                                CreateCircle.commandPitchDeg--;
                            }
                            return true;
                        }else{
                            return false;
                        }
                    }
                    SoulGraphPen.getInstance().changeCurrentMode((int) dy);
                    return true;
                });
            });
        }
}

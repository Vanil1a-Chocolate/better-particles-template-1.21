package com.vanilla.util;

import com.vanilla.item.ModItems;
import com.vanilla.item.SoulGraphPen;
import com.vanilla.obj.Point;
import com.vanilla.particle.ModParticleManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public final class PointListener {
    private static Vec3d pointA;
    private static Vec3d pointB;
    private static boolean wasLeftDown;
    private static boolean wasRightDown;

    public static void onEnterLineMode() {
        ClientTickEvents.END_CLIENT_TICK.register(PointListener::onTick);
        pointA = null;
        pointB = null;
        wasLeftDown = false;
        wasRightDown = false;
    }

    public static void onExitLineMode() {
        SendMessageToPlayer.sendMessageToPlayer("退出绑定模式");
    }

    private static void onTick(MinecraftClient client) {
        SoulGraphPen soulGraphPen = SoulGraphPen.getInstance();
        if(soulGraphPen.getCurrentMode() != SoulGraphPen.ParticleMode.CREATE_LINE) return;

        ClientPlayerEntity player = client.player;
        if(player == null) return;
        if(!player.getMainHandStack().isOf(ModItems.SOUL_GRAPH_PEN)) return;

        boolean nowLeft = isLeftClicking();
        if (nowLeft && !wasLeftDown) {
            SendMessageToPlayer.sendMessageToPlayer("第一个点已记录");
            pointA = player.getPos().add(0, player.getEyeHeight(player.getPose()), 0);
            ModParticleManager.getInstance().addWarnParticle(client.world, pointA);
            wasLeftDown = true;
        }

        boolean nowRight = isRightClicking();
        if (nowRight && !wasRightDown && pointA != null) {
            SendMessageToPlayer.sendMessageToPlayer("第二个点已记录");
            pointB = player.getPos().add(0, player.getEyeHeight(player.getPose()), 0);
            ModParticleManager.getInstance().addWarnParticle(client.world,pointB);
            onPointsReady(pointA, pointB);
            pointA = null;
            pointB = null;
        }

        wasLeftDown = nowLeft;
        wasRightDown = nowRight;
    }


    private static boolean isLeftClicking(){
        return GLFW.glfwGetMouseButton(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;
    }

    private static boolean isRightClicking(){
        return GLFW.glfwGetMouseButton(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;
    }

    public static void onPointsReady(Vec3d pointA, Vec3d pointB) {
        Point.INSTANCE = new Point(pointA, pointB);
    }
}

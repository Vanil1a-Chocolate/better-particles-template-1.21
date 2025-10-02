package com.vanilla.util;

import com.vanilla.BetterParticles;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class SendMessageToPlayer {
    public static void sendMessageToPlayer(String message) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) {
            BetterParticles.LOGGER.info("玩家初始化失败");
        }
        sendMessageToPlayer(message, player);
    }

    public static void sendMessageToPlayer(String message,PlayerEntity player) {
        if (player == null) {
            BetterParticles.LOGGER.info("玩家不存在");
        }
        assert player != null;
        player.sendMessage(Text.of(message), false);
    }

}

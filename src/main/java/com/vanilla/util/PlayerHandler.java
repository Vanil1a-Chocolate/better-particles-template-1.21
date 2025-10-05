package com.vanilla.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class PlayerHandler {

    public static Vec3d getPlayerEyePosition(PlayerEntity player) {
        return player.getEyePos().add(player.getRotationVec(1).multiply(2));
    }
}

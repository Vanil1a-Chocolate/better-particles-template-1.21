package com.vanilla.util;

import com.vanilla.function.CreateSingleParticle;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

public class Draw {

    public static void Heart() {
        Vec3d eye = null;
        if (MinecraftClient.getInstance().player != null) {
            eye = MinecraftClient.getInstance().player.getEyePos();
        }
        Vec3d look = null;
        if (MinecraftClient.getInstance().player != null) {
            look = MinecraftClient.getInstance().player.getRotationVec(1.0F);
        }
        Vec3d center = null;
        if (look != null) {
            center = eye.add(look.multiply(2.0));
        }

        int precision = 64;
        double radius = 5.0;
        for (int i = 0; i < precision; i++) {
            double t = 2.0 * Math.PI * i / precision;
            double hx = 16 * Math.sin(t) * Math.sin(t) * Math.sin(t);
            double hz = 13 * Math.cos(t) - 5 * Math.cos(2 * t) - 2 * Math.cos(3 * t) - Math.cos(4 * t);

            hx *= radius / 16.0;
            hz *= radius / 16.0;

            Vec3d local = new Vec3d(hx, 0, hz);
            Vec3d point = null;
            if (center != null) {
                point = center.add(local);
            }
            CreateSingleParticle create = new CreateSingleParticle(point);
            create.clientGenerate();
        }
    }
}

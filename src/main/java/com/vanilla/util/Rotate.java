package com.vanilla.util;

import com.vanilla.particle.ParticleData;
import net.minecraft.util.math.Vec3d;

public class Rotate {
    public static void rotateYAxis(ParticleData data, Vec3d center,double angular_velocity) {
                rotateAnyAxis(data,center,new Vec3d(0,1,0),angular_velocity);
    }


    public static void rotateAnyAxis(ParticleData data, Vec3d center, Vec3d axis, double angular_velocity) {
        Vec3d unitAxis = axis.normalize();
        data.setMove(() -> rotateFunction(data.getPosition(),center,unitAxis,angular_velocity));
    }

    public static Vec3d rotateFunction(Vec3d p,Vec3d center,Vec3d unitAxis, double angular_velocity){
        double ux = unitAxis.x;
        double uy = unitAxis.y;
        double uz = unitAxis.z;

        double x = p.x - center.x;
        double y = p.y - center.y;
        double z = p.z - center.z;

        double cos = Math.cos(angular_velocity);
        double sin = Math.sin(angular_velocity);
        double v = 1 - cos;
        double var_1 = ux * x + uy * y + uz * z;

        double xPrime = x * cos
                + (uy * z - uz * y) * sin
                + ux * var_1 * v;

        double yPrime = y * cos
                + (uz * x - ux * z) * sin
                + uy * var_1 * v;

        double zPrime = z * cos
                + (ux * y - uy * x) * sin
                + uz * var_1 * v;

        return new Vec3d(
                xPrime + center.x,
                yPrime + center.y,
                zPrime + center.z
        );
    }
}

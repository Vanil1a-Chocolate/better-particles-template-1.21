package com.vanilla.util;

import net.minecraft.util.math.Vec3d;

public class DistanceHelper {

    public static Vec3d getDistanceFromTwoVec3d(Vec3d start,Vec3d end){
        return start.subtract(end);
    }

}

package com.vanilla.obj;

import net.minecraft.util.math.Vec3d;

public class Point {
    private Vec3d start;
    private Vec3d end;
    public static Point INSTANCE;

    public Point(Vec3d start, Vec3d end) {
        this.start = start;
        this.end = end;
    }
    public Vec3d getStart() {
        return start;
    }
    public void setStart(Vec3d start) {
        this.start = start;
    }
    public Vec3d getEnd() {
        return end;
    }
    public void setEnd(Vec3d end) {
        this.end = end;
    }
}

package com.vanilla.util;

import com.google.gson.JsonObject;
import net.minecraft.util.math.Vec3d;

public class JsonHelper {

    public static JsonObject UseVec3dToJson(Vec3d vec3d) {
        JsonObject json = new JsonObject();
        json.addProperty("x", vec3d.x);
        json.addProperty("y", vec3d.y);
        json.addProperty("z", vec3d.z);
        return json;
    }

    public static Vec3d getVec3dFromJson(JsonObject json) {
        return new Vec3d(
                json.get("x").getAsDouble(),
                json.get("y").getAsDouble(),
                json.get("z").getAsDouble()
        );
    }

    public static Vec3d getVec3dFromJsonEz(JsonObject json) {
        return getVec3dFromJson(json.getAsJsonObject("position"));
    }
}

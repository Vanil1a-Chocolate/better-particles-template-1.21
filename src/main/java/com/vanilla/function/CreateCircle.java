package com.vanilla.function;

import com.google.gson.JsonObject;
import com.vanilla.item.SoulGraphPen;
import com.vanilla.particle.ModParticleManager;
import com.vanilla.particle.ModParticleRegister;
import com.vanilla.particle.ParticleData;
import com.vanilla.util.DistanceHelper;
import com.vanilla.util.JsonHelper;
import com.vanilla.util.ReadTextToJson;
import com.vanilla.util.SaveJsonToText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CreateCircle implements CreateInter {
    private final double radius;
    private final Vec3d position;
    private final ParticleData data;
    private final int precision;
    private  final double pitchDeg ;
    private  final double yawDeg;
    public static double commandPitchDeg = 90;

    public static final CreateCircle INSTANCE = new CreateCircle(0,Vec3d.ZERO,0);

    public CreateCircle(double radius, Vec3d position, ParticleData data, int precision) {
        this(radius,position,data,precision,commandPitchDeg,0);
    }
    public CreateCircle(double radius, Vec3d position, int precision) {
        this(radius,position,ModParticleRegister.SIMPLE_DEFAULT_PARTICLE_DATA,precision);
    }

    public CreateCircle(double radius, Vec3d position, ParticleData data,int precision, double pitchDeg, double yawDeg) {
        this.radius = radius;
        this.position = position;
        this.data = data;
        this.precision = precision;
        this.pitchDeg = pitchDeg;
        this.yawDeg = yawDeg;
    }
    @Override
    public void generate(World world) {
        double pitch = Math.toRadians(pitchDeg);
        double yaw = Math.toRadians(yawDeg);

        double nx = Math.cos(pitch) * Math.sin(yaw);
        double ny = Math.sin(pitch);
        double nz = Math.cos(pitch) * Math.cos(yaw);
        Vec3d normal = new Vec3d(nx, ny, nz).normalize();

        Vec3d u;

        if (Math.abs(normal.x) < Math.abs(normal.y) && Math.abs(normal.x) < Math.abs(normal.z)) {
            u = new Vec3d(1, 0, 0);
        } else if (Math.abs(normal.y) < Math.abs(normal.z)) {
            u = new Vec3d(0, 1, 0);
        } else {
            u = new Vec3d(0, 0, 1);
        }
        u = normal.crossProduct(u).normalize();

        Vec3d v = normal.crossProduct(u).normalize();

        ModParticleManager particleManager = ModParticleManager.getInstance();
        String handle = "CIRCLE_"+ particleManager.outGetCurrentHandle();

        if(data.getParticleType()!= ModParticleRegister.PREVIEW_PARTICLE){
            if (SoulGraphPen.isSaved){
                SaveJsonToText.getInstance().saveToTextFile(toJson(data));
            }
        }
        for(int i =0;i<precision;i++){
            ParticleData data_new = data.copy();
            double t = 2*Math.PI*i/precision;
            Vec3d point = position.add(u.multiply(Math.cos(t)*radius))
                                  .add(v.multiply(Math.sin(t)*radius));
            data_new.setPosition(point);
            particleManager.addParticle(data_new,world,handle);
        }
    }

    @Override
    public JsonObject toJson(ParticleData data){
        JsonObject json = new JsonObject();

        Vec3d nPos = DistanceHelper.getDistanceFromTwoVec3d(SaveJsonToText.getInstance().getStartPosition(),position);
        json.addProperty("mode", SoulGraphPen.ParticleMode.CREATE_CIRCLE.getValue());
        json.addProperty("radius", radius);
        json.addProperty("precision", precision);
        json.addProperty("pitchDeg", pitchDeg);
        json.addProperty("yawDeg", yawDeg);
        JsonObject pos = JsonHelper.UseVec3dToJson(nPos);
        json.add("position", pos);
        json.add("data",ParticleData.DataToJson(data));
        return json;
    }

    @Override
    public void toData(JsonObject json){
        int precision = json.get("precision").getAsInt();
        double radius = json.get("radius").getAsDouble();
        double pitchDeg = json.get("pitchDeg").getAsDouble();
        double yawDeg = json.get("yawDeg").getAsDouble();
        Vec3d pos = JsonHelper.getVec3dFromJsonEz(json);
        Vec3d CurrentPos = DistanceHelper.getDistanceFromTwoVec3d(ReadTextToJson.getStartPos(),pos);
        CreateCircle createCircle = new CreateCircle(radius,CurrentPos,ModParticleRegister.SIMPLE_DEFAULT_PARTICLE_DATA.copy(),precision,pitchDeg,yawDeg);
        createCircle.generate(MinecraftClient.getInstance().world);
    }
}

package com.vanilla.network;

import com.vanilla.obj.Point;
import com.vanilla.particle.ParticleData;
import net.minecraft.network.PacketByteBuf;

import java.awt.*;

public class ParticleDataBufferHelper {
    public static void write(PacketByteBuf buf, ParticleData data) {
        buf.writeIdentifier(data.getId());
        buf.writeInt(data.getLifeTime());
        buf.writeFloat(data.getScale());
        writeColor(buf, data.getColor());
    }

    public static ParticleData read(PacketByteBuf buf) {
        ParticleData data = new ParticleData();
        data.setId(buf.readIdentifier());
        data.setLifeTime(buf.readInt());
        data.setScale(buf.readFloat());
        data.setColor(readColor(buf));
        return data;
    }

    private static void writeColor(PacketByteBuf buf, Color color) {
        buf.writeByte(color.getRed());
        buf.writeByte(color.getGreen());
        buf.writeByte(color.getBlue());
        buf.writeByte(color.getAlpha());
    }

    public static void writePoint(PacketByteBuf buf, Point point) {
        buf.writeVec3d(point.getStart());
        buf.writeVec3d(point.getEnd());
    }

    public static Point readPoint(PacketByteBuf buf) {
        return new Point(buf.readVec3d(), buf.readVec3d());
    }

    private static Color readColor(PacketByteBuf buf) {
        int red = buf.readByte()& 0xFF;
        int green = buf.readByte()& 0xFF;
        int blue = buf.readByte()& 0xFF;
        int alpha = buf.readByte()& 0xFF;
        return new Color(red, green, blue, alpha);
    }
}

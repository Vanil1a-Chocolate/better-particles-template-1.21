package com.vanilla.client;

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

    private static Color readColor(PacketByteBuf buf) {
        int red = buf.readByte();
        int green = buf.readByte();
        int blue = buf.readByte();
        int alpha = buf.readByte();
        return new Color(red, green, blue, alpha);
    }
}

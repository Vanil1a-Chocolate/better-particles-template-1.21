package com.vanilla.network;

import com.vanilla.BetterParticles;
import com.vanilla.function.CreateInter;
import com.vanilla.item.SoulGraphPen;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ParticlePayload(CreateInter create) implements CustomPayload {
    public static final Identifier ID = Identifier.of(BetterParticles.MOD_ID, "client_particle");
    public static final CustomPayload.Id<ParticlePayload> TYPE = new CustomPayload.Id<>(ID);
    public static final PacketCodec<PacketByteBuf, ParticlePayload> CODEC = PacketCodec.of(
            ParticlePayload::write,
            ParticlePayload::read
    );

    private static void write(ParticlePayload payload, PacketByteBuf buf) {
        buf.writeByte(payload.create.getId());
        payload.create.write(buf);
    }

    private static ParticlePayload read(PacketByteBuf buf) {
        byte id = buf.readByte();
        CreateInter create = SoulGraphPen.ParticleMode.get(id);
        if(create == null) {
            throw new IllegalArgumentException("未知粒子模式: " + id);
        }
        return new ParticlePayload(create.read(buf));
    }

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return TYPE;
    }
}

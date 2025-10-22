package com.vanilla.network;

import com.vanilla.BetterParticles;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Objects;


public class ParticleNetWork {
    public static void initClientToServer(){
        BetterParticles.LOGGER.info("Particle Server Net Work Initialized");
        PayloadTypeRegistry.playC2S().register(ParticlePayload.TYPE,ParticlePayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(
                ParticlePayload.TYPE,
                (payload, context)->{
                    ServerPlayerEntity player = context.player();
                    context.server().execute(() -> {
                        Vec3d pos = player.getPos();
                        List<ServerPlayerEntity> allPlayers = Objects.requireNonNull(player.getServer()).getPlayerManager().getPlayerList();
                        for (ServerPlayerEntity var_1 : allPlayers) {
                            double distance = var_1.getPos().distanceTo(pos);
                            if (distance <= 32 && !var_1.equals(player) ) {
                                ServerPlayNetworking.send(var_1, payload);
                            }
                        }

                    });
                }
        );
    }

    public static void initServerToClient(){
        BetterParticles.LOGGER.info("Particle Client Net Work Initialized");
        PayloadTypeRegistry.playS2C().register(ParticlePayload.TYPE,ParticlePayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(ParticlePayload.TYPE, (payload, context) ->
                MinecraftClient.getInstance().execute(()->
                payload.create().clientGenerate()));
    }
}

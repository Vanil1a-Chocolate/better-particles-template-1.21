package com.vanilla.function;

import com.vanilla.item.ModItems;
import com.vanilla.item.SoulGraphPen;
import com.vanilla.particle.ModParticleRegister;
import com.vanilla.util.PlayerHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class CreatePreviewOnTick {
    public static void initPreview(){
        ClientTickEvents.END_CLIENT_TICK.register(CreatePreviewOnTick::onTick);
    }

    public static void onTick(MinecraftClient client) {
        if (client.player == null) return;
        if (!client.player.getMainHandStack().isOf(ModItems.SOUL_GRAPH_PEN)) return;
        Vec3d pos = PlayerHandler.getPlayerEyePosition(client.player);
        ModParticleRegister.PREVIEW_PARTICLE_DATA.setPosition(pos);
        if(SoulGraphPen.CurrentMode == SoulGraphPen.ParticleMode.CREATE_SINGLE_PARTICLE){
            CreateSingleParticle create = new CreateSingleParticle(ModParticleRegister.PREVIEW_PARTICLE_DATA,"VIEW_");
            create.generate(client.world);
        }else if(SoulGraphPen.CurrentMode == SoulGraphPen.ParticleMode.CREATE_CIRCLE){
            CreateCircle create = new CreateCircle(5,pos,ModParticleRegister.PREVIEW_PARTICLE_DATA,60);
            create.generate(client.world);
        }

    }
}

package com.vanilla.mixin;

import com.vanilla.BetterParticles;
import com.vanilla.atlas.ModParticleSheet;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.ParticleTextureSheet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
    @Shadow
    @Final
    @Mutable
    private static List<ParticleTextureSheet> PARTICLE_TEXTURE_SHEETS;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void injectCustomSheet(CallbackInfo ci) {
        List<ParticleTextureSheet> newSheets = new ArrayList<>(PARTICLE_TEXTURE_SHEETS);
        newSheets.add(ModParticleSheet.DEFAULT_PARTICLE_SHEET);
        PARTICLE_TEXTURE_SHEETS = List.copyOf(newSheets);
        BetterParticles.LOGGER.info("注入后 PARTITION_TEXTURE_SHEETS 列表：");
        for (ParticleTextureSheet sheet : PARTICLE_TEXTURE_SHEETS) {
            BetterParticles.LOGGER.info(" - " + sheet.toString());
        }
    }
}
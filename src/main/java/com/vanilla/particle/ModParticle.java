package com.vanilla.particle;

import com.vanilla.BetterParticles;
import com.vanilla.atlas.ModParticleSheet;
import com.vanilla.util.UseCommandData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class ModParticle extends SpriteBillboardParticle {

    private  ParticleTextureSheet sheet;
    public final ParticleData data;
    private String handle;
    private final ModParticleMove move;
    protected ModParticle(ClientWorld clientWorld,ParticleData data){
        super(clientWorld,data.getPosition().getX(),data.getPosition().getY(),data.getPosition().getZ(),
                data.getVelocity().getX(),data.getVelocity().getY(),data.getVelocity().getZ());
        if(UseCommandData.changeSprite){
            Sprite sp =  UseCommandData.getSprite();
            if (sp!=null){
                setSprite(sp);
                this.sheet = ModParticleSheet.DEFAULT_PARTICLE_SHEET;
            }
        }else{
            setSprite(data.getSpriteProvider());
            this.sheet = data.getSheet();
        }
        this.data = data;
        this.maxAge = data.getLifeTime();
        this.scale = data.getScale();
        this.alpha = data.getColor().getAlpha();
        this.move = data.getMove();
    }

    @Override
    public ParticleTextureSheet getType() {
        BetterParticles.LOGGER.info(ModParticleSheet.DEFAULT_PARTICLE_SHEET.toString());
        if (sheet == null) {
            return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
        }
        return sheet;
    }

    @Override
    public void tick() {
        if(move != null) {
            Vec3d vec= move.tickMove();
            setPos(vec.x, vec.y, vec.z);
        }
        if(data != null&& !data.isMoved()) {
            this.setVelocity(0, 0, 0);
        }
        if (data != null) {
            this.velocityX = data.getVelocity().getX();
            this.velocityY = data.getVelocity().getY();
            this.velocityZ = data.getVelocity().getZ();
        }
        super.tick();
        if (age++ >= maxAge) {
            this.markDead();
            ModParticleManager.getInstance().cleanGroup(handle);
        }
    }

    public String getHandle() { return handle; }

    public void setHandle(String handle) { this.handle = handle; }
}

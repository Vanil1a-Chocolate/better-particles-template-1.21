package com.vanilla.particle;

import com.vanilla.atlas.AtlasSpriteManager;
import com.vanilla.atlas.ModParticleSheet;
import com.vanilla.util.UseCommandData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.render.LightmapTextureManager;
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

    public void refresh(){
        Sprite sp = AtlasSpriteManager.getInstance().getSprite(this.sprite.getContents().getId());
        setSprite(sp);
    }

    @Override
    public ParticleTextureSheet getType() {
        if (sheet == null) {
            return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
        }
        return sheet;
    }

    @Override
    public int getBrightness(float tickDelta){
        return LightmapTextureManager.MAX_LIGHT_COORDINATE;
    }

    @Override
    public void tick() {
        if(move != null) {
            Vec3d vec= move.tickMove();
            setPos(vec.x, vec.y, vec.z);
        }
        if(data != null){
            if(!data.isMoved()) {
                this.setVelocity(0, 0, 0);
            }
            this.velocityX = data.getVelocity().getX();
            this.velocityY = data.getVelocity().getY();
            this.velocityZ = data.getVelocity().getZ();
            if (data.getParticleType() == ModParticleRegister.METEOR_PARTICLE) {
                setSprite(data.getSpriteProvider().getSprite(age, maxAge));
            }
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

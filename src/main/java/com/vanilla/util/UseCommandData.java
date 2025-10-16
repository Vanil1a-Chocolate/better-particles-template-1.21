package com.vanilla.util;

import com.vanilla.BetterParticles;
import com.vanilla.atlas.AtlasSpriteManager;
import com.vanilla.particle.ModParticle;
import com.vanilla.particle.ModParticleManager;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class UseCommandData {
    public static Vec3d position;
    public static boolean isMoved = false;
    public static boolean changeSprite = false;

    public static Sprite getSprite() {
        return sprite;
    }

    private static Sprite sprite;
    public static void getPositionFromPicked(){
        List<String> handle = ModParticleManager.getInstance().getWarningParticlesHandle();
        String new_handle = handle.getLast();
        ModParticle particle = ParticleVisionLocator.getReVisionMap().get(new_handle);
        position = particle.data.getPosition();
    }

    public static void changeSprite(String name,boolean isMes){
        Identifier identifier = Identifier.of(BetterParticles.MOD_ID, name);
        Sprite var_1= AtlasSpriteManager.getInstance().getSprite(identifier);
        if(var_1 == null){
            if(isMes){
                SendMessageToPlayer.sendMessageToPlayer("该贴图不存在");
            }
            return;
        }
        sprite = var_1;
        if(isMes){
            SendMessageToPlayer.sendMessageToPlayer("成功切换贴图:"+name);
        }
    }

    public static void changeSprite(String name){
        changeSprite(name,true);
    }
}

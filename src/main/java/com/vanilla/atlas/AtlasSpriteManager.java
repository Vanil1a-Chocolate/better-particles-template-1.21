package com.vanilla.atlas;

import com.vanilla.obj.Picture;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.*;
import net.minecraft.util.Identifier;

import java.util.*;

public class AtlasSpriteManager {
    private static final AtlasSpriteManager INSTANCE= new AtlasSpriteManager();
    private final Map<Identifier, Sprite> PlayerSpriteMap = new HashMap<>();
    private final Map<Identifier, SpriteMeta> metaMap = new HashMap<>();
    private int atlasGlTextureId = -1;
    private Identifier atlasSpriteId = null;
    private AtlasSpriteManager() {}

    public void addSpriteMeta(SpriteMeta data) {
        metaMap.put(data.getTextureId(), data);
    }

    public void AllMetaToSprite() {
        for (SpriteMeta data : metaMap.values()) {
            Sprite sp=  CreateSpriteFromMeta.createSpriteFromMeta(data);
            PlayerSpriteMap.put(data.getTextureId(), sp);
        }
    }

    public static AtlasSpriteManager getInstance() {
        return INSTANCE;
    }

    public Sprite getSprite(Identifier textureId) {
        return PlayerSpriteMap.get(textureId);
    }

    public void setAtlasGlTextureId(int atlasGlTextureId) {
        this.atlasGlTextureId = atlasGlTextureId;
    }

    public boolean isUpdateToGPU() {
        return atlasGlTextureId!=-1;
    }

    public void setAtlasSpriteId(Identifier atlasSpriteId) {
        this.atlasSpriteId = atlasSpriteId;
    }

    public Identifier getAtlasSpriteId() {
        return atlasSpriteId;
    }

    public List<String> getAllIds() {
        Set<Identifier> allIds = PlayerSpriteMap.keySet();
        List<String> ids = new ArrayList<>();
        for (Identifier id : allIds) {
            ids.add(id.getPath());
        }
        return ids;
    }

    public Picture getAtlasWidthAndHeight() {
        TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
        AbstractTexture texture =  textureManager.getTexture(atlasSpriteId);
        NativeImage atlasImage = ((NativeImageBackedTexture) texture).getImage();
        if (atlasImage != null) {
            return new Picture(atlasImage.getWidth(), atlasImage.getHeight());
        }
        return new Picture();
    }

}

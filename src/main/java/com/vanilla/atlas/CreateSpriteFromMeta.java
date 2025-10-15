package com.vanilla.atlas;

import com.vanilla.obj.Picture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteContents;
import net.minecraft.client.texture.SpriteDimensions;
import net.minecraft.resource.metadata.ResourceMetadata;

public class CreateSpriteFromMeta  {

    public static Sprite createSpriteFromMeta(SpriteMeta meta){
        if(!AtlasSpriteManager.getInstance().isUpdateToGPU()||AtlasSpriteManager.getInstance().getAtlasSpriteId()==null){
            throw new IllegalStateException("图集未上传GPU！");
        }
        SpriteDimensions dimensions = new SpriteDimensions(meta.getWidth(), meta.getHeight());
        NativeImage dummyImage = new NativeImage(NativeImage.Format.RGBA,1,1,false);
        SpriteContents contents = new SpriteContents(
                meta.getTextureId(),
                dimensions,dummyImage,
                ResourceMetadata.NONE
        );
        Picture pc =  AtlasSpriteManager.getInstance().getAtlasWidthAndHeight();
        dummyImage.close();
        return new ModSprite(meta,contents,pc.width, pc.height);
    }

    private static class  ModSprite extends Sprite{

        SpriteMeta meta;
        protected ModSprite(SpriteMeta meta,SpriteContents contents, int atlasWidth, int atlasHeight) {
            super(AtlasSpriteManager.getInstance().getAtlasSpriteId(), contents, atlasWidth, atlasHeight, meta.getXInAtlas(), meta.getYInAtlas());
            this.meta = meta;
        }

        @Override
        public float getMinU() {
            return meta.geMinU();
        }

        @Override
        public float getMaxU() {
            return meta.getMaxU();
        }

        @Override
        public float getMinV() {
            return meta.getMinV();
        }

        @Override
        public float getMaxV() {
            return meta.getMaxV();
        }
    }
}

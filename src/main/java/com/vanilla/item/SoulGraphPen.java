package com.vanilla.item;


import com.vanilla.util.SendMessageToPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SoulGraphPen extends Item {
    public enum ParticleMode {
        CREATE_SINGLE_PARTICLE,
        CREATE_LINE,
        CREATE_CIRCLE;


        public ParticleMode next() {
            return values()[(this.ordinal() + 1) % values().length];
        }

        public ParticleMode prev() {
            int len = values().length;
            return values()[(this.ordinal() - 1 + len) % len];
        }
    }

    public static ParticleMode CurrentMode;
    public static ParticleMode mode;
    private static final SoulGraphPen instance = new SoulGraphPen(new Item.Settings().maxCount(1));
    public SoulGraphPen(Settings settings) {
        super(settings);
        mode = ParticleMode.CREATE_SINGLE_PARTICLE;
        CurrentMode = mode;
    }

    public void changeCurrentMode(int number) {
        if(number==1){
            CurrentMode = CurrentMode.next();
        }else if(number==-1){
            CurrentMode = CurrentMode.prev();
        }
        sendCurrentModeToPlayer();
    }

    private void sendCurrentModeToPlayer() {
        switch (CurrentMode) {
            case CREATE_SINGLE_PARTICLE:
                SendMessageToPlayer.sendMessageToPlayer("当前为单个粒子模式");
                break;
            case CREATE_LINE:
                SendMessageToPlayer.sendMessageToPlayer("当前为直线模式");
                break;
            case CREATE_CIRCLE:
                SendMessageToPlayer.sendMessageToPlayer("当前为圆形模式");
                break;
        }
    }

    public static SoulGraphPen getInstance(){
        return instance;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (world.isClient) {
            Vec3d pos = user.getEyePos().add(user.getRotationVec(1).multiply(2));
            switch (CurrentMode) {
                case CREATE_SINGLE_PARTICLE:
                    world.addParticle(ParticleTypes.END_ROD, pos.x, pos.y, pos.z, 0, 0.1, 0);
                    break;
                case CREATE_LINE:

                case CREATE_CIRCLE:

            }

        }
        return TypedActionResult.success(stack);
    }
}

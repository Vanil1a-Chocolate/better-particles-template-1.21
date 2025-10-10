package com.vanilla.item;


import com.vanilla.function.CreateCircle;
import com.vanilla.function.CreateInter;
import com.vanilla.function.CreateLine;
import com.vanilla.function.CreateSingleParticle;
import com.vanilla.util.PlayerHandler;
import com.vanilla.util.PointListener;
import com.vanilla.util.SendMessageToPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SoulGraphPen extends Item {
    public enum ParticleMode {
        CREATE_SINGLE_PARTICLE(1),
        CREATE_LINE(2),
        CREATE_CIRCLE(3);


        private final int value ;
        ParticleMode(int i){
            value = i;
        }

        public ParticleMode next() {
            return values()[(this.ordinal() + 1) % values().length];
        }

        public ParticleMode prev() {
            int len = values().length;
            return values()[(this.ordinal() - 1 + len) % len];
        }

        public int getValue() {
            return value;
        }
    }

    public static ParticleMode CurrentMode;
    public static ParticleMode mode;
    private static final SoulGraphPen instance = new SoulGraphPen(new Settings().maxCount(1));
    public static boolean isSaved = false;
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
        if(CurrentMode == ParticleMode.CREATE_LINE){
            PointListener.onEnterLineMode();
        }
        sendCurrentModeToPlayer();
    }

    public ParticleMode getCurrentMode() {
        return CurrentMode;
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
            Vec3d pos = PlayerHandler.getPlayerEyePosition(user);
            switch (CurrentMode) {
                case CREATE_SINGLE_PARTICLE:
                    CreateInter single = new CreateSingleParticle(pos);
                    single.generate(world);
                    break;
                case CREATE_LINE:
                    CreateLine.CreateLineAuto();
                    break;
                case CREATE_CIRCLE:
                    CreateInter create = new CreateCircle(5,user.getPos(),60);
                    create.generate(world);
                    break;
            }

        }
        return TypedActionResult.success(stack);
    }
}

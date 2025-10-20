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

import java.util.HashMap;
import java.util.Map;

public class SoulGraphPen extends Item {
    public enum ParticleMode {
        CREATE_SINGLE_PARTICLE(1,CreateSingleParticle.INSTANCE),
        CREATE_LINE(2,CreateLine.INSTANCE),
        CREATE_CIRCLE(3,CreateCircle.INSTANCE),;

        private static final Map<Integer,CreateInter> ParticleModeMap = new HashMap<>();

        static {
            for (ParticleMode p : ParticleMode.values()) {
                ParticleModeMap.put(p.value,p.inter);
            }
        }

        private final int value ;
        private final CreateInter inter;
        ParticleMode(int i,CreateInter createInter) {
            value = i;
            inter = createInter;
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

        public static CreateInter get(int value) {
            return ParticleModeMap.get(value);
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
//                    CreateInter single = new CreateSingleParticle(pos);
//                    single.generate(world);
                    CreateSingleParticle.CreateTickChangeSingleParticle(pos);
                    break;
                case CREATE_LINE:
                    CreateLine createLine = new CreateLine(50);
                    createLine.generate(world);
                    break;
                case CREATE_CIRCLE:
                    if(CreateCircle.CommandCreateCircleData != null){
                        CreateCircle create =  CreateCircle.INSTANCE.UseCommandCreateCircleData(user.getPos());
                        create.generate(world);
                        CreateCircle.CommandCreateCircleData = null;
                    }else{
                        CreateInter create = new CreateCircle(5,user.getPos(),60);
                        create.generate(world);
                    }
                    break;
            }

        }
        return TypedActionResult.success(stack);
    }
}

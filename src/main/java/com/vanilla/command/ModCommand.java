package com.vanilla.command;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.vanilla.function.CreateCircle;
import com.vanilla.function.CreateLine;
import com.vanilla.particle.ModParticle;
import com.vanilla.particle.ModParticleManager;
import com.vanilla.util.ParticleVisionLocator;
import com.vanilla.util.SendMessageToPlayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class ModCommand {

    public static void initCommand() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher,registryAccess)-> dispatcher.register(ClientCommandManager.literal("cleanParticles").executes(context -> {
            ModParticleManager.getInstance().cleanAllGroup();
            context.getSource().sendFeedback(Text.literal("已经清除全部粒子"));
            return 1;
        })));

        ClientCommandRegistrationCallback.EVENT.register((dispatcher,registryAccess)-> dispatcher.register(ClientCommandManager.literal("undo").executes(context -> {
            boolean res =  ModParticleManager.getInstance().undo();
            if(res){
                context.getSource().sendFeedback(Text.literal("撤销上个粒子"));
            }else{
                context.getSource().sendFeedback(Text.literal("已经到头啦"));
            }
            return 1;
        })));

        ClientCommandRegistrationCallback.EVENT.register((dispatcher,registryAccess)-> dispatcher.register(ClientCommandManager.literal("showHandle").executes(context -> {
            SendMessageToPlayer.sendMessageToPlayer("当前句柄:");
            ModParticleManager.getInstance().printCurrentHandle();
            return 1;
        })));

        ClientCommandRegistrationCallback.EVENT.register((dispatcher,registryAccess)-> dispatcher.register(ClientCommandManager.literal("cleanGroup")
                .then(ClientCommandManager.argument("group", StringArgumentType.string()).executes(context->{
                    String groupName = StringArgumentType.getString(context, "group");
                    ModParticleManager.getInstance().cleanGroup(groupName);
                    SendMessageToPlayer.sendMessageToPlayer("成功清除句柄:"+groupName);
                    return 1;
                }))
        ));

        ClientCommandRegistrationCallback.EVENT.register((dispatcher,registryAccess)-> dispatcher.register(ClientCommandManager.literal("modTest").executes(context -> {
            ParticleVisionLocator l = new ParticleVisionLocator();
            ModParticle particle =  l.getClosestParticleInSight(MinecraftClient.getInstance().player,5);
            if(particle != null){
                SendMessageToPlayer.sendMessageToPlayer("当前面向:"+particle.getHandle());
            }
            return 1;
        })));

        ClientCommandRegistrationCallback.EVENT.register((dispatcher,registryAccess)-> dispatcher.register(ClientCommandManager.literal("setPitchDeg")
                .then(ClientCommandManager.argument("n", DoubleArgumentType.doubleArg()).executes(context->{
                    double deg = DoubleArgumentType.getDouble(context, "n");
                    CreateCircle.commandPitchDeg = deg;
                    SendMessageToPlayer.sendMessageToPlayer("设置成功!");
                    return 1;
                }))
        ));

        ClientCommandRegistrationCallback.EVENT.register((dispatcher,registryAccess)-> dispatcher.register(ClientCommandManager.literal("createLine")
                .executes(commandContext -> {
                    CreateLine.UseVisionParticleCreateLine(true);
                    return 1;
                })
        ));
    }




}

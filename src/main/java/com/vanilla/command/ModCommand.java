package com.vanilla.command;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.vanilla.atlas.AtlasSpriteManager;
import com.vanilla.atlas.CustomTextureLoader;
import com.vanilla.function.CreateCircle;
import com.vanilla.function.CreateLine;
import com.vanilla.gui.AtlasHudOverlay;
import com.vanilla.particle.ModParticleManager;
import com.vanilla.util.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ModCommand {

    public static void initCommand() {

        ClientCommandRegistrationCallback.EVENT.register((dispatcher,registryAccess)-> dispatcher.register(ClientCommandManager.literal("modTest").executes(context -> {
            UseCommandData.changeSprite = !UseCommandData.changeSprite;
            return 1;
        })));

        ClientCommandRegistrationCallback.EVENT.register((dispatcher,registryAccess)-> dispatcher.register(ClientCommandManager.literal("modTest2").executes(context -> {
            ModParticleManager.getInstance().nextParticle();
            return 1;
        })));

        ClientCommandRegistrationCallback.EVENT.register((dispatcher,registryAccess)-> dispatcher.register(ClientCommandManager.literal("loadNewParticle")
                .then(ClientCommandManager.argument("path", StringArgumentType.string()).executes(context->{
                    String path = StringArgumentType.getString(context, "path");
                    CustomTextureLoader.readImageFile(path);
                    return 1;
                }))
        ));
        ClientCommandRegistrationCallback.EVENT.register((dispatcher,registryAccess)-> dispatcher.register(ClientCommandManager.literal("changeParticle")
                .then(ClientCommandManager.argument("name", StringArgumentType.string())
                        .suggests((context, builder) ->{
                            List<String> suggestions = new ArrayList<>();
                            suggestions.add("default");
                            suggestions.addAll(AtlasSpriteManager.getInstance().getAllIds());
                            for (String s : suggestions) {
                                builder.suggest(s);
                            }
                            return builder.buildFuture();
                        })
                        .executes(context->{
                    String name = StringArgumentType.getString(context, "name");
                    if (name.equals("default")) {
                        UseCommandData.changeSprite = false;
                        AtlasHudOverlay.testSprite = null;
                        return 1;
                    }
                    ModParticleManager.getInstance().resetCurrentParticle();
                    UseCommandData.changeSprite = true;
                    UseCommandData.changeSprite(name);
                    return 1;
                }))
        ));
        ClientCommandRegistrationCallback.EVENT.register((dispatcher,registryAccess)-> dispatcher.register(ClientCommandManager.literal("showParticleTexture").executes(context -> {
            List<String> ids = AtlasSpriteManager.getInstance().getAllIds();
            if (ids.isEmpty()) {
                SendMessageToPlayer.sendMessageToPlayer("未加载任何贴图!");
                return 1;
            }
            for (String id : ids) {
                SendMessageToPlayer.sendMessageToPlayer(id);
            }
            return 1;
        })));

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

        ClientCommandRegistrationCallback.EVENT.register((dispatcher,registryAccess)-> dispatcher.register(ClientCommandManager.literal("readParticles")
                .then(ClientCommandManager.argument("n", StringArgumentType.string()).executes(context->{
                    String str = StringArgumentType.getString(context,"n");
                    List<JsonObject > json =  ReadTextToJson.readTextToJson(SaveJsonToText.getRootDir().resolve(str));
                    for (JsonObject jsonObj : json) {
                       ReadTextToJson.UseJsonToCreate(jsonObj);
                    }
                    return 1;
                }))
        ));



        ClientCommandRegistrationCallback.EVENT.register((dispatcher,registryAccess)-> dispatcher.register(ClientCommandManager.literal("setPitchDeg")
                .then(ClientCommandManager.argument("n", DoubleArgumentType.doubleArg()).executes(context->{
                    CreateCircle.commandPitchDeg = DoubleArgumentType.getDouble(context, "n");
                    SendMessageToPlayer.sendMessageToPlayer("设置成功!");
                    return 1;
                }))
        ));

        ClientCommandRegistrationCallback.EVENT.register((dispatcher,registryAccess)-> dispatcher.register(ClientCommandManager.literal("setCircle")
                .then(ClientCommandManager.argument("radius", DoubleArgumentType.doubleArg())
                .then(ClientCommandManager.argument("precision", IntegerArgumentType.integer())
                .executes(context->{
                    double radius= DoubleArgumentType.getDouble(context, "radius");
                    int precision = IntegerArgumentType.getInteger(context, "precision");
                    CreateCircle.CommandCreateCircleData = new CreateCircle(radius, Vec3d.ZERO,precision);
                    SendMessageToPlayer.sendMessageToPlayer("设置成功!");
                    return 1;
                })))
        ));

        ClientCommandRegistrationCallback.EVENT.register((dispatcher,registryAccess)-> dispatcher.register(ClientCommandManager.literal("createLine")
                .executes(commandContext -> {
                    CreateLine.UseVisionParticleCreateLine(true);
                    return 1;
                })
        ));
        ClientCommandRegistrationCallback.EVENT.register((dispatcher,registryAccess)-> dispatcher.register(ClientCommandManager.literal("setPosition")
                .executes(commandContext -> {
                    UseCommandData.getPositionFromPicked();
                    return 1;
                })
        ));

        ClientCommandRegistrationCallback.EVENT.register((dispatcher,registryAccess)-> dispatcher.register(ClientCommandManager.literal("setIsMoved")
                .then(ClientCommandManager.argument("isMoved", BoolArgumentType.bool())
                                .executes(context->{
                                    UseCommandData.isMoved = BoolArgumentType.getBool(context, "isMoved");
                                    SendMessageToPlayer.sendMessageToPlayer("设置成功!");
                                    return 1;
                                }))
        ));
    }




}

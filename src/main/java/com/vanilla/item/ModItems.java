package com.vanilla.item;

import com.vanilla.BetterParticles;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {


    public static final Item SOUL_GRAPH_PEN;
    public static final Item MAGIC_INGOT;

    static {
        SOUL_GRAPH_PEN = registerItems("soul_graph_pen",SoulGraphPen.getInstance());
        MAGIC_INGOT = registerItems("magic_ingot",new Item(new Item.Settings()));
    }

    private static Item registerItems(String id, Item item) {
        return Registry.register(Registries.ITEM,Identifier.of(BetterParticles.MOD_ID,id),item);
    }

    public static void initModItems() {
        BetterParticles.LOGGER.info("Registering Mod Items");
    }
}

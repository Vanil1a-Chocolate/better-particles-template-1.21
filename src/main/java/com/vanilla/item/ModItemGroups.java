package com.vanilla.item;

import com.vanilla.BetterParticles;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final  RegistryKey<ItemGroup> BETTER_PARTICLES_GROUP;

    static {
        BETTER_PARTICLES_GROUP = register("better-particles-group");
    }

    private static RegistryKey<ItemGroup> register(String id) {
        return RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(BetterParticles.MOD_ID,id));
    }

    public static void registerModItemGroups() {
        Registry.register(Registries.ITEM_GROUP,
                        BETTER_PARTICLES_GROUP,
                        ItemGroup.create(ItemGroup.Row.TOP, 7)
                                .displayName(Text.translatable("itemGroup.better_particles_group"))
                                .icon(() -> new ItemStack(ModItems.SOUL_GRAPH_PEN)).entries((displayContext, entries) ->
                                {
                                    entries.add(ModItems.SOUL_GRAPH_PEN);
                                }).build());
    }

    public static void initModItemGroups() {
        BetterParticles.LOGGER.info("Registering Mod Item Groups");
    }
}

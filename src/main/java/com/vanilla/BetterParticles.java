package com.vanilla;

import com.vanilla.item.ModItemGroups;
import com.vanilla.item.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterParticles implements ModInitializer {
	public static final String MOD_ID = "better-particles";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.initModItemGroups();
		ModItems.initModItems();
		LOGGER.info("Hello Fabric world!");
	}
}
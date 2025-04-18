package net.justmili.UnderTheStars;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.justmili.underthestars.init.UnderTheStarsTabs;
import net.justmili.underthestars.init.UnderTheStarsItems;

import net.fabricmc.api.ModInitializer;

public class UnderTheStars implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "UnderTheStars";

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing 'Under The Stars'... ");

		UnderTheStarsTabs.load();

		UnderTheStarsItems.load();

	}
}

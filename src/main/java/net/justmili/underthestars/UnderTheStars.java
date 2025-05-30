package net.justmili.underthestars;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;
import net.justmili.underthestars.init.UnderTheStarsBlocks;
import net.justmili.underthestars.init.UnderTheStarsItems;
import net.justmili.underthestars.init.UnderTheStarsTabs;
import net.justmili.underthestars.procedures.CampfireHealingHandler;

public class UnderTheStars implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "underthestars";

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Under The Stars...");

        UnderTheStarsTabs.load();
        UnderTheStarsBlocks.load();
        UnderTheStarsItems.load();

        CampfireHealingHandler.register();
    }
}

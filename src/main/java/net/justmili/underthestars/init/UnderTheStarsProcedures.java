package net.justmili.underthestars.init;

import net.fabricmc.api.ModInitializer;
import net.justmili.underthestars.procedures.CampfireHealing;

@SuppressWarnings("InstantiationOfUtilityClass")
public class UnderTheStarsProcedures {
	public static void load() {
		new CampfireHealing();
	}
}


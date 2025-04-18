package net.justmili.underthestars.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;

import net.justmili.UnderTheStars.UnderTheStars;

public class UnderTheStarsBlocks {
	//public static Block BLOCK;

	public static void load() {
		//BLOCK = register("block", new BloockBlock());
	}

	public static void clientLoad() {
		//Block.clientInit();
	}

	private static Block register(String registryName, Block block) {
		return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(UnderTheStars.MODID, registryName), block);
	}
}


/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.justmili.underthestars.init;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;

import net.justmili.underthestars.item.MarshmallowOnAStickItem;
import net.justmili.underthestars.item.MarshmallowItem;
import net.justmili.underthestars.item.KelpGelatinItem;
import net.justmili.underthestars.item.CookedMarshmallowOnAStickItem;
import net.justmili.underthestars.item.CookedMarshmallowItem;
import net.justmili.underthestars.UnderthestarsMod;

public class UnderthestarsModItems {
	public static Item KELP_GELATIN;
	public static Item MARSHMALLOW;
	public static Item COOKED_MARSHMALLOW;
	public static Item MARSHMALLOW_ON_A_STICK;
	public static Item COOKED_MARSHMALLOW_ON_A_STICK;

	public static void load() {
		KELP_GELATIN = register("kelp_gelatin", new KelpGelatinItem());
		MARSHMALLOW = register("marshmallow", new MarshmallowItem());
		COOKED_MARSHMALLOW = register("cooked_marshmallow", new CookedMarshmallowItem());
		MARSHMALLOW_ON_A_STICK = register("marshmallow_on_a_stick", new MarshmallowOnAStickItem());
		COOKED_MARSHMALLOW_ON_A_STICK = register("cooked_marshmallow_on_a_stick", new CookedMarshmallowOnAStickItem());
	}

	public static void clientLoad() {
	}

	private static Item register(String registryName, Item item) {
		return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(UnderthestarsMod.MODID, registryName), item);
	}

	private static void registerBlockingProperty(Item item) {
		ItemProperties.register(item, new ResourceLocation("blocking"), (ClampedItemPropertyFunction) ItemProperties.getProperty(Items.SHIELD, new ResourceLocation("blocking")));
	}
}

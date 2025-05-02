package net.justmili.underthestars.init;

import net.justmili.underthestars.UnderTheStars;
import net.justmili.underthestars.item.ChocolateBar;
import net.justmili.underthestars.item.ChocolatePiece;
import net.justmili.underthestars.item.CookedMarshmallow;
import net.justmili.underthestars.item.CookedMarshmallowOnAStick;
import net.justmili.underthestars.item.KelpGelatin;
import net.justmili.underthestars.item.Marshmallow;
import net.justmili.underthestars.item.MarshmallowOnAStick;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class UnderTheStarsItems {
	public static Item KELP_GELATIN;
	public static Item MARSHMALLOW;
	public static Item COOKED_MARSHMALLOW;
	public static Item MARSHMALLOW_ON_A_STICK;
	public static Item COOKED_MARSHMALLOW_ON_A_STICK;
	public static Item CHOCOLATE_BAR;
	public static Item CHOCOLATE_PIECE;

	public static void load() {
		KELP_GELATIN = register("kelp_gelatin", new KelpGelatin());
		MARSHMALLOW = register("marshmallow", new Marshmallow());
		COOKED_MARSHMALLOW = register("cooked_marshmallow", new CookedMarshmallow());
		MARSHMALLOW_ON_A_STICK = register("marshmallow_on_a_stick", new MarshmallowOnAStick());
		COOKED_MARSHMALLOW_ON_A_STICK = register("cooked_marshmallow_on_a_stick", new CookedMarshmallowOnAStick());
		CHOCOLATE_BAR = register("chocolate_bar", new ChocolateBar());
		CHOCOLATE_PIECE = register("chocolate_piece", new ChocolatePiece());
	}

	public static void clientLoad() {
	}

	private static Item register(String registryName, Item item) {
		return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(UnderTheStars.MODID, registryName), item);
	}

	private static void registerBlockingProperty(Item item) {
		ItemProperties.register(item, new ResourceLocation("blocking"), (ClampedItemPropertyFunction) ItemProperties.getProperty(Items.SHIELD, new ResourceLocation("blocking")));
	}
}

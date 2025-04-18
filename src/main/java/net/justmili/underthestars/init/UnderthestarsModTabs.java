
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.justmili.underthestars.init;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;

import net.justmili.underthestars.UnderthestarsMod;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;

public class UnderthestarsModTabs {
	public static ResourceKey<CreativeModeTab> TAB_UNDER_THE_STARS = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(UnderthestarsMod.MODID, "under_the_stars"));

	public static void load() {
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_UNDER_THE_STARS,
				FabricItemGroup.builder().title(Component.translatable("item_group." + UnderthestarsMod.MODID + ".under_the_stars")).icon(() -> new ItemStack(UnderthestarsModItems.MARSHMALLOW)).build());
	}
}

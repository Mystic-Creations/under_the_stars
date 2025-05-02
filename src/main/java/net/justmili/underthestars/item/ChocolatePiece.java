
package net.justmili.underthestars.item;

import java.util.List;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.justmili.underthestars.init.UnderTheStarsTabs;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ChocolatePiece extends Item {
	public ChocolatePiece() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food((new FoodProperties.Builder()).nutrition(1).saturationMod(0.1f)

				.build()));
		ItemGroupEvents.modifyEntriesEvent(UnderTheStarsTabs.UNDER_THE_STARS).register(content -> content.accept(this));
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 8;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
	}
}



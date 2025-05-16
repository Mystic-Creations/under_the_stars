package net.justmili.underthestars.init;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.justmili.underthestars.UnderTheStars;
import net.justmili.underthestars.block.WhiteSleepingBag;
import net.justmili.underthestars.init.UnderTheStarsTabs;  
import net.minecraft.world.item.Item.Properties;

public class UnderTheStarsBlocks {
    public static final Block WHITE_SLEEPING_BAG = registerBlock("white_sleeping_bag", new WhiteSleepingBag());
    public static final Item  WHITE_SLEEPING_BAG_ITEM = registerItem(
        "white_sleeping_bag",
        new BlockItem(WHITE_SLEEPING_BAG, new Properties().stacksTo(1))
    );

    public static void load() {
        ItemGroupEvents.modifyEntriesEvent(UnderTheStarsTabs.UNDER_THE_STARS)
            .register(entries -> entries.accept(WHITE_SLEEPING_BAG_ITEM));
    }

    private static Block registerBlock(String name, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK,
            new ResourceLocation(UnderTheStars.MODID, name),
            block);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM,
            new ResourceLocation(UnderTheStars.MODID, name),
            item);
    }
}
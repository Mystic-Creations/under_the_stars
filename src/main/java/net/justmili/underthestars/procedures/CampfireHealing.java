package net.justmili.underthestars.procedures;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class CampfireHealing {
    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register((MinecraftServer server) -> {
            for (ServerLevel world : server.getAllLevels()) {
                for (ServerPlayer player : world.players()) {
                    BlockPos center = player.blockPosition();
                    boolean nearLitCampfire = false;

                    for (int dx = -2; dx <= 2 && !nearLitCampfire; dx++) {
                        for (int dy = -2; dy <= 2 && !nearLitCampfire; dy++) {
                            for (int dz = -2; dz <= 2 && !nearLitCampfire; dz++) {
                                BlockPos checkPos = center.offset(dx, dy, dz);
                                if (world.getBlockState(checkPos).is(Blocks.CAMPFIRE) &&
                                    world.getBlockState(checkPos).getValue(BlockStateProperties.LIT)) {
                                    nearLitCampfire = true;
                                }
                            }
                        }
                    }

                    if (nearLitCampfire) {
                        MobEffectInstance effect = new MobEffectInstance(MobEffects.REGENERATION, 40, 1, true, false, true);
                        player.addEffect(effect);
                    }
                }
            }
        });
    }
}

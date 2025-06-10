package net.justmili.underthestars.procedures;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;

public class CampfireHealingHandler {

    private static final int HEAL_INTERVAL_TICKS = 30;
    private static final Map<UUID, Long> lastHealTickMap = new ConcurrentHashMap<>();

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(CampfireHealingHandler::onWorldTick);
    }

    private static void onWorldTick(ServerLevel level) {
        long currentTick = level.getGameTime();

        for (ServerPlayer player : level.players()) {
            UUID playerId = player.getUUID();
            BlockPos playerPos = player.blockPosition();
            if (playerPos.getY() < 63) continue;
            boolean nearLitCampfire = false;
            for (int dy : new int[]{-2, 0, 1}) {
                for (int dx = -3; dx <= 2; dx++) {
                    for (int dz = -3; dz <= 2; dz++) {
                        BlockPos pos = playerPos.offset(dx, dy, dz);
                        if (level.hasChunkAt(pos)) {
                            var state = level.getBlockState(pos);
                            if (state.is(Blocks.CAMPFIRE) && state.getValue(CampfireBlock.LIT)) {
                                nearLitCampfire = true;
                                break;
                            }
                        }
                    }
                    if (nearLitCampfire) break;
                }
                if (nearLitCampfire) break;
            }

            if (nearLitCampfire && player.getHealth() < player.getMaxHealth()) {
                long lastHealTick = lastHealTickMap.getOrDefault(playerId, 0L);
                if (currentTick - lastHealTick >= HEAL_INTERVAL_TICKS) {
                    player.heal(1.0F);
                    lastHealTickMap.put(playerId, currentTick);
                }
            }
        }
    }
}
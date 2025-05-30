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
import net.minecraft.world.phys.AABB;

public class CampfireHealingHandler {

    private static final float MAX_HEAL_AMOUNT = 6.0F; // Mili this is amount it heals
    private static final int COOLDOWN_TICKS = 20 * 30; // how long it takes for cooldown

    private static final Map<UUID, PlayerHealingData> playerHealingData = new ConcurrentHashMap<>();

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(CampfireHealingHandler::onWorldTick);
    }

    private static void onWorldTick(ServerLevel level) {
        long currentTick = level.getGameTime();

        for (ServerPlayer player : level.players()) {
            UUID playerId = player.getUUID();

            PlayerHealingData data = playerHealingData.getOrDefault(playerId, new PlayerHealingData());

            if (data.onCooldown) {
                if (currentTick - data.cooldownStartTick >= COOLDOWN_TICKS) {
                    data.reset();
                } else {
                    playerHealingData.put(playerId, data);
                    continue;
                }
            }

            BlockPos playerPos = player.blockPosition();
            AABB area = new AABB(playerPos).inflate(2);

            boolean nearLitCampfire = BlockPos.betweenClosedStream(area)
                .anyMatch(pos -> {
                    if (level.hasChunkAt(pos)) {
                        var state = level.getBlockState(pos);
                        return state.is(Blocks.CAMPFIRE) && state.getValue(CampfireBlock.LIT);
                    }
                    return false;
                });

            if (nearLitCampfire && player.getHealth() < player.getMaxHealth()) {
                if (data.healedAmount < MAX_HEAL_AMOUNT) {
                    player.heal(1.0F);
                    data.healedAmount += 1.0F;

                    if (data.healedAmount >= MAX_HEAL_AMOUNT) {
                        data.onCooldown = true;
                        data.cooldownStartTick = currentTick;
                    }
                }
            }

            playerHealingData.put(playerId, data);
        }
    }

    private static class PlayerHealingData {
        float healedAmount = 0f;
        boolean onCooldown = false;
        long cooldownStartTick = 0L;

        void reset() {
            healedAmount = 0f;
            onCooldown = false;
            cooldownStartTick = 0L;
        }
    }
}
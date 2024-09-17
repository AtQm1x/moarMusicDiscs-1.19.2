package com.atmx.moarmusicdiscs;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID)
public class ExpandingExplosion {
    private static final Map<Level, List<ExplosionInstance>> activeExplosions = new HashMap<>();

    public static void startExplosion(Level world, BlockPos center, int maxRadius, Block[] blockBlackList, int ticksBetweenExpansion) {
        ExplosionInstance explosion = new ExplosionInstance(world, center, maxRadius, new HashSet<>(Arrays.asList(blockBlackList)), ticksBetweenExpansion);
        activeExplosions.computeIfAbsent(world, k -> new ArrayList<>()).add(explosion);
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            List<ExplosionInstance> explosions = activeExplosions.get(event.level);
            if (explosions != null) {
                Iterator<ExplosionInstance> iterator = explosions.iterator();
                while (iterator.hasNext()) {
                    ExplosionInstance explosion = iterator.next();
                    if (explosion.tick()) {
                        iterator.remove();
                    }
                }
                if (explosions.isEmpty()) {
                    activeExplosions.remove(event.level);
                }
            }
        }
    }


    private static class ExplosionInstance {
        private final Level world;
        private final BlockPos center;
        private final int maxRadius;
        private final Set<Block> blockBlackList;
        private final int ticksBetweenExpansion;
        private int currentRadius = 0;
        private int ticksUntilNextExpansion;

        public ExplosionInstance(Level world, BlockPos center, int maxRadius, Set<Block> blockBlackList, int ticksBetweenExpansion) {
            this.world = world;
            this.center = center;
            this.maxRadius = maxRadius;
            this.blockBlackList = blockBlackList;
            this.ticksBetweenExpansion = ticksBetweenExpansion;
            this.ticksUntilNextExpansion = ticksBetweenExpansion;
        }

        public boolean tick() {
            if (--ticksUntilNextExpansion <= 0) {
                expandExplosion();
                ticksUntilNextExpansion = ticksBetweenExpansion;
            }
            return currentRadius >= maxRadius;
        }

        private void expandExplosion() {
            currentRadius += 1;
            if (currentRadius > maxRadius) return;

            for (int x = -currentRadius; x <= currentRadius; x++) {
                for (int y = -currentRadius; y <= currentRadius; y++) {
                    for (int z = -currentRadius; z <= currentRadius; z++) {
                        double distanceFunction = x*x + (y*y) + z*z;
                        if (distanceFunction >= (currentRadius-1)*(currentRadius-1) &&
                                distanceFunction <= currentRadius*currentRadius) {
                            BlockPos pos = center.offset(x, y, z);
                            BlockState state = world.getBlockState(pos);
                            if (!blockBlackList.contains(state.getBlock())) {
                                world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                            }
                        }
                    }
                }
            }
        }
    }
}
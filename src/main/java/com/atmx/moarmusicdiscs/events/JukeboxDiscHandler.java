package com.atmx.moarmusicdiscs.events;

import com.atmx.moarmusicdiscs.ExampleMod;
import com.atmx.moarmusicdiscs.ExpandingExplosion;
import com.atmx.moarmusicdiscs.MusicController;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID)
public class JukeboxDiscHandler {

    private static BlockPos jukeboxPos = null;
    private static long playEndTime = 0;
    private static Logger LOGGER = ExampleMod.LOGGER;
    private static Level level;

    @SubscribeEvent
    public static void onBlockInteract(BlockEvent.EntityPlaceEvent event) {
        if (event.getLevel().isClientSide()) return; // Server-side only

        BlockPos pos = event.getPos();
        Level world = (Level) event.getLevel();
        BlockState newState = event.getPlacedBlock();
        BlockState oldState = event.getBlockSnapshot().getReplacedBlock();

        if (oldState.getBlock() instanceof JukeboxBlock && newState.getBlock() instanceof JukeboxBlock) {
            boolean wasPlaying = oldState.getValue(JukeboxBlock.HAS_RECORD);
            boolean isPlaying = newState.getValue(JukeboxBlock.HAS_RECORD);

            if (wasPlaying && !isPlaying) {
                // Disc was removed
                ExampleMod.LOGGER.info("Disc was removed at: " + jukeboxPos);                // Reset the jukebox position and cancel the sound end event
                playEndTime = 0;
                jukeboxPos = null;
            }
        }
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        BlockState state = event.getLevel().getBlockState(event.getPos());
        level = event.getLevel();

        // Check if the block is a jukebox and the player is holding a music disc
        if (state.getBlock() instanceof JukeboxBlock && event.getItemStack().getItem() instanceof RecordItem) {
            RecordItem disc = (RecordItem) event.getItemStack().getItem();

            // Store the jukebox's position
            jukeboxPos = event.getPos();

            // Track the music disc's duration (replace with actual disc duration)
            long duration = getMusicDiscDuration(disc);
            playEndTime = System.currentTimeMillis() + duration;

            ExampleMod.LOGGER.info("Music disc inserted into jukebox at: " + jukeboxPos);
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        // If the jukebox is broken, stop the sound early
        if (jukeboxPos != null && event.getPos().equals(jukeboxPos)) {
            ExampleMod.LOGGER.info("Jukebox was broken at: " + jukeboxPos);
            // Reset the jukebox position and cancel the sound end event
            playEndTime = 0;
            jukeboxPos = null;
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        // Check if the music disc should finish playing
        if (playEndTime > 0 && System.currentTimeMillis() >= playEndTime) {
            playEndTime = 0;
            triggerMusicEndEvent();
        }
    }

    private static long getMusicDiscDuration(RecordItem disc) {
        // Replace this with the actual logic for getting the music disc's duration
        return 1000; // Example duration: 10 seconds
    }

    private static void triggerMusicEndEvent() {
        if (jukeboxPos != null) {
            ExampleMod.LOGGER.info("Music disc finished playing at: " + jukeboxPos);

            MusicController.stopAllRecords();
            BlockPos center = jukeboxPos;
            int maxRadius = 150;
            Block[] blockBlacklist = {Blocks.AIR, Blocks.BEDROCK, Blocks.BARRIER};
            int ticksBetweenExpansion = 1; // faster/slower expansion

            ExpandingExplosion.startExplosion(level, center, maxRadius, blockBlacklist, ticksBetweenExpansion);

            jukeboxPos = null;
        }
    }
}

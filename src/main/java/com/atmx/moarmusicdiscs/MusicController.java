package com.atmx.moarmusicdiscs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MusicController {

    @OnlyIn(Dist.CLIENT)
    public static void stopAllMusic() {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getSoundManager();
        minecraft.getSoundManager().stop(null, SoundSource.MUSIC);
    }
    @OnlyIn(Dist.CLIENT)
    public static void stopAllRecords() {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getSoundManager();
        minecraft.getSoundManager().stop(null, SoundSource.RECORDS);
    }

    @OnlyIn(Dist.CLIENT)
    public static void stopSpecificMusic(SoundInstance sound) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getSoundManager();
        minecraft.getSoundManager().stop(sound);
    }

    // Optional: Method to stop all sounds, not just music
    @OnlyIn(Dist.CLIENT)
    public static void stopAllSounds() {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getSoundManager();
        for (SoundSource category : SoundSource.values()) {
            minecraft.getSoundManager().stop(null, category);
        }
    }
}

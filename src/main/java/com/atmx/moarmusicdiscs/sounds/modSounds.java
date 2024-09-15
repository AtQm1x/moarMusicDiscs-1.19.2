package com.atmx.moarmusicdiscs.sounds;

import com.atmx.moarmusicdiscs.ExampleMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class modSounds {

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ExampleMod.MODID);


    public static final RegistryObject<SoundEvent> DISC_ERIKA =
            SOUND_EVENTS.register("disc_erika",
                    () -> new SoundEvent(new ResourceLocation(ExampleMod.MODID, "disc_erika")));


    public static final RegistryObject<SoundEvent> DISC_FREE_BIRD =
            SOUND_EVENTS.register("disc_free_bird",
                    () -> new SoundEvent(new ResourceLocation(ExampleMod.MODID, "disc_free_bird")));



}

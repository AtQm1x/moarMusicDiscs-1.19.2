package com.atmx.moarmusicdiscs.items;
import com.atmx.moarmusicdiscs.ExampleMod;
import com.atmx.moarmusicdiscs.sounds.modSounds;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class modItems {
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }


    // Create a Deferred Register to hold Items which will all be registered under the "moarmusicdiscs" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MODID);

    public static final RegistryObject<Item> DISC_ERIKA_ITEM = ITEMS.register("disc_erika_item",
            () -> new RecordItem(8, modSounds.DISC_ERIKA,
                    new Item.Properties().stacksTo(1), 3600 )
    );

    public static final RegistryObject<Item> DISC_FREE_BIRD_ITEM = ITEMS.register("disc_free_bird_item",
            () -> new RecordItem(8, modSounds.DISC_FREE_BIRD,
                    new Item.Properties().stacksTo(1), 660005 )
    );
}

package cc.bluebits.hongtaiyang.registries.sound;

import cc.bluebits.hongtaiyang.HongTaiyang;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, HongTaiyang.MOD_ID);

    public static final RegistryObject<SoundEvent> HANDSPONGE_SCRUB = registerSoundEvents("handsponge_scrub");
    public static final RegistryObject<SoundEvent> CHALK_WRITING = registerSoundEvents("chalk_writing");
    public static final RegistryObject<SoundEvent> RUNE_EMPTY = registerSoundEvents("rune_empty");


    public static final ForgeSoundType RUNE_SOUNDS = new ForgeSoundType(1f, 1f, ModSounds.RUNE_EMPTY, ModSounds.RUNE_EMPTY, ModSounds.RUNE_EMPTY, ModSounds.RUNE_EMPTY, ModSounds.RUNE_EMPTY);

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(HongTaiyang.MOD_ID, name)));


    }


    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);

    }
}

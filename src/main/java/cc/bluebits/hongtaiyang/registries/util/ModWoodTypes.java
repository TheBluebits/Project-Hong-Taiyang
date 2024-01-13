package cc.bluebits.hongtaiyang.registries.util;

import cc.bluebits.hongtaiyang.HongTaiyang;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

/**
 * A class holding all the wood types used in the mod.
 */
@SuppressWarnings("MissingJavadoc")
public class ModWoodTypes {
	public static final WoodType DARKDWELLER = WoodType.register(new WoodType((HongTaiyang.MOD_ID + ":darkdweller"), BlockSetType.OAK));
}

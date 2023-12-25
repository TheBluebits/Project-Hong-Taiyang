package cc.bluebits.hongtaiyang.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class AxisUtil {
	public static Direction.Axis getRelativeAxisFromPos(BlockPos origin, BlockPos target) {
		if (origin == null || target == null) return null;
		
		int x = origin.getX() - target.getX();
		int y = origin.getY() - target.getY();
		int z = origin.getZ() - target.getZ();
		
		if (x == 0 && y == 0 && z == 0) return null;
		if (x != 0 && y == 0 && z == 0) return Direction.Axis.X;
		if (x == 0 && y != 0 && z == 0) return Direction.Axis.Y;
		if (x == 0 && y == 0) return Direction.Axis.Z; // z is already known to be not equal to 0
		
		return null;
	}
}

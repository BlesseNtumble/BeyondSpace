package beyondspace.world.dimension.Space;

import beyondspace.asjlib.ChunkProviderFlat;
import net.minecraft.world.World;

public class ChunkProviderSpace extends ChunkProviderFlat {

	public ChunkProviderSpace(World world, long seed, boolean features) {
		super(world, seed, "2;255x0;1");
	}
}

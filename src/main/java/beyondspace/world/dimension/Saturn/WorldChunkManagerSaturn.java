package beyondspace.world.dimension.Saturn;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldChunkManagerSpace;
import net.minecraft.world.biome.BiomeGenBase;

public class WorldChunkManagerSaturn extends WorldChunkManagerSpace {

	@Override
	public BiomeGenBase getBiome() {
		return BiomeGenBaseSaturn.saturn;
	}
}
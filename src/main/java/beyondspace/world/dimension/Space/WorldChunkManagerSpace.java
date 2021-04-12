package beyondspace.world.dimension.Space;

import galaxyspace.core.world.gen.GSBiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase;

public class WorldChunkManagerSpace extends micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldChunkManagerSpace {

	public WorldChunkManagerSpace() { }

	public BiomeGenBase getBiome() {
		return GSBiomeGenBase.GSSpace;
	}

}
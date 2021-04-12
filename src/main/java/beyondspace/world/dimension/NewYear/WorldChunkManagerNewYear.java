package beyondspace.world.dimension.NewYear;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldChunkManagerSpace;
import net.minecraft.world.biome.BiomeGenBase;

public class WorldChunkManagerNewYear extends WorldChunkManagerSpace {
	public BiomeGenBase getBiome() {
		return BiomeGenBaseNewYear.newYear;
	}
}

package beyondspace.world.dimension.Halloween;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldChunkManagerSpace;
import net.minecraft.world.biome.BiomeGenBase;

public class WorldChunkManagerHalloween extends WorldChunkManagerSpace {
	
	public BiomeGenBase getBiome() {
		return BiomeGenBaseHalloween.halloween;
	}
}

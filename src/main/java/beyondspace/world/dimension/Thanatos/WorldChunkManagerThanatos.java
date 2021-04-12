package beyondspace.world.dimension.Thanatos;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldChunkManagerSpace;
import net.minecraft.world.biome.BiomeGenBase;

public class WorldChunkManagerThanatos extends WorldChunkManagerSpace {
	
	public BiomeGenBase getBiome() {
		return BiomeGenBaseThanatos.thanatos;
	}
}
package beyondspace.world.dimension.SaturnRings;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldChunkManagerSpace;
import net.minecraft.world.biome.BiomeGenBase;

public class WorldChunkManagerRings extends WorldChunkManagerSpace
{
    @Override
    public BiomeGenBase getBiome()
    {
        return BiomeGenBaseSaturnRings.saturnRings;
    }
}
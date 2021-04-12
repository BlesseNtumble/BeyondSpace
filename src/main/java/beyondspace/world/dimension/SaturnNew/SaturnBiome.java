package beyondspace.world.dimension.SaturnNew;

import galaxyspace.core.world.worldengine.WE_Biome;

public class SaturnBiome extends WE_Biome {
	public SaturnBiome() {
		super(0, false);
		this.setBiomeName("Saturn");
		
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
	}
}
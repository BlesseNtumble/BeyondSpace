package beyondspace.world.dimension.NeptuneNew;

import galaxyspace.core.world.worldengine.WE_Biome;

public class NeptuneBiome extends WE_Biome {
	public NeptuneBiome() {
		super(0, false);
		this.setBiomeName("Neptune");
		
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
	}
}
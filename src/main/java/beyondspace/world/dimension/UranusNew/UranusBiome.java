package beyondspace.world.dimension.UranusNew;

import galaxyspace.core.world.worldengine.WE_Biome;

public class UranusBiome extends WE_Biome {
	public UranusBiome() {
		super(0, false);
		this.setBiomeName("Uranus");
		
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
	}
}
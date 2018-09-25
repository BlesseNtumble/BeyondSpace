package projectandromeda.systems.ArterosSystem.arteros_e.world.gen.we;

import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_BiomeLayer;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_LakeGen;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class Arteros_E_River extends WE_Biome {

	public Arteros_E_River() {
		super(new BiomeProperties("river"));
		
		biomeMinValueOnMap      =  0.12D;
		biomeMaxValueOnMap      =   0.2D;
		biomePersistence        =   0.8D;
		biomeNumberOfOctaves    =      6;
		biomeScaleX             = 240.0D;
		biomeScaleY             =   2.0D;
		biomeSurfaceHeight      =     54;
		biomeInterpolateQuality =     45;

		decorateChunkGen_List.clear();
		createChunkGen_InXZ_List.clear();
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(Blocks.PACKED_ICE, (byte)0,  Blocks.STONE, (byte)0, 3, 1,  7,  2, true);
		standardBiomeLayers.add(Blocks.DIRT   , (byte)0, Blocks.STONE, (byte)0, -256, 0,   -3, -2,  true);
		standardBiomeLayers.add(Blocks.GRASS , (byte)0, Blocks.DIRT , (byte)0, -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.BEDROCK, (byte)0,                                0, 2,  0,  0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}
}

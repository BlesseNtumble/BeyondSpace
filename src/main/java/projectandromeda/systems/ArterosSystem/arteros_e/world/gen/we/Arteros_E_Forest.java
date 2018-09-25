package projectandromeda.systems.ArterosSystem.arteros_e.world.gen.we;

import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_BiomeLayer;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_GrassGen;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_LakeGen;
import net.minecraft.init.Blocks;

public class Arteros_E_Forest extends WE_Biome {

	public Arteros_E_Forest() {
		super(new BiomeProperties("forest"));
		
		biomeMinValueOnMap      =  	0.2D;
		biomeMaxValueOnMap      =  	0.3D;
		biomePersistence        =   1.0D;
		biomeNumberOfOctaves    =      5;
		biomeScaleX             = 240.0D;
		biomeScaleY             =   2.0D;
		biomeSurfaceHeight      =     70;
		biomeInterpolateQuality =     30;
		
		//decorateChunkGen_List.add(new MeteorGenWarm(512, 1, 16));
		//-//
		decorateChunkGen_List.clear();		
		
		WE_GrassGen grass = new WE_GrassGen();
		grass.add(Blocks.TALLGRASS, (byte)1, 8, false, Blocks.GRASS, (byte)0);
		decorateChunkGen_List.add(grass);
		
		WE_LakeGen lakes = new WE_LakeGen();
		lakes.lakeBlock     = Blocks.WATER;
		lakes.lakeBlock_f   =      Blocks.ICE;
		lakes.chunksForLake =                  32 ;
		lakes.fY            =                  131;
		decorateChunkGen_List.add(lakes);
		//-//
		//decorateChunkGen_List.add(new TreeGen(1, 2, 5, 7, 1, 2, 20, 30, 30, 120, 4, 3, false, 0, 0, 0));
		//-//
		
		createChunkGen_InXZ_List.clear();
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(Blocks.PACKED_ICE, (byte)0,  Blocks.STONE, (byte)0, 3, 1,  7,  2, true);
		standardBiomeLayers.add(Blocks.DIRT   , (byte)0, Blocks.STONE, (byte)0, -256, 0,   -3, -2,  true);
		standardBiomeLayers.add(Blocks.GRASS , (byte)0, Blocks.DIRT , (byte)0, -256, 0, -256,  0, false);		
		standardBiomeLayers.add(Blocks.BEDROCK, (byte)0,                                0, 2,  0,  0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}
}

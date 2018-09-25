package projectandromeda.systems.ArterosSystem.arteros_e.world.gen.we;

import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_BiomeLayer;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_SnowGen;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class Arteros_E_Mountain extends WE_Biome {
	public Arteros_E_Mountain() {
		super(new BiomeProperties("mountain"));
		
		biomeMinValueOnMap      =  0.35D;
		biomeMaxValueOnMap      =  0.99D;
		biomePersistence        =   1.6D;
		biomeNumberOfOctaves    =      8;
		biomeScaleX             = 240.0D;
		biomeScaleY             =   2.0D;
		biomeSurfaceHeight      =    135;
		biomeInterpolateQuality =     25;
		
		decorateChunkGen_List.clear();
		createChunkGen_InXZ_List.clear();
		/*
		WE_GrassGen grass = new WE_GrassGen();
		grass.add(BRBlocks.BarnardaCGrass, (byte)0, 8, false, BRBlocks.BarnardaCDirt, (byte)0);
		grass.add(Blocks.stone, (byte)0, 8, false, BRBlocks.BarnardaCDirt, (byte)0);
		decorateChunkGen_List.add(grass);
		*/
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(Blocks.PACKED_ICE, (byte)0,  Blocks.STONE, (byte)0, 3, 1,  7,  2, true);
		standardBiomeLayers.add(Blocks.DIRT   , (byte)0, Blocks.STONE, (byte)0, -256, 0,   -1, -1,  true);
		standardBiomeLayers.add(Blocks.GRASS , (byte)0, Blocks.DIRT , (byte)0, -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.BEDROCK, (byte)0,                                0, 2,  0,  0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
		
		WE_SnowGen snowGen = new WE_SnowGen();
		snowGen.snowPoint       =               120;
		snowGen.randomSnowPoint =               8  ;
		snowGen.snowBlock       =  Blocks.SNOW;
		snowGen.iceBlock        =  Blocks.ICE ;
		snowGen.freezeMaterial  =  Material.WATER;
		createChunkGen_InXZ_List.add(snowGen);
	}
}

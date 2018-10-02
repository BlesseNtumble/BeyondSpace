package projectandromeda.systems.ArterosSystem.arteros_e.world.gen.we;

import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_BiomeLayer;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_SnowGen;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome.BiomeProperties;
import projectandromeda.core.registers.blocks.PABlocks;

public class Arteros_E_Mountain extends WE_Biome {
	public Arteros_E_Mountain() {
		super(new BiomeProperties("mountain"));
		
		biomeMinValueOnMap      =  -0.4D;
		biomeMaxValueOnMap      =  1.3D;
		biomePersistence        =   1.6D;
		biomeNumberOfOctaves    =      6;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.8D;
		biomeSurfaceHeight      =    125;
		biomeInterpolateQuality =     40;
		biomeblockcolor = 0x34c924;
				
		decorateChunkGen_List.clear();
		createChunkGen_InXZ_List.clear();

		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(Blocks.PACKED_ICE, (byte)0, PABlocks.ARTEROS_E_BLOCKS, (byte)2, 3, 1,  7,  2, true);
		standardBiomeLayers.add(PABlocks.ARTEROS_E_BLOCKS, (byte)1, PABlocks.ARTEROS_E_BLOCKS, (byte)2, -256, 0,   -1, -1,  true);
		standardBiomeLayers.add(PABlocks.ARTEROS_E_BLOCKS, (byte)0, PABlocks.ARTEROS_E_BLOCKS, (byte)1, -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.BEDROCK, (byte)0,                                0, 2,  0,  0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
		
		WE_SnowGen snowGen = new WE_SnowGen();
		snowGen.snowPoint       =               110;
		snowGen.randomSnowPoint =               8  ;
		snowGen.snowBlock       =  Blocks.SNOW_LAYER;
		snowGen.iceBlock        =  Blocks.ICE ;
		snowGen.freezeMaterial  =  Material.WATER;
		createChunkGen_InXZ_List.add(snowGen);
	}
}

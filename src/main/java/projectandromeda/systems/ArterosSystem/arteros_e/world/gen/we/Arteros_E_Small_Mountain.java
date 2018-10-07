package projectandromeda.systems.ArterosSystem.arteros_e.world.gen.we;

import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_BiomeLayer;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_SnowGen;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome.BiomeProperties;
import projectandromeda.core.registers.blocks.PABlocks;

public class Arteros_E_Small_Mountain extends WE_Biome {
	public Arteros_E_Small_Mountain() {
		super(new BiomeProperties("small_mountain"));
		
		biomeMinValueOnMap      =  	0.0D;
		biomeMaxValueOnMap      =  	0.1D;
		biomePersistence        =   1.4D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =    100;
		biomeInterpolateQuality =     10;
		biomeBlockGrassColor    = 0x34c924;
				
		decorateChunkGen_List.clear();
		createChunkGen_InXZ_List.clear();

		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(PABlocks.ARTEROS_E_BLOCKS, (byte)1, PABlocks.ARTEROS_E_BLOCKS, (byte)2, -256, 0,   -4, -1,  true);
		standardBiomeLayers.add(PABlocks.ARTEROS_E_BLOCKS, (byte)0, PABlocks.ARTEROS_E_BLOCKS, (byte)1, -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.BEDROCK, (byte)0,                                0, 2,  0,  0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);

	}
}

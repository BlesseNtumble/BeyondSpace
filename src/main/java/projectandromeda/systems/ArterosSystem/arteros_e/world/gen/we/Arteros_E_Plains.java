package projectandromeda.systems.ArterosSystem.arteros_e.world.gen.we;

import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_BiomeLayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome.BiomeProperties;
import projectandromeda.core.registers.blocks.PABlocks;

public class Arteros_E_Plains extends WE_Biome {

	public Arteros_E_Plains() {
		super(new BiomeProperties("plains"));
		
		biomeMinValueOnMap      =  -0.74D;
		biomeMaxValueOnMap      =  -0.3D;
		biomePersistence        =   1.2D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     68;
		biomeInterpolateQuality =     25;
		biomeBlockGrassColor    = 0xFF7515; //0x50c878
		
		decorateChunkGen_List.clear();
		createChunkGen_InXZ_List.clear();
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(PABlocks.ARTEROS_E_BLOCKS, (byte)1, PABlocks.ARTEROS_E_BLOCKS, (byte)2, -256, 0,   -4, -1,  true);
		standardBiomeLayers.add(PABlocks.ARTEROS_E_BLOCKS, (byte)0, PABlocks.ARTEROS_E_BLOCKS, (byte)1, -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.BEDROCK, (byte)0,                                0, 2,  0,  0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}
}

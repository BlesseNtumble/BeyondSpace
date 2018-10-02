package projectandromeda.systems.ArterosSystem.arteros_e.world.gen.we;

import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_BiomeLayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome.BiomeProperties;
import projectandromeda.core.registers.blocks.PABlocks;

public class Arteros_E_Plains extends WE_Biome {

	public Arteros_E_Plains() {
		super(new BiomeProperties("plains"));
		
		biomeMinValueOnMap      =  -0.1D;
		biomeMaxValueOnMap      =   0.6D;
		biomePersistence        =   0.8D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.5D;
		biomeSurfaceHeight      =     70;
		biomeInterpolateQuality =     35;
		biomeblockcolor = 0xFF7515; //0x50c878
		
		decorateChunkGen_List.clear();
		createChunkGen_InXZ_List.clear();
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(Blocks.PACKED_ICE, (byte)0, PABlocks.ARTEROS_E_BLOCKS, (byte)2, 3, 1,  7,  2, true);
		standardBiomeLayers.add(PABlocks.ARTEROS_E_BLOCKS, (byte)1, PABlocks.ARTEROS_E_BLOCKS, (byte)2, -256, 0,   -1, -1,  true);
		standardBiomeLayers.add(PABlocks.ARTEROS_E_BLOCKS, (byte)0, PABlocks.ARTEROS_E_BLOCKS, (byte)1, -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.BEDROCK, (byte)0,                                0, 2,  0,  0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}
}

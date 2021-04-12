//- By Vamig Aliev.
//- https://vk.com/win_vista.

package beyondspace.world.dimension.SaturnNew;

import beyondspace.utils.RegistrationsList;
import galaxyspace.core.world.worldengine.WE_PerlinNoise;
import galaxyspace.core.world.worldengine.additions.WE_CreateChunkGen;
import galaxyspace.core.world.worldengine.additions.WE_GeneratorData;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;

public class WE_SaturnGenerator extends WE_CreateChunkGen {
	@Override
	public void gen(WE_GeneratorData data) {
		for(int x = 0; x < 16; x++)
			for(int z = 0; z < 16; z++) {
				double n = WE_PerlinNoise.PerlinNoise2D(data.chunkProvider.worldObj.getSeed(),
					(data.chunk_X + (long)x) / 16.0D, (data.chunk_Z + (long)z) / 16.0D, 2.1D, 3);
				for(int y = 0; y < 256; y++)
					if     (y ==   0                                    )
						setBlock(data, Blocks.bedrock             , (byte)0, x, y, z);
					else if(y <=  40 + MathHelper.floor_double(n * 2.0D))
						setBlock(data, RegistrationsList.silicate               , (byte)0, x, y, z);
					else if(y <=  80 + MathHelper.floor_double(n * 2.0D))
						setBlock(data, Blocks.stone               , (byte)0, x, y, z);
					else if(y <=  100 + MathHelper.floor_double(n * 2.0D))
						setBlock(data, Blocks.packed_ice               , (byte)0, x, y, z);
					else if(y <= 130 + MathHelper.floor_double(n       ))
						setBlock(data, RegistrationsList.HMetal   , (byte)0, x, y, z);
					else if(y <= 160                                    )
						setBlock(data, RegistrationsList.HHeLiquid, (byte)0, x, y, z);
					else if(y >= 246 && y <= 255
						&&  y >= 248 + MathHelper.floor_double(n * 3.5D)
						&&  y <= 252 - MathHelper.floor_double(n * 3.0D))
						setBlock(data, RegistrationsList.HCloud   , (byte)0, x, y, z);
			}
	}

}
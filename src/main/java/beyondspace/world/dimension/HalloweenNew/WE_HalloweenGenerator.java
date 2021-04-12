//- By Vamig Aliev.
//- https://vk.com/win_vista.

package beyondspace.world.dimension.HalloweenNew;

import galaxyspace.core.world.worldengine.WE_PerlinNoise;
import galaxyspace.core.world.worldengine.additions.WE_CreateChunkGen;
import galaxyspace.core.world.worldengine.additions.WE_GeneratorData;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;

public class WE_HalloweenGenerator extends WE_CreateChunkGen {
	@Override
	public void gen(WE_GeneratorData data) {
		for(int x = 0; x < 16; x++)
			for(int z = 0; z < 16; z++) {
				double n = WE_PerlinNoise.PerlinNoise2D(data.chunkProvider.worldObj.getSeed(),
					(data.chunk_X + (long)x) / 16.0D, (data.chunk_Z + (long)z) / 16.0D, 2.1D, 3);
				for(int y = 0; y < 256; y++)
					if     (y ==   0                                    )
						setBlock(data, Blocks.bedrock             , (byte)0, x, y, z);
					else if(y <=  25 + MathHelper.floor_double(n * 2.0D))
						setBlock(data, Blocks.stone              , (byte)0, x, y, z);
					else if(y <=  35 + MathHelper.floor_double(n       ))
						setBlock(data, Blocks.gravel               , (byte)0, x, y, z);
					else if(y <= 45 + MathHelper.floor_double(n        ))
						setBlock(data, Blocks.mossy_cobblestone    , (byte)0, x, y, z);
					else if(y <= 60                                    )
						setBlock(data, Blocks.grass, (byte)0, x, y, z);
			}
	}
}
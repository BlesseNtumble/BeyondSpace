package beyondspace.world.generation.structures;

import java.util.Random;

import beyondspace.utils.RegistrationsList;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class IcePeaks extends WorldGenerator {
	
	protected Block[] GetValidSpawnBlocks() {
		return new Block[] { Blocks.snow_layer, RegistrationsList.snow, Blocks.snow };
	}

	public boolean LocationIsValidSpawn(World world, int x, int y, int z) {
		Block checkBlock = world.getBlock(x, y - 1, z);
		Block blockAbove = world.getBlock(x, y , z);
		Block blockBelow = world.getBlock(x, y - 2, z);

		for (Block i : GetValidSpawnBlocks()) {
			if (blockAbove != Blocks.air) {
				return false;
			}
			if (checkBlock == i) {
				return true;
			} else if (checkBlock == Blocks.snow_layer && blockBelow == i) {
				return true;
			} else if (checkBlock.getMaterial() == Material.plants && blockBelow == i) {
				return true;
			}
		}
		return false;
	}

	public boolean generate(World world, Random rand, int x, int y, int z) {
		world.setBlock(x + 2, y + 0, z + 0, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 0, z + 0, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 4, y + 0, z + 0, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 1, y + 0, z + 1, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 2, y + 0, z + 1, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 0, z + 1, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 4, y + 0, z + 1, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 5, y + 0, z + 1, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 0, y + 0, z + 2, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 1, y + 0, z + 2, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 2, y + 0, z + 2, Blocks.ice, 0, 3);
		world.setBlock(x + 3, y + 0, z + 2, Blocks.ice, 0, 3);
		world.setBlock(x + 4, y + 0, z + 2, Blocks.ice, 0, 3);
		world.setBlock(x + 5, y + 0, z + 2, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 6, y + 0, z + 2, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 0, y + 0, z + 3, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 1, y + 0, z + 3, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 2, y + 0, z + 3, Blocks.ice, 0, 3);
		world.setBlock(x + 3, y + 0, z + 3, Blocks.ice, 0, 3);
		world.setBlock(x + 4, y + 0, z + 3, Blocks.ice, 0, 3);
		world.setBlock(x + 5, y + 0, z + 3, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 6, y + 0, z + 3, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 0, y + 0, z + 4, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 1, y + 0, z + 4, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 2, y + 0, z + 4, Blocks.ice, 0, 3);
		world.setBlock(x + 3, y + 0, z + 4, Blocks.ice, 0, 3);
		world.setBlock(x + 4, y + 0, z + 4, Blocks.ice, 0, 3);
		world.setBlock(x + 5, y + 0, z + 4, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 6, y + 0, z + 4, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 1, y + 0, z + 5, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 2, y + 0, z + 5, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 0, z + 5, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 4, y + 0, z + 5, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 5, y + 0, z + 5, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 2, y + 0, z + 6, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 0, z + 6, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 4, y + 0, z + 6, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 2, y + 1, z + 1, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 1, z + 1, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 4, y + 1, z + 1, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 1, y + 1, z + 2, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 2, y + 1, z + 2, Blocks.ice, 0, 3);
		world.setBlock(x + 3, y + 1, z + 2, Blocks.ice, 0, 3);
		world.setBlock(x + 4, y + 1, z + 2, Blocks.ice, 0, 3);
		world.setBlock(x + 5, y + 1, z + 2, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 1, y + 1, z + 3, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 2, y + 1, z + 3, Blocks.ice, 0, 3);
		world.setBlock(x + 3, y + 1, z + 3, Blocks.ice, 0, 3);
		world.setBlock(x + 4, y + 1, z + 3, Blocks.ice, 0, 3);
		world.setBlock(x + 5, y + 1, z + 3, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 1, y + 1, z + 4, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 2, y + 1, z + 4, Blocks.ice, 0, 3);
		world.setBlock(x + 3, y + 1, z + 4, Blocks.ice, 0, 3);
		world.setBlock(x + 4, y + 1, z + 4, Blocks.ice, 0, 3);
		world.setBlock(x + 5, y + 1, z + 4, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 2, y + 1, z + 5, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 1, z + 5, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 4, y + 1, z + 5, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 2, z + 1, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 4, y + 2, z + 1, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 2, y + 2, z + 2, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 2, z + 2, Blocks.ice, 0, 3);
		world.setBlock(x + 4, y + 2, z + 2, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 1, y + 2, z + 3, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 2, y + 2, z + 3, Blocks.ice, 0, 3);
		world.setBlock(x + 3, y + 2, z + 3, Blocks.ice, 0, 3);
		world.setBlock(x + 4, y + 2, z + 3, Blocks.ice, 0, 3);
		world.setBlock(x + 5, y + 2, z + 3, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 1, y + 2, z + 4, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 2, y + 2, z + 4, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 2, z + 4, Blocks.ice, 0, 3);
		world.setBlock(x + 4, y + 2, z + 4, Blocks.ice, 0, 3);
		world.setBlock(x + 5, y + 2, z + 4, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 2, z + 5, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 4, y + 2, z + 5, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 3, z + 1, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 2, y + 3, z + 2, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 3, z + 2, Blocks.ice, 0, 3);
		world.setBlock(x + 4, y + 3, z + 2, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 2, y + 3, z + 3, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 3, z + 3, Blocks.ice, 0, 3);
		world.setBlock(x + 4, y + 3, z + 3, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 2, y + 3, z + 4, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 3, z + 4, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 4, y + 3, z + 4, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 5, y + 3, z + 4, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 4, y + 3, z + 5, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 4, z + 2, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 2, y + 4, z + 3, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 4, z + 3, Blocks.ice, 0, 3);
		world.setBlock(x + 4, y + 4, z + 3, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 4, z + 4, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 4, y + 4, z + 4, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 5, z + 2, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 2, y + 5, z + 3, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 5, z + 3, Blocks.ice, 0, 3);
		world.setBlock(x + 4, y + 5, z + 3, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 5, z + 4, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 2, y + 6, z + 2, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 6, z + 2, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 4, y + 6, z + 2, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 2, y + 6, z + 3, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 6, z + 3, Blocks.ice, 0, 3);
		world.setBlock(x + 4, y + 6, z + 3, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 2, y + 6, z + 4, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 6, z + 4, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 4, y + 6, z + 4, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 7, z + 2, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 2, y + 7, z + 3, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 7, z + 3, Blocks.ice, 0, 3);
		world.setBlock(x + 4, y + 7, z + 3, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 7, z + 4, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 8, z + 2, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 8, z + 3, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 9, z + 3, Blocks.packed_ice, 0, 3);
		world.setBlock(x + 3, y + 10, z + 3, Blocks.packed_ice, 0, 3);
		return true;
	}
}
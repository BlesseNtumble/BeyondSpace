package beyondspace.world.generation.structures;

import java.util.Random;

import beyondspace.utils.RegistrationsList;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class HCloud1 extends WorldGenerator {
	protected Block[] GetValidSpawnBlocks() {
		return new Block[] { Blocks.air, };
	}

	public boolean LocationIsValidSpawn(World world, int x, int y, int z) {

		Block checkBlock = world.getBlock(x, y - 1, z);
		Block blockAbove = world.getBlock(x, y, z);
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
		int i = rand.nextInt(2);

		if (i == 0) {
			generate_r0(world, rand, x, y, z);
		}

		if (i == 1) {
			generate_r1(world, rand, x, y, z);
		}

		return true;

	}

	public boolean generate_r0(World world, Random rand, int x, int y, int z) {
		if (!LocationIsValidSpawn(world, x + 2, y, z + 3)) {
			return false;
		}

		world.setBlock(x + 2, y + 0, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 0, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 0, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 0, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 0, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 0, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 0, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 0, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 0, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 0, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 0, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 1, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 6, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 6, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 6, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 5, RegistrationsList.HCloud, 0, 3);
		return true;

	}
	public boolean generate_r1(World world, Random rand, int x, int y, int z)
{
		if(!LocationIsValidSpawn(world, x + 3, y, z + 2))
		{
			return false;
		}

		world.setBlock(x + 1, y + 0, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 0, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 0, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 0, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 0, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 0, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 0, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 0, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 0, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 0, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 0, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 1, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 6, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 6, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 6, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		return true;

	}

}
package beyondspace.world.generation.structures;

import java.util.Random;

import beyondspace.utils.RegistrationsList;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class HCloud6 extends WorldGenerator {
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
		int i = rand.nextInt(4);

		if (i == 0) {
			generate_r0(world, rand, x, y, z);
		}

		if (i == 1) {
			generate_r1(world, rand, x, y, z);
		}

		if (i == 2) {
			generate_r2(world, rand, x, y, z);
		}

		if (i == 3) {
			generate_r3(world, rand, x, y, z);
		}

		return true;

	}

	public boolean generate_r0(World world, Random rand, int x, int y, int z) {
		if (!LocationIsValidSpawn(world, x + 7, y, z + 3)) {
			return false;
		}

		world.setBlock(x + 1, y + 0, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 0, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 0, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 0, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 0, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 0, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 0, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 0, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 0, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 0, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 6, y + 0, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 0, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 0, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 0, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 0, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 13, y + 1, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 12, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 13, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 14, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 10, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 11, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 12, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 13, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 6, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 8, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 9, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 10, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 11, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 12, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 6, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 7, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 8, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 9, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 10, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 1, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 1, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 6, y + 1, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 8, y + 1, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 12, y + 2, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 13, y + 2, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 14, y + 2, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 11, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 12, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 13, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 14, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 9, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 10, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 11, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 12, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 13, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 14, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 6, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 7, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 8, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 9, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 10, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 11, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 12, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 13, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 2, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 2, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 2, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 6, y + 2, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 7, y + 2, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 8, y + 2, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 9, y + 2, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 10, y + 2, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 11, y + 2, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 2, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 2, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 6, y + 2, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 7, y + 2, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 8, y + 2, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 9, y + 2, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 13, y + 3, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 12, y + 3, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 13, y + 3, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 14, y + 3, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 10, y + 3, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 11, y + 3, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 12, y + 3, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 13, y + 3, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 6, y + 3, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 8, y + 3, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 9, y + 3, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 10, y + 3, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 11, y + 3, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 12, y + 3, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 3, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 6, y + 3, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 7, y + 3, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 8, y + 3, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 9, y + 3, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 10, y + 3, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 6, y + 3, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 8, y + 3, z + 5, RegistrationsList.HCloud, 0, 3);
		return true;

	}
	public boolean generate_r1(World world, Random rand, int x, int y, int z)
{
		if(!LocationIsValidSpawn(world, x + 3, y, z + 7))
		{
			return false;
		}

		world.setBlock(x + 3, y + 0, z + 13, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 0, z + 12, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 0, z + 10, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 0, z + 9, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 0, z + 14, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 0, z + 13, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 0, z + 12, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 0, z + 11, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 0, z + 10, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 0, z + 9, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 0, z + 8, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 0, z + 13, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 0, z + 12, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 0, z + 10, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 0, z + 9, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 14, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 13, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 12, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 11, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 10, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 9, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 8, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 6, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 14, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 13, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 12, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 11, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 10, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 9, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 8, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 7, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 6, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 1, z + 14, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 1, z + 13, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 1, z + 12, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 1, z + 11, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 1, z + 10, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 1, z + 9, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 1, z + 8, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 1, z + 6, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 2, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 13, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 12, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 10, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 9, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 8, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 7, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 6, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 2, z + 14, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 2, z + 13, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 2, z + 12, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 2, z + 11, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 2, z + 10, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 2, z + 9, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 2, z + 8, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 2, z + 7, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 2, z + 6, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 2, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 2, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 2, z + 13, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 2, z + 12, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 2, z + 10, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 2, z + 9, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 2, z + 8, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 2, z + 7, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 2, z + 6, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 2, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 3, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 3, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 3, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 3, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 3, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 3, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 3, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 3, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 3, z + 8, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 3, z + 6, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 3, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 3, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 3, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 3, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 3, z + 9, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 3, z + 8, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 3, z + 7, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 3, z + 6, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 3, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 3, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 3, z + 8, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 3, z + 6, RegistrationsList.HCloud, 0, 3);
		return true;

	}
	public boolean generate_r2(World world, Random rand, int x, int y, int z)
	{
		if(!LocationIsValidSpawn(world, x + 7, y, z + 3))
		{
			return false;
		}

		world.setBlock(x + 13, y + 0, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 12, y + 0, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 10, y + 0, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 9, y + 0, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 14, y + 0, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 13, y + 0, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 12, y + 0, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 11, y + 0, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 10, y + 0, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 9, y + 0, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 8, y + 0, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 13, y + 0, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 12, y + 0, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 10, y + 0, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 9, y + 0, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 14, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 13, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 12, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 11, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 10, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 9, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 8, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 6, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 14, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 13, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 12, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 11, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 10, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 9, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 8, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 7, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 6, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 14, y + 1, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 13, y + 1, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 12, y + 1, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 11, y + 1, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 10, y + 1, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 9, y + 1, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 8, y + 1, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 6, y + 1, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 2, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 2, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 13, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 12, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 10, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 9, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 8, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 7, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 6, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 14, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 13, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 12, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 11, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 10, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 9, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 8, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 7, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 6, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 13, y + 2, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 12, y + 2, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 10, y + 2, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 9, y + 2, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 8, y + 2, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 7, y + 2, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 6, y + 2, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 2, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 3, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 3, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 3, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 3, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 3, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 3, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 3, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 3, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 8, y + 3, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 6, y + 3, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 3, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 3, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 3, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 3, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 9, y + 3, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 8, y + 3, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 7, y + 3, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 6, y + 3, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 3, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 3, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 8, y + 3, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 6, y + 3, z + 0, RegistrationsList.HCloud, 0, 3);
		return true;

	}
	public boolean generate_r3(World world, Random rand, int x, int y, int z)
{
		if(!LocationIsValidSpawn(world, x + 3, y, z + 7))
		{
			return false;
		}

		world.setBlock(x + 2, y + 0, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 0, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 0, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 0, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 0, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 0, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 0, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 0, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 0, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 0, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 0, z + 6, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 0, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 0, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 0, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 0, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 1, z + 13, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 12, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 13, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 1, z + 14, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 10, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 11, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 12, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 1, z + 13, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 6, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 8, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 9, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 10, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 11, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 1, z + 12, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 6, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 7, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 8, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 9, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 1, z + 10, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 1, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 1, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 1, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 1, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 1, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 1, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 1, z + 6, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 1, z + 8, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 2, z + 12, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 2, z + 13, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 2, z + 14, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 2, z + 11, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 2, z + 12, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 2, z + 13, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 2, z + 14, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 9, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 10, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 11, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 12, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 13, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 2, z + 14, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 6, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 7, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 8, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 9, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 10, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 11, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 12, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 2, z + 13, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 0, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 3, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 6, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 7, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 8, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 9, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 10, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 2, z + 11, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 2, z + 1, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 2, z + 2, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 2, z + 4, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 2, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 2, z + 6, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 2, z + 7, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 2, z + 8, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 2, z + 9, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 5, y + 3, z + 13, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 3, z + 12, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 3, z + 13, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 4, y + 3, z + 14, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 3, z + 10, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 3, z + 11, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 3, z + 12, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 3, y + 3, z + 13, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 3, z + 6, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 3, z + 8, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 3, z + 9, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 3, z + 10, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 3, z + 11, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 2, y + 3, z + 12, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 3, z + 5, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 3, z + 6, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 3, z + 7, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 3, z + 8, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 3, z + 9, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 1, y + 3, z + 10, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 3, z + 6, RegistrationsList.HCloud, 0, 3);
		world.setBlock(x + 0, y + 3, z + 8, RegistrationsList.HCloud, 0, 3);
		return true;

	}

}
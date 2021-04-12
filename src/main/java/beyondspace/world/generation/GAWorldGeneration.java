package beyondspace.world.generation;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class GAWorldGeneration implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.dimensionId) {
		case 0: break;
		case 100: generateVanillaOres(world, random, chunkX * 16, chunkZ * 16); break;/**Jupiter**/
		case 101: generateVanillaOres(world, random, chunkX * 16, chunkZ * 16); break;/**Saturn**/
		}
	}

	private void generateVanillaOres(World world, Random random, int x, int z) {
		this.addOreSpawn(Blocks.coal_ore, 0, Blocks.stone, world, random, x, z, 16, 16, 4 + random.nextInt(4), 10, 1, 16);
		this.addOreSpawn(Blocks.iron_ore, 0, Blocks.stone, world, random, x, z, 16, 16, 6 + random.nextInt(6), 15, 1, 16);
		this.addOreSpawn(GCBlocks.basicBlock, 5, Blocks.stone, world, random, x, z, 16, 16, 6 + random.nextInt(6), 15, 1, 16); // Copper
		this.addOreSpawn(GCBlocks.basicBlock, 6, Blocks.stone, world, random, x, z, 16, 16, 6 + random.nextInt(6), 15, 1, 16); // Tin
		this.addOreSpawn(GCBlocks.basicBlock, 7, Blocks.stone, world, random, x, z, 16, 16, 6 + random.nextInt(6), 15, 1, 16); // Aluminum
		this.addOreSpawn(GCBlocks.basicBlock, 8, Blocks.stone, world, random, x, z, 16, 16, 2 + random.nextInt(2), 5, 1, 16); // Silicon
		this.addOreSpawn(Blocks.lapis_ore, 0, Blocks.stone, world, random, x, z, 16, 16, 2 + random.nextInt(2), 5, 1, 16);
		this.addOreSpawn(Blocks.redstone_ore, 0, Blocks.stone, world, random, x, z, 16, 16, 4 + random.nextInt(4), 5, 1, 16);
		this.addOreSpawn(Blocks.gold_ore, 0, Blocks.stone, world, random, x, z, 16, 16, 6 + random.nextInt(6), 15, 1, 16);
		this.addOreSpawn(Blocks.diamond_ore, 0, Blocks.stone, world, random, x, z, 16, 16, 8 + random.nextInt(8), 40, 1, 16);
		this.addOreSpawn(Blocks.emerald_ore, 0, Blocks.stone, world, random, x, z, 16, 16, 6 + random.nextInt(6), 20, 1, 16);
	}

	private void addOreSpawn(Block generatable, int meta, Block filler, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int maxVeinSize, int chanceToSpawn, int minY, int maxY) {
		for (int i = 0; i < chanceToSpawn; i++) {
			int posX = blockXPos + random.nextInt(maxX);
			int posY = maxY - random.nextInt(maxY - minY);
			int posZ = blockZPos + random.nextInt(maxZ);
			(new WorldGenMinable(generatable, meta, maxVeinSize, filler)).generate(world, random, posX, posY, posZ);
		}
	}
}

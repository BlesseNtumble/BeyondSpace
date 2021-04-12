package beyondspace.world.dimension.NewYear;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import beyondspace.utils.RegistrationsList;
import beyondspace.world.generation.structures.IcePeaks;
import beyondspace.world.generation.structures.MiddleSpruce;
import beyondspace.world.generation.structures.NewYearHouse;
import beyondspace.world.generation.structures.NewYearSpruce;
import beyondspace.world.generation.structures.SmallSpruce;
import micdoodle8.mods.galacticraft.core.perlin.NoiseModule;
import micdoodle8.mods.galacticraft.core.perlin.generator.Gradient;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;

public class ChunkProviderNewYear extends ChunkProviderGenerate implements IChunkProvider {

    private final Random rand;
    
    final Block topBlockID = Blocks.snow_layer;
    final Block fillBlockID = RegistrationsList.snow;
    final Block lowerBlockID = Blocks.snow;

    private final NoiseModule noiseGen1;
    private final NoiseModule noiseGen2;
    private final NoiseModule noiseGen3;
    private final NoiseModule noiseGen4;

    private final World worldObj;

    private BiomeGenBase[] biomesForGeneration = { BiomeGenBaseNewYear.newYear };
    
    private static final int CRATER_PROB = 300;

    // DO NOT CHANGE
    private static final int MID_HEIGHT = 63;
    private static final int CHUNK_SIZE_X = 16;
    private static final int CHUNK_SIZE_Y = 128;
    private static final int CHUNK_SIZE_Z = 16;

    public ChunkProviderNewYear(World par1World, long par2, boolean par4) {
        super(par1World, par2, par4);
        this.worldObj = par1World;
        this.rand = new Random(par2);
        this.noiseGen1 = new Gradient(this.rand.nextLong(), 4, 0.25F);
        this.noiseGen2 = new Gradient(this.rand.nextLong(), 4, 0.25F);
        this.noiseGen3 = new Gradient(this.rand.nextLong(), 1, 0.25F);
        this.noiseGen4 = new Gradient(this.rand.nextLong(), 1, 0.25F);
    }

    public void generateTerrain(int chunkX, int chunkZ, Block[] idArray, byte[] metaArray) {
        this.noiseGen1.setFrequency(0.0125F);
        this.noiseGen2.setFrequency(0.015F);
        this.noiseGen3.setFrequency(0.01F);
        this.noiseGen4.setFrequency(0.02F);

        for (int x = 0; x < this.CHUNK_SIZE_X; x++) {
            for (int z = 0; z < this.CHUNK_SIZE_Z; z++) {
                final double d = this.noiseGen1.getNoise(x + chunkX * 16, z + chunkZ * 16) * 8;
                final double d2 = this.noiseGen2.getNoise(x + chunkX * 16, z + chunkZ * 16) * 24;
                double d3 = this.noiseGen3.getNoise(x + chunkX * 16, z + chunkZ * 16) - 0.1;
                d3 *= 4;

                double yDev = 0;

                if (d3 < 0.0D) {
                    yDev = d;
                } else if (d3 > 1.0D) {
                    yDev = d2;
                } else {
                    yDev = d + (d2 - d) * d3;
                }

                for (int y = 0; y < this.CHUNK_SIZE_Y; y++) {
                    if (y < this.MID_HEIGHT + yDev) {
                        idArray[this.getIndex(x, y, z)] = this.lowerBlockID;
                        metaArray[this.getIndex(x, y, z)] = 0;
                    }
                }
            }
        }
    }

    @Override
    public void replaceBlocksForBiome(int par1, int par2, Block[] arrayOfIDs, byte[] arrayOfMeta, BiomeGenBase[] par4ArrayOfBiomeGenBase) {
        final int var5 = 20;
        for (int var8 = 0; var8 < 16; ++var8) {
            for (int var9 = 0; var9 < 16; ++var9) {
                final int var12 = (int) (this.noiseGen4.getNoise(var8 + par1 * 16, var9 * par2 * 16) / 3.0D + 3.0D + this.rand.nextDouble() * 0.25D);
                int var13 = -1;
                Block var14 = this.topBlockID;
                byte var14m = (byte)this.rand.nextInt(3);
                Block var15 = this.fillBlockID;
                byte var15m = 0;

                for (int var16 = 127; var16 >= 0; --var16) {
                    final int index = this.getIndex(var8, var16, var9);
                    arrayOfMeta[index] = 0;

                    if (var16 <= 0 + this.rand.nextInt(5)) {
                        arrayOfIDs[index] = Blocks.bedrock;
                    } else {
                        final Block var18 = arrayOfIDs[index];
                        if (Blocks.air == var18) {
                            var13 = -1;
                        } else if (var18 == this.lowerBlockID) {
                            arrayOfMeta[index] = 0;

                            if (var13 == -1) {
                                if (var12 <= 0) {
                                    var14 = Blocks.air;
                                    var14m = 0;
                                    var15 = this.lowerBlockID;
                                    var15m = 0;
                                } else if (var16 >= var5 - -16 && var16 <= var5 + 1) {
                                    var14 = this.topBlockID;
                                    var14m = (byte)this.rand.nextInt(3);
                                    var14 = this.fillBlockID;
                                    var14m = 0;
                                }

                                var13 = var12;

                                if (var16 >= var5 - 1) {
                                    arrayOfIDs[index] = var14;
                                    arrayOfMeta[index] = var14m;
                                } else if (var16 < var5 - 1 && var16 >= var5 - 2) {
                                    arrayOfIDs[index] = var15;
                                    arrayOfMeta[index] = var15m;
                                }
                            } else if (var13 > 0) {
                                --var13;
                                arrayOfIDs[index] = var15;
                                arrayOfMeta[index] = var15m;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Chunk provideChunk(int par1, int par2) {
        this.rand.setSeed(par1 * 341873128712L + par2 * 132897987541L);
        final Block[] ids = new Block[16 * 16 * 256];
        final byte[] meta = new byte[16 * 16 * 256];
        Arrays.fill(ids, Blocks.air);
        this.generateTerrain(par1, par2, ids, meta);
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, par1 * 16, par2 * 16, 16, 16);
        this.replaceBlocksForBiome(par1, par2, ids, meta, this.biomesForGeneration);

        final Chunk var4 = new Chunk(this.worldObj, ids, meta, par1, par2);

        var4.generateSkylightMap();
        return var4;
    }

    @Override
    public boolean chunkExists(int par1, int par2) {
        return true;
    }

    @Override
	public boolean unloadQueuedChunks() {
		return false;
	}

    @Override
	public int getLoadedChunkCount() {
		return 0;
	}

    private int getIndex(int x, int y, int z) {
        return (x * 16 + z) * 256 + y;
    }

    private double randFromPoint(int x, int z) {
        int n;
        n = x + z * 57;
        n = n << 13 ^ n;
        return 1.0 - (n * (n * n * 15731 + 789221) + 1376312589 & 0x7fffffff) / 1073741824.0;
    }

    public void decoratePlanet(World world, Random random, int chunkX, int chunkZ) {
    	for (int i = 0; i < 3; i++) {
    		int chance = random.nextInt(500);
    		
    		
			if (chance % 50 == 0) {
	    		int x = chunkX + random.nextInt(16) - 8;
				int z = chunkZ + random.nextInt(16) - 8;
				int y = world.getHeightValue(x, z) + 2;
				if (world.getBlock(x, world.getHeightValue(x, z), z) == Blocks.snow_layer || world.getBlock(x, world.getHeightValue(x, z), z) == RegistrationsList.snow)
				new IcePeaks().generate(world, random, x, y, z);
			}
			
			if (chance % 50 + 5 == 10) {
	    		int x = chunkX + random.nextInt(16) - 8;
				int z = chunkZ + random.nextInt(16) - 8;
				int y = world.getHeightValue(x, z) + 2;
				if (world.getBlock(x, world.getHeightValue(x, z), z) == Blocks.snow_layer || world.getBlock(x, world.getHeightValue(x, z), z) == RegistrationsList.snow)
				new SmallSpruce().generate(world, random, x, y, z);
			}
			
			if (chance % 200 == 0) {
	    		int x = chunkX + random.nextInt(16) - 8;
				int z = chunkZ + random.nextInt(16) - 8;
				int y = world.getHeightValue(x, z) + 2;
				if (world.getBlock(x, world.getHeightValue(x, z), z) == Blocks.snow_layer || world.getBlock(x, world.getHeightValue(x, z), z) == RegistrationsList.snow)
				new MiddleSpruce().generate(world, random, x, y, z);
			}
			
			if (chance == 0) {
	    		int x = chunkX + random.nextInt(16) - 8;
				int z = chunkZ + random.nextInt(16) - 8;
				int y = world.getHeightValue(x, z) + 3;
				if (world.getBlock(x, world.getHeightValue(x, z), z) == Blocks.snow_layer || world.getBlock(x, world.getHeightValue(x, z), z) == RegistrationsList.snow)
				new NewYearHouse().generate(world, random, x, y, z);
			}
		}
    	
		if (chunkX == 0 && chunkZ == 0) {
			int x1 = 0;
			int z1 = 0;
			int y1 = world.getHeightValue(x1, z1);
			new NewYearSpruce().generate(world, random, x1, y1, z1);
		}
    }

    @Override
    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3) {
        BlockFalling.fallInstantly = true;
        final int k = par2 * 16;
        final int l = par3 * 16;
        this.worldObj.getBiomeGenForCoords(k + 16, l + 16);
        this.rand.setSeed(this.worldObj.getSeed());
        final long i1 = this.rand.nextLong() / 2L * 2L + 1L;
        final long j1 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed(par2 * i1 + par3 * j1 ^ this.worldObj.getSeed());
        this.decoratePlanet(this.worldObj, this.rand, k, l);
        BlockFalling.fallInstantly = false;
    }

    @Override
    public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate) {
        return true;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public String makeString() {
        return "ChunkNewYear";
    }

    @Override
    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int i, int j, int k){
            return null;
    }

    @Override
    public void recreateStructures(int par1, int par2) {
    	return;
    }
}
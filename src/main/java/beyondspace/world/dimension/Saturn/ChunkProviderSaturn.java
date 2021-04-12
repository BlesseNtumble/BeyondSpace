package beyondspace.world.dimension.Saturn;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import beyondspace.world.generation.structures.HCloud1;
import beyondspace.world.generation.structures.HCloud2;
import beyondspace.world.generation.structures.HCloud3;
import beyondspace.world.generation.structures.HCloud4;
import beyondspace.world.generation.structures.HCloud5;
import beyondspace.world.generation.structures.HCloud6;
import beyondspace.world.generation.structures.HCloud7;
import beyondspace.world.generation.structures.HCloud8;
import beyondspace.world.generation.structures.HCloud9;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;

public class ChunkProviderSaturn implements IChunkProvider {
    private World worldObj;
    private Random random;
    private final Block[] cachedBlockIDs = new Block[256];
    private final byte[] cachedBlockMetadata = new byte[256];
    private final FlatGeneratorInfo flatWorldGenInfo;
    
	public ChunkProviderSaturn(World world, long seed, String genString) {
        this.worldObj = world;
        this.random = new Random(seed);
        this.flatWorldGenInfo = FlatGeneratorInfo.createFlatGeneratorFromString(genString);

        Iterator iterator = this.flatWorldGenInfo.getFlatLayers().iterator();

        while (iterator.hasNext()) {
            FlatLayerInfo flatlayerinfo = (FlatLayerInfo)iterator.next();

            for (int j = flatlayerinfo.getMinY(); j < flatlayerinfo.getMinY() + flatlayerinfo.getLayerCount(); ++j) {
                this.cachedBlockIDs[j] = flatlayerinfo.func_151536_b();
                this.cachedBlockMetadata[j] = (byte)flatlayerinfo.getFillBlockMeta();
            }
        }
    }

    public Chunk loadChunk(int x, int z) {
        return this.provideChunk(x, z);
    }

    public Chunk provideChunk(int x, int z) {
        Chunk chunk = new Chunk(this.worldObj, x, z);
        int l;

        for (int k = 0; k < this.cachedBlockIDs.length; ++k) {
            Block block = this.cachedBlockIDs[k];

            if (block != null) {
                l = k >> 4;
                ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[l];

                if (extendedblockstorage == null) {
                    extendedblockstorage = new ExtendedBlockStorage(k, !this.worldObj.provider.hasNoSky);
                    chunk.getBlockStorageArray()[l] = extendedblockstorage;
                }

                for (int i1 = 0; i1 < 16; ++i1) {
                    for (int j1 = 0; j1 < 16; ++j1) {
                        extendedblockstorage.func_150818_a(i1, k & 15, j1, block);
                        extendedblockstorage.setExtBlockMetadata(i1, k & 15, j1, this.cachedBlockMetadata[k]);
                    }
                }
            }
        }

        chunk.generateSkylightMap();
        BiomeGenBase[] abiomegenbase = this.worldObj.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[])null, x * 16, z * 16, 16, 16);
        byte[] abyte = chunk.getBiomeArray();

        for (l = 0; l < abyte.length; ++l) {
            abyte[l] = (byte)abiomegenbase[l].biomeID;
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    public boolean chunkExists(int x, int z) {
        return true;
    }

    @Override
    public void populate(IChunkProvider chunk, int x, int z) {
        int k = x * 16;
        int l = z * 16;
        this.random.setSeed(this.worldObj.getSeed());
        long i1 = this.random.nextLong() / 2L * 2L + 1L;
        long j1 = this.random.nextLong() / 2L * 2L + 1L;
        this.random.setSeed((long)x * i1 + (long)z * j1 ^ this.worldObj.getSeed());
        
		int a = k + random.nextInt(16) - 8;
		int b = random.nextInt(64) + 176 + 16;
		int c = l + random.nextInt(16) - 8;
		
		switch(random.nextInt(9)) {
			case 0: new HCloud1().generate(worldObj, random, a, b, c); break;
			case 1: new HCloud2().generate(worldObj, random, a, b, c); break;
			case 2: new HCloud3().generate(worldObj, random, a, b, c); break;
			case 3: new HCloud4().generate(worldObj, random, a, b, c); break;
			case 4: new HCloud5().generate(worldObj, random, a, b, c); break;
			case 5: new HCloud6().generate(worldObj, random, a, b, c); break;
			case 6: new HCloud7().generate(worldObj, random, a, b, c); break;
			case 7: new HCloud8().generate(worldObj, random, a, b, c); break;
			case 8: new HCloud9().generate(worldObj, random, a, b, c); break;
		}
    }

    public boolean saveChunks(boolean b, IProgressUpdate progress) {
        return true;
    }

    public void saveExtraData() {}

    public boolean unloadQueuedChunks() {
        return false;
    }

    public boolean canSave() {
        return true;
    }

    public String makeString() {
        return "SaturnChunkProvider";
    }

    public List getPossibleCreatures(EnumCreatureType creature, int i, int x, int z) {
        return this.worldObj.getBiomeGenForCoords(x, z).getSpawnableList(creature);
    }

    public ChunkPosition func_147416_a(World world, String structureName, int i, int x, int z) {
        return null;
    }

    public int getLoadedChunkCount() {
        return 0;
    }

    public void recreateStructures(int p_82695_1_, int p_82695_2_) { }
}
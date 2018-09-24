package projectandromeda.systems.ArterosSystem.arteros_e.dimension;

 

import java.util.List;

import com.google.common.collect.Lists;

import asmodeuscore.core.astronomy.dimension.world.gen.ChunkProviderSpaceLakes;
import asmodeuscore.core.astronomy.dimension.world.gen.MapGenCaves;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeAdaptive;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import projectandromeda.systems.ArterosSystem.arteros_e.world.gen.BiomeDecoratorArteros_E;

public class ChunkProviderArteros_E extends ChunkProviderSpaceLakes {
	
    private List<MapGenBaseMeta> worldGenerators;
    
    private final MapGenCaves caveGenerator = new MapGenCaves(Blocks.STONE, 0, 0, 0);

	@Override
	protected List<MapGenBaseMeta> getWorldGenerators()
	{
		List<MapGenBaseMeta> generators = Lists.newArrayList();
		generators.add(this.caveGenerator);
		return generators;
	}
	
	public ChunkProviderArteros_E(World par1World, long seed, boolean mapFeaturesEnabled)	
	{
		super(par1World, seed, mapFeaturesEnabled);
	}
	
	@Override	
	protected BiomeDecoratorSpace getBiomeGenerator() {	
	    return new BiomeDecoratorArteros_E();	
	}
	//This should be a custom biome for your mod, but I'm opting to go desert instead out of quickness
	//and the fact that biomes are outside the scope of this tutorial
	
	@Override	
	protected Biome[] getBiomesForGeneration() {	
	    return new Biome[]{BiomeAdaptive.biomeDefault};	
	}
	
	@Override
	public int getCraterProbability() {	
	    return 0;	
	}
		
	@Override
	public double getHeightModifier() {	
	    return 5;	
	}
		
	@Override
	public void onChunkProvider(int cX, int cZ, ChunkPrimer primer) {

	}

	@Override
	public void onPopulate(int cX, int cZ) {
		
	}
	    
	@Override
	public double getMountainHeightModifier() {
	    return 15;
	}
	
	@Override
	public int getWaterLevel() {
	    return 80;
	}
	
	@Override	
	public double getSmallFeatureHeightModifier() {	
	    return 15;	
	}
	
	@Override	
	public double getValleyHeightModifier() {	
	    return 15;	
	}
	
	@Override
	protected BlockMetaPair getGrassBlock() {
		return new BlockMetaPair(Blocks.STONE, (byte) 0);
	}
	
	@Override
	protected BlockMetaPair getDirtBlock() {
		return new BlockMetaPair(Blocks.STONE, (byte) 0);
	}
	
	@Override
	protected BlockMetaPair getStoneBlock() {
		return new BlockMetaPair(Blocks.STONE, (byte) 0);
	}
	
	@Override
	protected boolean enableBiomeGenBaseBlock() {
		return false;
	}

	@Override
	public boolean canGenerateWaterBlock() {
		return false;
	}

	@Override
	public boolean canGenerateIceBlock() {
		return false;
	}

	@Override
	protected BlockMetaPair getWaterBlock() {
		return new BlockMetaPair(Blocks.WATER, (byte) 0);
	}

	@Override
	protected GenType getGenType() {
		return GenType.GC;
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {
		
	}

	@Override
	public Chunk generateChunk(int x, int z) {
		return null;
	}

	@Override
	public void populate(int x, int z) {}

	@Override
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		return null;
	}

	
}
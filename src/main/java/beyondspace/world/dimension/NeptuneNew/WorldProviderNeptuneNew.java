package beyondspace.world.dimension.NeptuneNew;

import java.util.Random;

import beyondspace.utils.BSUtilities;
import beyondspace.utils.RegistrationsList;
import beyondspace.utils.world.EmptyBiomeDecorator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.api.dimension.IAdvancedSpace;
import galaxyspace.api.dimension.ILightning;
import galaxyspace.core.world.worldengine.WE_Biome;
import galaxyspace.core.world.worldengine.WE_ChunkProvider;
import galaxyspace.core.world.worldengine.WE_WorldProvider;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IAtmosphericGas;
import micdoodle8.mods.galacticraft.api.world.IExitHeight;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.api.world.ITeleportType;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.planets.asteroids.entities.EntityEntryPod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderNeptuneNew extends WE_WorldProvider implements IGalacticraftWorldProvider, IExitHeight, ISolarLevel, ITeleportType, ILightning, IAdvancedSpace {
	
	public String getDimensionName() {
		return "Neptune";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getCloudHeight() {
		return 300.0F;
	}
	
	@Override
	public float getGravity() {
		return BSUtilities.calculateGravity(11.15F);
	}

	@Override
	public double getMeteorFrequency() {
		return 0;
	}

	@Override
	public double getFuelUsageMultiplier() {
		return 2.5D;
	}

	@Override
	public boolean canSpaceshipTierPass(int tier) {
		return tier >= getCelestialBody().getTierRequirement();
	}

	@Override
	public float getFallDamageModifier() {
		return 2.5F;
	}

	@Override
	public float getSoundVolReductionAmount() {
		return 0.5F;
	}

	@Override
	public float getThermalLevelModifier() {
		return 5.0F;
	}

	@Override
	public float getWindLevel() {
		return 58.0F;
	}

	@Override
	public int AtmosphericPressure() {
		return 4;
	}

	@Override
	public boolean SolarRadiation() {
		return true;
	}

	public double getHorizon() {
		return 32.0D;
	}
	
	public int getAverageGroundLevel() {
		return 16;
	}
	
	@Override
	public CelestialBody getCelestialBody() {
		return SolarSystemBodies.planetNeptune;
	}

	@Override
	public Vector3 getFogColor() {
		return new Vector3((double)4/255, (double)28/255, (double)183/255);
	}

	@Override
	public Vector3 getSkyColor() {
		return new Vector3((double)68/255, (double)92/255, (double)247/255);
	}

	@Override
	public boolean canRainOrSnow() {
		return false;
	}

	@Override
	public boolean hasSunset() {
		return false;
	}

	@Override
	public long getDayLength() {
		return 16064L;
	}

	@Override
	public Class<? extends IChunkProvider> getChunkProviderClass() {
		return WE_ChunkProvider.class;
	}

	@Override
	public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {
		return WorldChunkManagerHell.class;
	}

	@Override
	public double getYCoordinateToTeleport() {
		return 2000.0D;
	}

	@Override
	public double getSolarEnergyMultiplier() {
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getStarBrightness(float par1) {
		return 0F;
	}
	
	@Override
	public boolean useParachute() {
		return false;
	}

	@Override
	public Vector3 getPlayerSpawnLocation(WorldServer server, EntityPlayerMP player) {
		if (player != null) {
			GCPlayerStats stats = GCPlayerStats.get(player);
			return new Vector3(stats.coordsTeleportedFromX, 300.0D, stats.coordsTeleportedFromZ);
		}
		return null;
	}

	@Override
	public Vector3 getEntitySpawnLocation(WorldServer world, Entity entity) {
		return new Vector3(entity.posX, 300.0D, entity.posZ);
	}

	@Override
	public Vector3 getParaChestSpawnLocation(WorldServer world, EntityPlayerMP player, Random rand) {
		return null;
	}

	@Override
	public void onSpaceDimensionChanged(World world, EntityPlayerMP player, boolean ridingAutoRocket) {
		if ((player != null) && (GCPlayerStats.get(player).teleportCooldown <= 0)) {
			if (player.capabilities.isFlying) {
				player.capabilities.isFlying = false;
			}
			
			EntityEntryPod lander = new EntityEntryPod(player);
			if (!world.isRemote) {
				world.spawnEntityInWorld(lander);
			}
			
			GCPlayerStats.get(player).teleportCooldown = 10;
			
			if (player.posY > 224) {
				for (int x = -3; x < 3; x++) {
					for (int z = -3; z < 3; z++) {
						world.setBlock(MathHelper.floor_double(player.posX) + x, 224, MathHelper.floor_double(player.posZ) + z, GCBlocks.basicBlock, 1, 10);
					}}}}}

	@Override
	public void setupAdventureSpawn(EntityPlayerMP player) { }

	@Override
	public double getLightningStormFrequency() {
		return 1024.0D;
	}

	@Override
	public boolean hasBreathableAtmosphere() {
		return false;
	}

	@Override
	public boolean netherPortalsOperational() {
		return false;
	}

	@Override
	public boolean isGasPresent(IAtmosphericGas gas) {
		return (gas == IAtmosphericGas.HYDROGEN || gas == IAtmosphericGas.HELIUM || gas == IAtmosphericGas.METHANE);
	}

	@Override
	public float getSolarSize() {
		return 0.1F;
	}

	@Override
	public int getYPosLightning() {
		return 0;
	}

	@Override
	public void genSettings(WE_ChunkProvider cp) {
		cp.decorateChunkGen_List.clear();		
		cp.createChunkGen_InXZ_List.clear();
		
		cp.createChunkGen_List.add(new WE_NeptuneGenerator());
		WE_Biome.addBiomeToGeneration(cp, new NeptuneBiome());
	}
	
	@Override
	public BiomeDecoratorSpace getDecorator() {
		return new EmptyBiomeDecorator();
	}
	
	@Override
	public ClassBody getClassBody()
	{
		return ClassBody.GASGIANT;
	}

}
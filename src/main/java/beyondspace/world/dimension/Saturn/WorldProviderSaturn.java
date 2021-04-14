package beyondspace.world.dimension.Saturn;

import java.util.Random;

import beyondspace.utils.BSConfig;
import beyondspace.utils.BSUtilities;
import beyondspace.utils.RegistrationsList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.api.dimension.ILightning;
import galaxyspace.core.world.gen.WorldProviderAdvancedSpace;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IAtmosphericGas;
import micdoodle8.mods.galacticraft.api.world.IExitHeight;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.api.world.ITeleportType;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.planets.asteroids.entities.EntityEntryPod;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderSaturn extends WorldProviderAdvancedSpace implements IGalacticraftWorldProvider, IExitHeight, ISolarLevel, ITeleportType, ILightning {

	public void registerWorldChunkManager() {
		this.dimensionId = BSConfig.saturn;
		this.worldChunkMgr = new WorldChunkManagerSaturn();
	}
	
	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderSaturn(this.worldObj, this.worldObj.getSeed(), "2;" + Block.getIdFromBlock(Blocks.bedrock) + ",20x" + Block.getIdFromBlock(RegistrationsList.silicate) + ",35x" + Block.getIdFromBlock(Blocks.stone) + ",20x" + Block.getIdFromBlock(Blocks.packed_ice) + ",59x" + Block.getIdFromBlock(RegistrationsList.HMetal) + ",40x" + Block.getIdFromBlock(RegistrationsList.HHeLiquid) + ";1");
	}
	
	public String getDimensionName() {
		return "Saturn";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getCloudHeight() {
		return 256.0F;
	}
	
	@Override
	public float getGravity() {
		return BSUtilities.calculateGravity(10.44F);
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
		return 50.0F;
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
		return SolarSystemBodies.planetSaturn;
	}

	@Override
	public Vector3 getFogColor() {
		return new Vector3((double)191/255, (double)115/255, (double)38/255);
	}

	@Override
	public Vector3 getSkyColor() {
		return new Vector3((double)255/255, (double)178/255, (double)102/255);
	}
	
	@Override
	public boolean canRainOrSnow() {
		return false;
	}

	@Override
	public boolean hasSunset() {
		return true;
	}

	@Override
	public long getDayLength() {
		return 10330L;
	}

	@Override
	public Class<? extends IChunkProvider> getChunkProviderClass() {
		return ChunkProviderSaturn.class;
	}

	@Override
	public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {
		return WorldChunkManagerSaturn.class;
	}

	@Override
	public double getYCoordinateToTeleport() {
		return 2500.0D;
	}

	@Override
	public double getSolarEnergyMultiplier() {
		return 0;
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
			
			// Landing Platform Preset
			if (player.posY > 224) {
				for (int x = -3; x < 3; x++) {
					for (int z = -3; z < 3; z++) {
						world.setBlock(MathHelper.floor_double(player.posX) + x, 224, MathHelper.floor_double(player.posZ) + z, GCBlocks.basicBlock, 1, 10);
					}
				}
				
				//world.setBlock(MathHelper.floor_double(player.posX) + 1, 225, MathHelper.floor_double(player.posZ) + 1, Blocks.beacon);
				//world.setBlock(MathHelper.floor_double(player.posX) + 1, 225, MathHelper.floor_double(player.posZ) - 2, Blocks.beacon);
				//world.setBlock(MathHelper.floor_double(player.posX) - 2, 225, MathHelper.floor_double(player.posZ) + 1, Blocks.beacon);
				//world.setBlock(MathHelper.floor_double(player.posX) - 2, 225, MathHelper.floor_double(player.posZ) - 2, Blocks.beacon);
			}
		}
	}

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
		return (gas == IAtmosphericGas.HYDROGEN);
	}

	@Override
	public float getSolarSize() {
		return 0.1F;
	}

	@Override
	public int getYPosLightning() {
		// TODO Auto-generated method stub
		return 0;
	}

}
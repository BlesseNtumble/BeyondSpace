package beyondspace.world.dimension.Space;

import java.util.Random;

import beyondspace.utils.BSUtilities;
import beyondspace.utils.RegistrationsList;
import beyondspace.utils.space.GASpaceUtilities;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.api.dimension.IAdvancedSpace;
import galaxyspace.core.world.gen.WorldProviderAdvancedSpace;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IAtmosphericGas;
import micdoodle8.mods.galacticraft.api.world.IExitHeight;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.api.world.ITeleportType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderSpace extends WorldProviderAdvancedSpace implements IGalacticraftWorldProvider, IExitHeight, ISolarLevel, ITeleportType, IAdvancedSpace {

	@Override
	public int AtmosphericPressure() {
		return 0;
	}

	@Override
	public boolean SolarRadiation() {
		return true;
	}

	@Override
	public boolean useParachute() {
		return false;
	}

	@Override
	public Vector3 getPlayerSpawnLocation(WorldServer world, EntityPlayerMP player) {
		if (player != null) {
			Vec3 pos = GASpaceUtilities.getBodyPositionFromDimID(player.dimension);
			return new Vector3(pos.xCoord, pos.yCoord, pos.zCoord);
		}
		return null;
	}

	@Override
	public Vector3 getEntitySpawnLocation(WorldServer world, Entity entity) {
		Vec3 pos = GASpaceUtilities.getBodyPositionFromDimID(entity.dimension);
		return new Vector3(pos.xCoord, pos.yCoord, pos.zCoord);
	}

	@Override
	public Vector3 getParaChestSpawnLocation(WorldServer world, EntityPlayerMP player, Random rand) {
		return null;
	}

	@Override
	public void onSpaceDimensionChanged(World newWorld, EntityPlayerMP player, boolean ridingAutoRocket) { }

	@Override
	public void setupAdventureSpawn(EntityPlayerMP player) { }

	@Override
	public double getSolarEnergyMultiplier() {
		return 0;
	}

	@Override
	public double getYCoordinateToTeleport() {
		return 0;
	}

	@Override
	public float getGravity() {
		return BSUtilities.calculateGravity(0);
	}

	@Override
	public double getMeteorFrequency() {
		return 0;
	}

	@Override
	public double getFuelUsageMultiplier() {
		return 0.01D;
	}

	@Override
	public boolean canSpaceshipTierPass(int tier) {
		return true;
	}

	@Override
	public float getFallDamageModifier() {
		return 0;
	}

	@Override
	public float getSoundVolReductionAmount() {
		return 1000;
	}

	@Override
	public float getThermalLevelModifier() {
		return -16; 
	}

	@Override
	public float getWindLevel() {
		return 0;
	}

	@Override
	public CelestialBody getCelestialBody() {
		return RegistrationsList.enderStar;
	}

	@Override
	public Vector3 getFogColor() {
		return new Vector3(0, 0, 0);
	}

	@Override
	public Vector3 getSkyColor() {
		return new Vector3(0, 0, 0);
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
		return 0;
	}

	@Override
	public Class<? extends IChunkProvider> getChunkProviderClass() {
		return ChunkProviderSpace.class;
	}

	@Override
	public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {
		return WorldChunkManagerSpace.class;
	}
	
	@Override
    public boolean isGasPresent(IAtmosphericGas gas) {
        return false;
    }

	@Override
    public boolean hasAtmosphere() {
        return false;
    }
	
	@Override
    public boolean hasBreathableAtmosphere() {
        return false;
    }
	
	@Override
    public String getSaveFolder() {
        return "Space";
    }
	
	@Override
    public boolean canBlockFreeze(int x, int y, int z, boolean byWater) {
        return false;
    }

    @Override
    public boolean canDoLightning(Chunk chunk) {
        return false;
    }

    @Override
    public boolean canDoRainSnowIce(Chunk chunk) {
        return false;
    }

    @Override
    public float[] calcSunriseSunsetColors(float var1, float var2) {
        return null;
    }

    @Override
    public float calculateCelestialAngle(long par1, float par3) {
        return 0;
    }
    
    @Override
    public boolean isSkyColored() {
        return false;
    }
    
    @Override
	public boolean canRespawnHere() {
		return true;
	}

    @Override
    public boolean netherPortalsOperational() {
        return true;
    }
    
    @Override
    public float getSolarSize() {
        return 0;
    }
    
    @Override
    public boolean canCoordinateBeSpawn(int x, int z) {
        return GASpaceUtilities.getStarAt(x, z) == null ;
    }
    
    @Override
    public int getMoonPhase(long time) {
        return 0;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public float getCloudHeight() {
        return Integer.MIN_VALUE;
    }
    
    @Override
    public int getAverageGroundLevel() {
        return Integer.MIN_VALUE;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean getWorldHasVoidParticles() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getVoidFogYFactor() {
        return Integer.MIN_VALUE;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z){
        return false;
    }
    
    @Override
    public String getDimensionName() {
		return "Space";
    }
    
    @Override
    public double getMovementFactor() {
        return 1000.0;
    }
    
    @Override
    public ChunkCoordinates getRandomizedSpawnPoint() {
        return GASpaceUtilities.getEarthPosition();
    }
    
    @Override
    public boolean isDaytime() {
        return true;
    }
    
    @Override
    public float getSunBrightnessFactor(float par1) {
        return 1.0F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vec3 drawClouds(float partialTicks) {
        return Vec3.createVectorHelper(0, 0, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getSunBrightness(float par1) {
        return 1.0F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1) {
        return 1.0F;
    }
    
    @Override
    public void calculateInitialWeather() {
        return;
    }

    @Override
    public void updateWeather() {
        return;
    }

    @Override
    public boolean canSnowAt(int x, int y, int z, boolean checkLight) {
        return false;
    }
    
    @Override
    public ChunkCoordinates getSpawnPoint() {
        return GASpaceUtilities.getEarthPosition();
    }
    
    @Override
    public boolean canMineBlock(EntityPlayer player, int x, int y, int z) {
    	// TODO wtf
        return worldObj.canMineBlockBody(player, x, y, z);
    }

    @Override
    public boolean isBlockHighHumidity(int x, int y, int z) {
        return false;
    }
    
    @Override
    public double getHorizon() {
        return Double.MAX_VALUE;
    }
}

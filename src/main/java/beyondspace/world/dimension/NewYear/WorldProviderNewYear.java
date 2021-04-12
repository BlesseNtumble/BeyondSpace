package beyondspace.world.dimension.NewYear;

import java.util.Random;

import beyondspace.utils.BSConfig;
import beyondspace.utils.RegistrationsList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.api.dimension.IAdvancedSpace;
import galaxyspace.core.world.gen.WorldProviderAdvancedSpace;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IExitHeight;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.api.world.ITeleportType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

public class WorldProviderNewYear extends WorldProviderAdvancedSpace implements IGalacticraftWorldProvider, IExitHeight, ISolarLevel, ITeleportType, IAdvancedSpace {

	public void registerWorldChunkManager() {
		this.dimensionId = BSConfig.newYear;
		this.worldChunkMgr = new WorldChunkManagerNewYear();
    }
	
	@Override
    public float calculateCelestialAngle(long par1, float par3) {
        return 0.5F;
    }

	@Override
	@SideOnly(Side.CLIENT)
    public boolean getWorldHasVoidParticles() {
        return false;
    }

	@Override
	@SideOnly(Side.CLIENT)
    public double getVoidFogYFactor() {
        return 0;
    }

	@Override
	public boolean doesXZShowFog(int x, int z) {
        return true;
    }
	
	@Override
	public String getDimensionName() {
		return "NewYear";
	}
	
	@Override
	public double getMovementFactor() {
        return 0;
    }
	
	@Override
    public boolean isDaytime() {
		return false;
	}
	
	@Override
	public float getSunBrightnessFactor(float par1) {
        return 1.0F;
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
	@SideOnly(Side.CLIENT)
    public float getCloudHeight() {
        return 128.0F;
    }
	
	@SideOnly(Side.CLIENT)
	public IRenderHandler getCloudRenderer() {
		return new CloudRendererNewYear();
	}
	
	@Override
	public boolean canSnowAt(int x, int y, int z, boolean checkLight) {
        return true;
    }

	@Override
	public boolean canDoRainSnowIce(Chunk chunk) {
        return true;
    }
	
	@Override
	public boolean hasBreathableAtmosphere() {
		return true;
	}
	
	@Override
	public boolean useParachute() {
		return true;
	}

	@Override
	public Vector3 getPlayerSpawnLocation(WorldServer world, EntityPlayerMP player) {
		if (player != null) {
			return new Vector3(0, 217, 50);
		}
		return null;
	}

	@Override
	public Vector3 getEntitySpawnLocation(WorldServer world, Entity entity) {
		return new Vector3(entity.posX, 217, entity.posZ);
	}

	@Override
	public Vector3 getParaChestSpawnLocation(WorldServer world, EntityPlayerMP player, Random rand) {
		if (player != null) {
			return new Vector3(0, 217, 45);
		}
		return null;
	}

	@Override
	public void onSpaceDimensionChanged(World newWorld, EntityPlayerMP player, boolean ridingAutoRocket) {
		return;
	}

	@Override
	public void setupAdventureSpawn(EntityPlayerMP player) {
		return;
	}

	@Override
	public double getSolarEnergyMultiplier() {
		return 1;
	}

	@Override
	public double getYCoordinateToTeleport() {
		return 217.0D;
	}

	@Override
	public float getGravity() {
		return 0F;
	}

	@Override
	public double getMeteorFrequency() {
		return 0;
	}

	@Override
	public double getFuelUsageMultiplier() {
		return 1;
	}

	@Override
	public boolean canSpaceshipTierPass(int tier) {
		return tier > 0;
	}

	@Override
	public float getFallDamageModifier() {
		return 0;
	}

	@Override
	public float getSoundVolReductionAmount() {
		return 0;
	}

	@Override
	public float getThermalLevelModifier() {
		return 0;
	}

	@Override
	public float getWindLevel() {
		return 1.0F;
	}

	@Override
	public CelestialBody getCelestialBody() {
		return RegistrationsList.holiday;
	}

	@Override
	public int AtmosphericPressure() {
		return 2;
	}

	@Override
	public boolean SolarRadiation() {
		return false;
	}

	@Override
	public Vector3 getFogColor() {
		return new Vector3(1.0, 1.0, 1.0);
	}

	@Override
	public Vector3 getSkyColor() {
		return new Vector3(0.0, 0.0, 0.0);
	}

	@Override
	public boolean canRainOrSnow() {
		return true;
	}

	@Override
	public boolean hasSunset() {
		return false;
	}

	@Override
	public long getDayLength() {
		return 1;
	}

	@Override
	public Class<? extends IChunkProvider> getChunkProviderClass() {
		return ChunkProviderNewYear.class;
	}

	@Override
	public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {
		return WorldChunkManagerNewYear.class;
	}
}
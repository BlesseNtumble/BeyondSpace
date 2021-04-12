package beyondspace.world.dimension.Thanatos;

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

public class WorldProviderThanatos extends WorldProviderAdvancedSpace implements IGalacticraftWorldProvider, IExitHeight, ISolarLevel, ITeleportType, IAdvancedSpace {

	public void registerWorldChunkManager() {
		this.dimensionId = BSConfig.thanatos;
		this.worldChunkMgr = new WorldChunkManagerThanatos();
    }
	
	@Override
    public float calculateCelestialAngle(long par1, float par3) {
        return 0.25F;
    }

	@Override
	@SideOnly(Side.CLIENT)
    public boolean getWorldHasVoidParticles() {
        return true;
    }

	@Override
	@SideOnly(Side.CLIENT)
    public double getVoidFogYFactor() {
        return 1.0D;
    }

	@Override
	public boolean doesXZShowFog(int x, int z) {
        return true;
    }
	
	@Override
	public String getDimensionName() {
		return "Thanatos";
	}
	
	@Override
	public double getMovementFactor() {
        return 0;
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
        return 666.0F;
    }
	
	@SideOnly(Side.CLIENT)
	public IRenderHandler getCloudRenderer() {
		return null;
	}
	
	@Override
	public boolean canSnowAt(int x, int y, int z, boolean checkLight) {
        return false;
    }

	@Override
	public boolean canDoRainSnowIce(Chunk chunk) {
        return false;
    }
	
	@Override
	public boolean hasBreathableAtmosphere() {
		return false;
	}
	
	@Override
	public boolean useParachute() {
		return false;
	}

	@Override
	public Vector3 getPlayerSpawnLocation(WorldServer world, EntityPlayerMP player) {
		if (player != null) {
			return new Vector3(player.posX, 666, player.posZ);
		}
		return null;
	}

	@Override
	public Vector3 getEntitySpawnLocation(WorldServer world, Entity entity) {
		return new Vector3(entity.posX, 666, entity.posZ);
	}

	@Override
	public Vector3 getParaChestSpawnLocation(WorldServer world, EntityPlayerMP player, Random rand) {
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
		return -100500;
	}

	@Override
	public double getYCoordinateToTeleport() {
		return 666;
	}

	@Override
	public float getGravity() {
		return 0.086F;
	}

	@Override
	public double getMeteorFrequency() {
		return 0;
	}

	@Override
	public double getFuelUsageMultiplier() {
		return -0.1D;
	}

	@Override
	public boolean canSpaceshipTierPass(int tier) {
		return tier > 7;
	}

	@Override
	public float getFallDamageModifier() {
		return -1;
	}

	@Override
	public float getSoundVolReductionAmount() {
		return Float.MAX_VALUE;
	}

	@Override
	public float getThermalLevelModifier() {
		return -10;
	}

	@Override
	public float getWindLevel() {
		return 0;
	}

	@Override
	public CelestialBody getCelestialBody() {
		return RegistrationsList.thanatos;
	}

	@Override
	public int AtmosphericPressure() {
		return 10;
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
		return false;
	}

	@Override
	public boolean hasSunset() {
		return false;
	}

	@Override
	public long getDayLength() {
		return Long.MAX_VALUE;
	}

	@Override
	public Class<? extends IChunkProvider> getChunkProviderClass() {
		return ChunkProviderThanatos.class;
	}

	@Override
	public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {
		return WorldChunkManagerThanatos.class;
	}
}
package beyondspace.world.dimension.TheEnd;

import java.util.Random;

import beyondspace.utils.RegistrationsList;
import cpw.mods.fml.relauncher.ReflectionHelper;
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
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderEnd;

public class WorldProviderTheEnd extends WorldProviderAdvancedSpace implements IGalacticraftWorldProvider, IExitHeight, ISolarLevel, ITeleportType, IAdvancedSpace {

	public String getDimensionName() {
        return "The End";
    }

	@Override
	public boolean useParachute() {
		return false;
	}

	@Override
	public Vector3 getPlayerSpawnLocation(WorldServer world, EntityPlayerMP player) {
		if (player != null) {
			WorldServer worldserver1 = MinecraftServer.getServer().worldServerForDimension(1);
			ChunkCoordinates chunkcoordinates = worldserver1.getEntrancePortalLocation();
			return new Vector3(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ);
		}
		return null;
	}

	@Override
	public Vector3 getEntitySpawnLocation(WorldServer world, Entity entity) {
		WorldServer worldserver1 = MinecraftServer.getServer().worldServerForDimension(1);
		ChunkCoordinates chunkcoordinates = worldserver1.getEntrancePortalLocation();
		return new Vector3(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ);
	}

	@Override
	public Vector3 getParaChestSpawnLocation(WorldServer world, EntityPlayerMP player, Random rand) {
		WorldServer worldserver1 = MinecraftServer.getServer().worldServerForDimension(1);
		ChunkCoordinates chunkcoordinates = worldserver1.getEntrancePortalLocation();
		return new Vector3(chunkcoordinates.posX + (new Random()).nextInt(4) - 2, chunkcoordinates.posY, chunkcoordinates.posZ + (new Random()).nextInt(4) - 2);
	}

	@Override
	public void onSpaceDimensionChanged(World world, EntityPlayerMP player, boolean ridingAutoRocket) {
		if (!world.isRemote) {
			for (int x = -2; x < 3; x++) {
				for (int z = -2; z < 3; z++) {
					for(int y = -1; y < 3; y++) {
						if (y == -1) world.setBlock(MathHelper.floor_double(player.posX + x), MathHelper.floor_double(player.posY - 1), MathHelper.floor_double(player.posZ + z), Blocks.obsidian);
						else world.setBlock(MathHelper.floor_double(player.posX + x), MathHelper.floor_double(player.posY + y), MathHelper.floor_double(player.posZ + z), Blocks.air);
					}
				}
			}
		}
	}

	@Override
	public void setupAdventureSpawn(EntityPlayerMP player) {}


	@Override
	public double getMeteorFrequency() {
		return 0;
	}

	@Override
	public double getFuelUsageMultiplier() {
		return 0;
	}

	@Override
	public boolean canSpaceshipTierPass(int tier) {
		return tier >= 1;
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
	public boolean netherPortalsOperational() {
		return false;
	}

	@Override
	public boolean isGasPresent(IAtmosphericGas gas) {
		return gas == IAtmosphericGas.ARGON || gas == IAtmosphericGas.WATER;
	}

	@Override
	public float getSolarSize() {
		return 0;
	}

	@Override
	public double getSolarEnergyMultiplier() {
		return 0;
	}

	@Override
	public double getYCoordinateToTeleport() {
		return 64;
	}

	@Override
	public Vector3 getFogColor() {
		Timer timer = ReflectionHelper.getPrivateValue(Minecraft.class,  Minecraft.getMinecraft(), "timer");
		float f1 = 0.0F;
		float f2 = timer.renderPartialTicks;
		return new Vector3(this.getFogColor(f1, f2).xCoord, this.getFogColor(f1, f2).yCoord, this.getFogColor(f1, f2).zCoord);
	}

	@Override
	public Vector3 getSkyColor() {
		return new Vector3(0, 0, 0);
	}

	@SideOnly(Side.CLIENT)
	public Vec3 getFogColor(float p_76562_1_, float p_76562_2_) {
		int i = 10518688;
		float f2 = MathHelper.cos(p_76562_1_ * (float) Math.PI * 2.0F) * 2.0F + 0.5F;

		if (f2 < 0.0F) {
			f2 = 0.0F;
		}

		if (f2 > 1.0F) {
			f2 = 1.0F;
		}

		float f3 = (float) (i >> 16 & 255) / 255.0F;
		float f4 = (float) (i >> 8 & 255) / 255.0F;
		float f5 = (float) (i & 255) / 255.0F;
		f3 *= f2 * 0.0F + 0.15F;
		f4 *= f2 * 0.0F + 0.15F;
		f5 *= f2 * 0.0F + 0.15F;
		return Vec3.createVectorHelper((double) f3, (double) f4, (double) f5);
	}

	@SideOnly(Side.CLIENT)
	public boolean isSkyColored() {
		return false;
	}
	
	@Override
	public boolean canRainOrSnow() {
		return false;
	}

	@SideOnly(Side.CLIENT)
    public float getCloudHeight() {
        return 8.0F;
    }
	
	@Override
	public boolean hasSunset() {
		return false;
	}

	@SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float f, float f1) {
        return null;
    }
	
	@Override
	public float calculateCelestialAngle(long l, float f) {
        return 0.0F;
    }

	@Override
	public Class<? extends IChunkProvider> getChunkProviderClass() {
		return ChunkProviderEnd.class;
	}

	@Override
	public IChunkProvider createChunkGenerator() {
        return new ChunkProviderEnd(this.worldObj, this.worldObj.getSeed());
    }
	
	@Override
	public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {
		return WorldChunkManagerHell.class;
	}
	
	@Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.sky, 0.0F);
		this.dimensionId = 1;
		this.hasNoSky = true;
	}
	
	@Override
	public boolean canRespawnHere() {
        return false;
    }
	
	@Override
	public boolean isSurfaceWorld() {
        return false;
    }
	
	@Override
	public boolean canCoordinateBeSpawn(int x, int z) { 
        return this.worldObj.getTopBlock(x, z).getMaterial().blocksMovement();
    }
	
	@Override
	public ChunkCoordinates getEntrancePortalLocation() {
        return new ChunkCoordinates(100, 50, 0);
    }
	
	@Override
	public int getAverageGroundLevel() {
        return 50;
    }
	
	@SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z) {
        return true;
    }
	
    @Override
    public CelestialBody getCelestialBody() {
        return RegistrationsList.end;
    }
}

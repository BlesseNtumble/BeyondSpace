package projectandromeda.systems.ArterosSystem.arteros_e.dimension;

 


import java.util.List;

import asmodeuscore.core.astronomy.dimension.IProviderFreeze;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_ChunkProvider;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_WorldProvider;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_CaveGen;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_RavineGen;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_TerrainGenerator;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import projectandromeda.core.registers.blocks.PABlocks;
import projectandromeda.core.utils.PADimensions;
import projectandromeda.systems.ArterosSystem.ArterosBodies;
import projectandromeda.systems.ArterosSystem.arteros_e.dimension.sky.SkyProviderArteros_E;
import projectandromeda.systems.ArterosSystem.arteros_e.world.gen.BiomeProviderArteros_E;
import projectandromeda.systems.ArterosSystem.arteros_e.world.gen.we.Arteros_E_Forest;
import projectandromeda.systems.ArterosSystem.arteros_e.world.gen.we.Arteros_E_Mountain;
import projectandromeda.systems.ArterosSystem.arteros_e.world.gen.we.Arteros_E_River;

 
public class WorldProviderArteros_E extends WE_WorldProvider implements IProviderFreeze {
	
    @Override
    public double getHorizon() {
        return 44.0D;
    }

    @Override
    public float getFallDamageModifier() {
        return 0.16F;
    }

    @Override
    public double getFuelUsageMultiplier() {
        return 0.8;
    }

    @Override
    public double getMeteorFrequency() {
        return 0.0;
    }

    @Override
    public float getSoundVolReductionAmount() {
        return Float.MIN_VALUE;
    }

    @Override
    public boolean canRainOrSnow() {
        return true;
    }

    @Override
    public CelestialBody getCelestialBody() {
        return ArterosBodies.arteros_E;
    }

    @Override
    public Class<? extends IChunkGenerator> getChunkProviderClass() {
        return WE_ChunkProvider.class;

    }
    
    @Override 
    public Class<? extends BiomeProvider> getBiomeProviderClass() { 
    	return BiomeProviderArteros_E.class; 
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Vector3 getFogColor() {
    	float f = 1.0F - this.getStarBrightness(1.0F);
        return new Vector3(187 / 255F * f, 108 / 255F * f, 54 / 255F * f);
    }

    @Override
    public Vector3 getSkyColor() {
    	float f = 1.0F - this.getStarBrightness(1.0F);
        return new Vector3(66 / 255.0F * f, 133 / 255.0F * f, 180 / 255.0F * f);
    }
     
	@Override
	public boolean isSkyColored() {
		return true;
	}
 
	@Override
	public boolean hasSunset() {
		return true;
	}

    @Override
    public boolean shouldForceRespawn() {
        return !ConfigManagerCore.forceOverworldRespawn;
    }    
    
    @Override
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1)
    {
        final float var2 = this.world.getCelestialAngle(par1);
        float var3 = 1.0F - (MathHelper.cos(var2 * (float) Math.PI * 2.0F) * 2.0F + 0.25F);

        if (var3 < 0.0F)
        {
            var3 = 0.0F;
        }

        if (var3 > 1.0F)
        {
            var3 = 1.0F;
        }

        return var3 * var3 * 0.5F + 0.3F;    	
    }
   
    @Override
    public IRenderHandler getCloudRenderer(){
        return new CloudRenderer();
    }

	@SideOnly(Side.CLIENT)
    public IRenderHandler getSkyRenderer()
    {
    	if (super.getSkyRenderer() == null)
		{
			this.setSkyRenderer(new SkyProviderArteros_E());
		}

		return super.getSkyRenderer();
    }

	@Override
	public int getDungeonSpacing() {
		return 0;
	}

	@Override
	public ResourceLocation getDungeonChestType() {
		return null;
	}

	@Override
	public List<Block> getSurfaceBlocks() {
		return null;
	}

	@Override
	public DimensionType getDimensionType() {
	
		return PADimensions.Arteros_E;
	}

	@Override
	public void genSettings(WE_ChunkProvider cp) {
		cp.createChunkGen_List .clear(); 
		cp.createChunkGen_InXZ_List .clear(); 
		cp.createChunkGen_InXYZ_List.clear(); 
		cp.decorateChunkGen_List .clear(); 
		
		WE_Biome.setBiomeMap(cp, 1.2D, 6, 1200.0D, 0.375D);	
		
		WE_TerrainGenerator terrainGenerator = new WE_TerrainGenerator(); 
		terrainGenerator.worldStoneBlock = PABlocks.ARTEROS_E_BLOCKS; 
		terrainGenerator.worldStoneBlockMeta = 2;
		terrainGenerator.worldSeaGen = true;
		terrainGenerator.worldSeaGenBlock = Blocks.WATER;
		terrainGenerator.worldSeaGenMaxY = 64;
		cp.createChunkGen_List.add(terrainGenerator);
		
		//-// 
		WE_CaveGen cg = new WE_CaveGen(); 
		cg.replaceBlocksList .clear(); 
		cg.replaceBlocksMetaList.clear(); 
		cg.addReplacingBlock(terrainGenerator.worldStoneBlock, (byte)terrainGenerator.worldStoneBlockMeta); 
		//cg.lavaBlock = CW_Main.bfLava2; 
		cp.createChunkGen_List.add(cg); 
		//-// 
		 
		WE_RavineGen rg = new WE_RavineGen();
		rg.replaceBlocksList    .clear();
		rg.replaceBlocksMetaList.clear();
		rg.addReplacingBlock(PABlocks.ARTEROS_E_BLOCKS, (byte)2);
		rg.lavaBlock = Blocks.LAVA;
		cp.createChunkGen_List.add(rg);
		
		WE_Biome.addBiomeToGeneration(cp, new Arteros_E_Forest()); 
		WE_Biome.addBiomeToGeneration(cp, new Arteros_E_Mountain());
		WE_Biome.addBiomeToGeneration(cp, new Arteros_E_River()); 
	}

}
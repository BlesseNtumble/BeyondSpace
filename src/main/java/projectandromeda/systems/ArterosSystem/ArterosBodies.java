package projectandromeda.systems.ArterosSystem;

import asmodeuscore.core.astronomy.BodiesHelper;
import asmodeuscore.core.astronomy.BodiesHelper.BodiesData;
import asmodeuscore.core.astronomy.BodiesHelper.Galaxies;
import asmodeuscore.core.astronomy.dimension.world.gen.ACBiome;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.galaxies.Star;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import projectandromeda.ProjectAndromeda;
import projectandromeda.api.IBodiesHandler;
import projectandromeda.core.configs.PAConfigDimensions;
import projectandromeda.core.utils.PADimensions;
import projectandromeda.systems.ArterosSystem.arteros_e.dimension.TeleportTypeArteros_E;
import projectandromeda.systems.ArterosSystem.arteros_e.dimension.WorldProviderArteros_E;

public class ArterosBodies implements IBodiesHandler {
	
	public static SolarSystem arterosSystem;
	public static Star arteros_A;
	public static Planet arteros_B, arteros_C, arteros_D, arteros_E;
	
	public void preInit(FMLPreInitializationEvent event)
	{
		arterosSystem = new SolarSystem("arteros", Galaxies.ANDROMEDA.getName()).setMapPosition(new Vector3(1.5F, 0.0F, 0.0F));
		arteros_A = (Star) new Star("arteros_a").setParentSolarSystem(arterosSystem).setTierRequired(-1).setRelativeSize(1.3F);
		arteros_A.setBodyIcon(new ResourceLocation(ProjectAndromeda.ASSET_PREFIX, "textures/gui/celestialbodies/arteros/arteros_a.png"));
		arterosSystem.setMainStar(arteros_A);
		
		arteros_B = (Planet) BodiesHelper.registerPlanet(arterosSystem, "arteros_b", ProjectAndromeda.ASSET_PREFIX, null, -1, -1, 1.45F, 1.0F, 0.5F, 0.24F);
		arteros_C = (Planet) BodiesHelper.registerPlanet(arterosSystem, "arteros_c", ProjectAndromeda.ASSET_PREFIX, null, -1, -1, 0.45F, 1.0F, 0.75F, 0.82F);
		arteros_D = (Planet) BodiesHelper.registerPlanet(arterosSystem, "arteros_d", ProjectAndromeda.ASSET_PREFIX, null, -1, -1, 0.85F, 1.0F, 1.0F, 1.22F);
		arteros_E = (Planet) BodiesHelper.registerPlanet(arterosSystem, "arteros_e", ProjectAndromeda.ASSET_PREFIX, WorldProviderArteros_E.class, PAConfigDimensions.dimensionIDArteros_E, 1, 0.15F, 1.0F, 1.25F, 2.82F, ACBiome.ACSpace).setRingColorRGB(0.0F, 1.0F, 0.0F);
		
	}
	
	public void init(FMLInitializationEvent event)
	{
		registrycelestial();
    	registryteleport();
    	registerRecipesWorkBench();
    	registerDungeonLoot();
	}
	
	public void postInit(FMLPostInitializationEvent event)
	{
		PADimensions.Arteros_E = WorldUtil.getDimensionTypeById(PAConfigDimensions.dimensionIDArteros_E);
	}
	
	private static void registerDungeonLoot()
	{
		
	}
	
	private static void registrycelestial()
	{
		GalaxyRegistry.registerSolarSystem(arterosSystem);
		
		//BodiesHelper.registerBody(arteros_B, BodiesHelper.calculateGravity(10.0F), 0, 6, 0, 94000, false, true, true);
		//BodiesHelper.registerBody(arteros_C, BodiesHelper.calculateGravity(10.0F), 0, 2, 0, 48000, false, true, true);
		//BodiesHelper.registerBody(arteros_D, BodiesHelper.calculateGravity(10.0F), 84, 5, 3, 96000, false, false, true);
		BodiesData data = new BodiesData(null, BodiesHelper.calculateGravity(4.0F), 1, 0.5F, 1, 26000, true, false);
		BodiesHelper.registerBody(arteros_E, data, true);

	}
	
	private static void registryteleport()
	{
		GalacticraftRegistry.registerTeleportType(WorldProviderArteros_E.class, new TeleportTypeArteros_E());
	}
	
	private static void registerRecipesWorkBench()
    {
		
    }
}

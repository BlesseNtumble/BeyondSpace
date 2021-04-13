package beyondspace.utils;
// DO NOT ever press ctrl+shift+o!

import static beyondspace.asjlib.ASJUtilities.getBlockName;
import static beyondspace.asjlib.ASJUtilities.getItemName;
import static beyondspace.asjlib.ASJUtilities.registerEntity;
import static beyondspace.asjlib.ASJUtilities.registerEntityEgg;
import static cpw.mods.fml.common.registry.GameRegistry.addRecipe;
import static cpw.mods.fml.common.registry.GameRegistry.addShapelessRecipe;
import static cpw.mods.fml.common.registry.GameRegistry.registerBlock;
import static cpw.mods.fml.common.registry.GameRegistry.registerItem;
import static cpw.mods.fml.common.registry.GameRegistry.registerTileEntity;
import static net.minecraft.block.Block.soundTypeMetal;
import static net.minecraft.block.Block.soundTypeSnow;
import static net.minecraft.block.Block.soundTypeStone;

/** Regular Imports */
import java.util.Calendar;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import beyondspace.Switches;
import beyondspace.asjlib.BlockPattern;
import beyondspace.asjlib.ItemPattern;
import beyondspace.blocks.AdvancedRefinery;
import beyondspace.blocks.DiamondLiquid;
import beyondspace.blocks.GasGenerator;
import beyondspace.blocks.H2ONH3CH4Ice;
import beyondspace.blocks.H2ONH3CH4Liquid;
import beyondspace.blocks.HCloud;
import beyondspace.blocks.HHeLiquid;
import beyondspace.blocks.HoloMap;
import beyondspace.blocks.LightningRodBase;
import beyondspace.blocks.LightningRodMid;
import beyondspace.blocks.LightningRodTop;
import beyondspace.blocks.SulfurTorch;
import beyondspace.blocks.UltimateFurnace;
import beyondspace.blocks.UltimateOxygenModule;
import beyondspace.blocks.tileentity.AdvancedRefineryTileEntity;
import beyondspace.blocks.tileentity.GasGeneratorTileEntity;
import beyondspace.blocks.tileentity.HoloMapTileEntity;
import beyondspace.blocks.tileentity.LightningRodBaseTileEntity;
import beyondspace.blocks.tileentity.LightningRodMidTileEntity;
import beyondspace.blocks.tileentity.LightningRodTopTileEntity;
import beyondspace.blocks.tileentity.UltimateFurnaceTileEntity;
import beyondspace.blocks.tileentity.UltimateOxygenModuleTileEntity;
import beyondspace.entity.FloaterEntity;
import beyondspace.entity.IonPlasmaBurstEntity;
import beyondspace.entity.RedLightningEntity;
import beyondspace.items.AdvancedOxygenTank;
import beyondspace.items.AmmoBase;
import beyondspace.items.ArmorUpgrade;
import beyondspace.items.DehydratedFood;
import beyondspace.items.FlameThrower;
import beyondspace.items.FloaterDrop;
import beyondspace.items.HandRocket;
import beyondspace.items.HighPressureResistantModularArmor;
import beyondspace.items.IonPlasmaRifle;
import beyondspace.items.PlasmaHammer;
import beyondspace.items.PlasmaOmnitool;
import beyondspace.items.PortableBattery;
import beyondspace.items.TitaniumBattery;
import beyondspace.items.modules.Kinetic;
import beyondspace.items.modules.Protection;
import beyondspace.items.modules.SolarPanel;
import beyondspace.world.dimension.HalloweenNew.WorldProviderHalloweenNew;
import beyondspace.world.dimension.Jupiter.WorldProviderJupiter;
import beyondspace.world.dimension.JupiterNew.WorldProviderJupiterNew;
import beyondspace.world.dimension.NeptuneNew.WorldProviderNeptuneNew;
import beyondspace.world.dimension.NewYear.WorldProviderNewYear;
import beyondspace.world.dimension.SaturnNew.WorldProviderSaturnNew;
import beyondspace.world.dimension.SaturnRings.TeleportTypeSaturnRings;
import beyondspace.world.dimension.SaturnRings.WorldProviderSaturnRings;
import beyondspace.world.dimension.Space.WorldProviderSpace;
import beyondspace.world.dimension.UranusNew.WorldProviderUranusNew;
import galaxyspace.api.BodiesHelper;
import galaxyspace.api.BodiesHelper.BodiesData;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import galaxyspace.systems.SolarSystem.planets.overworld.recipe.AssemblyRecipes;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.galaxies.Star;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IAtmosphericGas;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.core.items.ItemBlockDesc;
import micdoodle8.mods.galacticraft.planets.asteroids.blocks.AsteroidBlocks;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.stats.Achievement;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class RegistrationsList {
	// Blocks
	public static Block advancedRefinery;
	public static Block antimatter;
	public static Block chameleonBlock;
	/** Part of WarpCore tileentity */
	public static Block energyConductor;
	public static Block FeNiMetal;
	public static Block gasGenerator;
	public static Block H2ONH3CH4Ice;
	public static Block HCloud;
	public static Block HMetal;
	public static Block holoMap;
	public static Block laserTurret;
	public static Block lightningrodBase;
	public static Block lightningrodMid;
	public static Block lightningrodTop;
	/** Part of WarpCore tileentity */
	public static Block machineFrame;
	/** Part of WarpCore tileentity */
	public static Block netherCore;
	/** Part of WarpCore tileentity */
	//public static Block nuclearReactor;
	public static Block resonantCasing;
	public static Block silicate;
	public static Block snow;
	public static Block sulfurTorch;
	public static Block ultimateFurnace;
	public static Block ultimateOxygenModule;
	/** Part of WarpCore tileentity */
	public static Block warpControlMonitor;
	/** Part of WarpCore tileentity */
	public static Block warpEngineMultiblock;

	// Fluids
	public static Block diamondLiquid;
	public static Block H2ONH3CH4Liquid;
	public static Block HHeLiquid;
	public static Fluid diamondFluid;
	public static Fluid H2ONH3CH4Fluid;
	public static Fluid HHeFluid;
	
	// Items
	public static Item advancedOxygenTank;
	public static Item armorUpgrade;
	public static Item dehydratedFood;
	public static Item flameThrower;
	public static Item floaterDrop;
	public static Item fuelTank;
	public static Item handRocket;
	public static Item highPressureResistantModularArmorHead;
	public static Item highPressureResistantModularArmorBody;
	public static Item highPressureResistantModularArmorLegs;
	public static Item highPressureResistantModularArmorBoot;
	public static Item ionPlasmaRifle;
	public static Item nuclearRod;
	public static Item nuclearRodEmpty;
	public static Item plasmaAmmo;
	public static Item plasmaHammer;
	public static Item plasmaOmnitool;
	public static Item portableBattery;
	public static Item repairKit;
	public static Item wolframBattery;
	
	// Space Objects
	public static SolarSystem holidays;
	public static Star holiday;
	public static Planet planetHalloween;
	public static Planet planetNewYear;
	public static Planet thanatos;
	public static Moon saturnRings;
	//public static Planet planetValentine;
	//public static Planet planetYeaster;
	public static AchievementPage gaAchievements;
	public static Achievement jupiter;
	public static Achievement saturn;
	public static Achievement diamond_rain;
	public static Achievement saturn_rings;
	public static Achievement uranus;
	public static Achievement neptune;
	public static Achievement indestructible;

	public static boolean isNewYear = (Calendar.getInstance().get(2) + 1 == 12 && Calendar.getInstance().get(5) >= 16 && Calendar.getInstance().get(5) <= 31) || (Calendar.getInstance().get(2) + 1 == 1 && Calendar.getInstance().get(5) >= 1 && Calendar.getInstance().get(5) <= 16) || BSConfig.isNewYear;
	public static boolean isHalloween = (Calendar.getInstance().get(2) + 1 == 10 && Calendar.getInstance().get(5) >= 16 && Calendar.getInstance().get(5) <= 31) || (Calendar.getInstance().get(2) + 1 == 11 && Calendar.getInstance().get(5) >= 1 && Calendar.getInstance().get(5) <= 16) || BSConfig.isHalloween;
	
	public static final ArmorMaterial HPRMA = EnumHelper.addArmorMaterial("HPRMA", 50, new int[] {5, 8, 7, 4}, 0);
	
	public void construct() {
		// Achievements
		jupiter = new Achievement("ach0", "jupiter", 0, 0, AsteroidsItems.tier3Rocket, (Achievement)null).initIndependentStat().registerStat();
		saturn = new Achievement("ach1", "saturn", 2, 0, AsteroidsItems.tier3Rocket, jupiter).initIndependentStat().registerStat();
		diamond_rain = new Achievement("ach2", "diamond_rain", 4, 0, Items.diamond, jupiter).initIndependentStat().registerStat();
		saturn_rings = new Achievement("ach3", "saturn_rings", 4, 2, AsteroidsItems.tier3Rocket, saturn).initIndependentStat().registerStat();
		uranus = new Achievement("ach4", "uranus", 4, 4, AsteroidsItems.tier3Rocket, saturn_rings).initIndependentStat().registerStat();
		neptune = new Achievement("ach5", "neptune", 4, 6, AsteroidsItems.tier3Rocket, uranus).initIndependentStat().registerStat();
		indestructible = new Achievement("ach6", "indestructible", 0, 4, AsteroidsItems.titaniumChestplate, (Achievement)null).initIndependentStat().registerStat();
		gaAchievements = new AchievementPage("GA", new Achievement[]{jupiter, diamond_rain, saturn, saturn_rings, uranus, neptune, indestructible});
		gaAchievements.registerAchievementPage(gaAchievements);
		
		// Blocks		
		advancedRefinery = new AdvancedRefinery("AdvancedRefinery");
		//antimatter = new Antimatter();
		//chameleonBlock = new SealableChameleonBlock();
		//energyConductor = new EnergyConductor();
		FeNiMetal = new BlockPattern(ModInfo.MODID, Material.rock, "FeNiMetal", BeyondSpace.gaTab, 16.0F, "pickaxe", 3, 6000.0F, soundTypeStone, true);
		gasGenerator = new GasGenerator("GasGenerator");
		H2ONH3CH4Ice = new H2ONH3CH4Ice();
		HCloud = new HCloud();
		HMetal = new BlockPattern(ModInfo.MODID, Material.rock, "HMetal", BeyondSpace.gaTab, 16.0F, "pickaxe", 3, 6000.0F, soundTypeStone, true);
		holoMap = new HoloMap();
		//laserTurret = new LaserTurret();
		lightningrodBase = new LightningRodBase();
		lightningrodTop = new LightningRodTop();
		lightningrodMid = new LightningRodMid();
		//machineFrame = new BlockPattern(ModInfo.MODID, Material.iron, "MachineFrameBlock", GAMain.gaTab, 6.0F, "pickaxe", 2, 200.0F, soundTypeMetal, true);
		netherCore = new BlockPattern(ModInfo.MODID, Material.rock, "NetherCore", BeyondSpace.gaTab, 50.0F, "pickaxe", 3, 20000.0F, soundTypeStone, true);
		//nuclearReactor = new NuclearReactor();
		nuclearRod = new ItemPattern(ModInfo.MODID, "NuclearRod", BeyondSpace.gaTab, 1).setMaxDamage(8192);
		nuclearRodEmpty = new ItemPattern(ModInfo.MODID, "NuclearRodEmpty", BeyondSpace.gaTab, 1);
		snow = new BlockPattern(ModInfo.MODID, Material.craftedSnow, "Snow", BeyondSpace.gaTab, 0.5F, "showel", 1, 5.0F, soundTypeSnow, true);
		//resonantCasing = new BlockPattern(ModInfo.MODID, Material.iron, "ResonantCasing", GAMain.gaTab, 6.0F, "pickaxe", 2, 200.0F, soundTypeMetal, true);
		silicate = new BlockPattern(ModInfo.MODID, Material.iron, "Silicate", BeyondSpace.gaTab, 16.0F, "pickaxe", 2, 200.0F, soundTypeMetal, true);
		sulfurTorch = new SulfurTorch(ModInfo.MODID, Material.circuits, "SulfurTorch", BeyondSpace.gaTab);
		ultimateFurnace = new UltimateFurnace(ModInfo.MODID, GCBlocks.machine, "UltimateArcFurnace", BeyondSpace.gaTab, 1.0F, "pickaxe", 2, 20F, soundTypeMetal, true);
		ultimateOxygenModule = new UltimateOxygenModule(ModInfo.MODID, GCBlocks.machine, "UltimateOxygenModule", BeyondSpace.gaTab, 1.0F, "pickaxe", 2, 18F, soundTypeMetal, true);
		//warpControlMonitor = new WarpControlMonitor();
		//warpEngineMultiblock = new WarpEngineMultiblock();
		
		// Items
		// WARNING: register ammo BEFORE weapon!!!
		advancedOxygenTank = new AdvancedOxygenTank(5, "AdvancedOxygenTank");
		armorUpgrade = new ArmorUpgrade();
		dehydratedFood = new DehydratedFood();
		floaterDrop = new FloaterDrop();
		fuelTank = new AmmoBase(250, BeyondSpace.gaTab, "FuelTank", ModInfo.MODID + ":FuelTank");
		flameThrower = new FlameThrower();
		handRocket = new HandRocket();
		plasmaAmmo = new AmmoBase(8, BeyondSpace.gaTab, "IonPlasmaAmmo", ModInfo.MODID + ":PlasmaAmmo");
		ionPlasmaRifle = new IonPlasmaRifle();
		plasmaHammer = new PlasmaHammer();
		plasmaOmnitool = new PlasmaOmnitool();
		portableBattery = new PortableBattery("PortableBattery");
		wolframBattery = new TitaniumBattery("litium-titaniumBattery", BeyondSpace.gaTab);
	
		// Armor
		highPressureResistantModularArmorHead = new HighPressureResistantModularArmor(0).setUnlocalizedName("HPRMAMKIHelmet").setTextureName(ModInfo.MODID + ":HPRMAMKIHelmet");
		highPressureResistantModularArmorBody = new HighPressureResistantModularArmor(1).setUnlocalizedName("HPRMAMKIChestplate").setTextureName(ModInfo.MODID + ":HPRMAMKIChestplate");
		highPressureResistantModularArmorLegs = new HighPressureResistantModularArmor(2).setUnlocalizedName("HPRMAMKILeggings").setTextureName(ModInfo.MODID + ":HPRMAMKILeggings");
		highPressureResistantModularArmorBoot = new HighPressureResistantModularArmor(3).setUnlocalizedName("HPRMAMKIBoots").setTextureName(ModInfo.MODID + ":HPRMAMKIBoots");
	}

	public void constructSpace() {
		// Solar Systems
		holidays = new SolarSystem("Holidays", "Alternate").setMapPosition(new Vector3(0.7D, -0.5D, 0));
		RegisterSolarSystems();

		// Stars
		holiday = (Star) new Star("Holiday").setParentSolarSystem(holidays).setTierRequired(-1).setBodyIcon(new ResourceLocation(GalacticraftCore.ASSET_PREFIX, "textures/gui/celestialbodies/sun.png")).setRelativeSize(1.0F);
		holidays.setMainStar(holiday);

		
		// Planets
		if (isNewYear){
	    planetNewYear = (Planet) new Planet("NewYear")
		.setParentSolarSystem(holidays)
		.setRingColorRGB(1.0F, 1.0F, 1.0F)
		.setPhaseShift(3.1415927F/2)
		.setBodyIcon(new ResourceLocation(ModInfo.MODID, "textures/blocks/Snow.png"))
		.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(1.0F, 1.0F))
		.setRelativeOrbitTime(1)
		.setTierRequired(1)
		.setDimensionInfo(BSConfig.newYear, WorldProviderNewYear.class)
		.atmosphereComponent(IAtmosphericGas.NITROGEN)
		.atmosphereComponent(IAtmosphericGas.OXYGEN)
		.atmosphereComponent(IAtmosphericGas.ARGON)
		.atmosphereComponent(IAtmosphericGas.WATER);
		}
		
		if (isHalloween) {
		planetHalloween = (Planet) new Planet("Halloween")
		.setParentSolarSystem(holidays)
		.setRingColorRGB(1.0F, 1.0F, 1.0F)
		.setPhaseShift(3.1415927F/2)
		.setBodyIcon(new ResourceLocation("textures/blocks/pumpkin_face_on.png"))
		.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(1.25F, 1.25F))
		.setRelativeOrbitTime(1)
		.setTierRequired(1)
		.setDimensionInfo(BSConfig.halloween, WorldProviderHalloweenNew.class)
		.atmosphereComponent(IAtmosphericGas.NITROGEN)
		.atmosphereComponent(IAtmosphericGas.OXYGEN)
		.atmosphereComponent(IAtmosphericGas.ARGON)
		.atmosphereComponent(IAtmosphericGas.WATER);
		}

		DimensionManager.unregisterDimension(1);
		DimensionManager.unregisterProviderType(1);
		
		SolarSystemBodies.planetJupiter.setTierRequired(8)
		.setDimensionInfo(BSConfig.jupiter, WorldProviderJupiterNew.class)
		.atmosphereComponent(IAtmosphericGas.HYDROGEN)
		.atmosphereComponent(IAtmosphericGas.HELIUM);
		
		SolarSystemBodies.planetSaturn.setTierRequired(8)
		.setDimensionInfo(BSConfig.saturn, WorldProviderSaturnNew.class)
		.atmosphereComponent(IAtmosphericGas.HYDROGEN);
		
		SolarSystemBodies.planetUranus.setTierRequired(7)
		.setDimensionInfo(BSConfig.uranus, WorldProviderUranusNew.class)
		.atmosphereComponent(IAtmosphericGas.HYDROGEN)
		.atmosphereComponent(IAtmosphericGas.HELIUM)
		.atmosphereComponent(IAtmosphericGas.METHANE);
		
		SolarSystemBodies.planetNeptune.setTierRequired(7)
		.setDimensionInfo(BSConfig.neptune, WorldProviderNeptuneNew.class)
		.atmosphereComponent(IAtmosphericGas.HYDROGEN)
		.atmosphereComponent(IAtmosphericGas.HELIUM)
		.atmosphereComponent(IAtmosphericGas.METHANE);
		
		//thanatos = (Planet) new Planet("Thanatos").setParentSolarSystem(GalacticraftCore.solarSystemSol).setRingColorRGB(0.0F, 0.0F, 0.0F).setPhaseShift(0).setBodyIcon(new ResourceLocation(ModInfo.MODID, "textures/gui/celestialbodies/Thanatos.png")).setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(15F, 15F)).setRelativeOrbitTime(1000).setTierRequired(8).setDimensionInfo(GAConfig.thanatos, WorldProviderThanatos.class);
		
		// Moons
		saturnRings = (Moon) new Moon("saturnRings").setParentPlanet(SolarSystemBodies.planetSaturn).setRingColorRGB(0.3F, 1.0F, 0.3F).setPhaseShift(0).setBodyIcon(new ResourceLocation(ModInfo.MODID, "textures/gui/celestialbodies/saturnRings.png")).setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(22.5F, 22.5F)).setRelativeOrbitTime(200F).setTierRequired(5).setDimensionInfo(BSConfig.saturnRings, WorldProviderSaturnRings.class);
	}
	
	public void RegisterBlocks() {
		registerBlock(HMetal, getBlockName(HMetal));
		registerBlock(silicate, getBlockName(silicate));
		registerBlock(FeNiMetal, getBlockName(FeNiMetal));
		registerBlock(snow, getBlockName(snow));
		registerBlock(HCloud, getBlockName(HCloud));
		registerBlock(H2ONH3CH4Ice, getBlockName(H2ONH3CH4Ice));
		registerBlock(netherCore, getBlockName(netherCore));
		registerBlock(sulfurTorch, getBlockName(sulfurTorch));
		registerBlock(ultimateFurnace, getBlockName(ultimateFurnace));
		registerBlock(advancedRefinery, getBlockName(advancedRefinery));
		registerBlock(gasGenerator, ItemBlockDesc.class, getBlockName(gasGenerator));
		registerBlock(ultimateOxygenModule, getBlockName(ultimateOxygenModule));
		//registerBlock(nuclearReactor, getBlockName(nuclearReactor));
		registerBlock(lightningrodBase, getBlockName(lightningrodBase));
		registerBlock(lightningrodMid, getBlockName(lightningrodMid));
		registerBlock(lightningrodTop, getBlockName(lightningrodTop));
		registerBlock(holoMap, getBlockName(holoMap));
		//registerBlock(chameleonBlock, getBlockName(chameleonBlock));
		//registerBlock(antimatter, getBlockName(antimatter));
		//registerBlock(laserTurret, getBlockName(laserTurret));
		//registerBlock(resonantCasing, getBlockName(resonantCasing));
		//registerBlock(machineFrame, getBlockName(machineFrame));
		//registerBlock(energyConductor, getBlockName(energyConductor));
		//registerBlock(warpControlMonitor, getBlockName(warpControlMonitor));
		//registerBlock(warpEngineMultiblock, getBlockName(warpEngineMultiblock));
	}

	public void RegisterFluids() {
		diamondFluid = new Fluid("DiamondFluid");
		FluidRegistry.registerFluid(diamondFluid);
		diamondLiquid = new DiamondLiquid(diamondFluid, Material.lava);
		registerBlock(diamondLiquid, getBlockName(diamondLiquid));
		diamondFluid.setUnlocalizedName(diamondLiquid.getUnlocalizedName()).setDensity(1500).setLuminosity(3).setTemperature(5000).setViscosity(50000);
		
		HHeFluid = new Fluid("HHeFluid");
		FluidRegistry.registerFluid(HHeFluid);
		HHeLiquid = new HHeLiquid(HHeFluid, Material.water);
		registerBlock(HHeLiquid, getBlockName(HHeLiquid));
		HHeFluid.setUnlocalizedName(HHeLiquid.getUnlocalizedName()).setDensity(1500).setLuminosity(3).setTemperature(5000).setViscosity(50000);
		
		H2ONH3CH4Fluid = new Fluid("H2ONH3CH4Fluid");
		FluidRegistry.registerFluid(H2ONH3CH4Fluid);
		H2ONH3CH4Liquid = new H2ONH3CH4Liquid(H2ONH3CH4Fluid, Material.lava);
		registerBlock(H2ONH3CH4Liquid, getBlockName(H2ONH3CH4Liquid));
		H2ONH3CH4Fluid.setUnlocalizedName(H2ONH3CH4Liquid.getUnlocalizedName()).setDensity(1500).setLuminosity(3).setTemperature(5000).setViscosity(50000);
	}

	public void RegisterItems() {
		registerItem(handRocket, getItemName(handRocket));
		registerItem(armorUpgrade, "ArmorUpgrade");
		registerItem(ionPlasmaRifle, getItemName(ionPlasmaRifle));
		registerItem(plasmaAmmo, getItemName(plasmaAmmo));
		registerItem(flameThrower, getItemName(flameThrower));
		registerItem(fuelTank, getItemName(fuelTank));
		registerItem(plasmaHammer, getItemName(plasmaHammer));
		registerItem(plasmaOmnitool, getItemName(plasmaOmnitool));
		registerItem(wolframBattery, getItemName(wolframBattery));
		registerItem(portableBattery, getItemName(portableBattery));
		//registerItem(nuclearRod, getItemName(nuclearRod));
		//registerItem(nuclearRodEmpty, getItemName(nuclearRodEmpty));
		registerItem(advancedOxygenTank, getItemName(advancedOxygenTank));
		registerItem(dehydratedFood, "DehydratedFood");
		registerItem(floaterDrop, "Drop");
		
		for (int i = 0; i < ArmorUpgrade.subItems.length; i++) {
			GalacticraftRegistry.addDungeonLoot(3, new ItemStack(armorUpgrade, 1, i));
		}
		
		GSUtils.registerModule(new SolarPanel());
		GSUtils.registerModule(new Kinetic());
		GSUtils.registerModule(new Protection());
		
	}

	private void RegisterSolarSystems() {
		GalaxyRegistry.registerSolarSystem(holidays);
	}
	
	public void RegisterPlanets() {
		if (Switches.spaceDim) {
			DimensionManager.registerProviderType(BSConfig.space, WorldProviderSpace.class, true);
			DimensionManager.registerDimension(BSConfig.space, BSConfig.space);
			GalacticraftRegistry.registerTeleportType(WorldProviderSpace.class, new WorldProviderSpace());
		}
		
		if (isNewYear) {
			GalaxyRegistry.registerPlanet(planetNewYear);
			GalacticraftRegistry.registerTeleportType(WorldProviderNewYear.class, new WorldProviderNewYear());
		}
		
		if(isHalloween) {
			GalaxyRegistry.registerPlanet(planetHalloween);
			GalacticraftRegistry.registerTeleportType(WorldProviderHalloweenNew.class, new WorldProviderHalloweenNew());
		}
			
		//GalaxyRegistry.registerPlanet(SolarSystemBodies.planetJupiter);
		GalacticraftRegistry.registerTeleportType(WorldProviderJupiterNew.class, new WorldProviderJupiter());

		//GalaxyRegistry.registerPlanet(SolarSystemBodies.planetSaturn);
		GalacticraftRegistry.registerTeleportType(WorldProviderSaturnNew.class, new WorldProviderSaturnNew());

		//GalaxyRegistry.registerPlanet(SolarSystemBodies.planetUranus);
		GalacticraftRegistry.registerTeleportType(WorldProviderUranusNew.class, new WorldProviderUranusNew());

		//GalaxyRegistry.registerPlanet(SolarSystemBodies.planetNeptune);
		GalacticraftRegistry.registerTeleportType(WorldProviderNeptuneNew.class, new WorldProviderNeptuneNew());		
		
		/*GalaxyRegistry.registerPlanet(thanatos);
		GalacticraftRegistry.registerTeleportType(WorldProviderThanatos.class, new WorldProviderThanatos());*/	
	}
	
	public void RegisterMoons() {
		GalaxyRegistry.registerMoon(saturnRings);
		GalacticraftRegistry.registerTeleportType(WorldProviderSaturnRings.class, new TeleportTypeSaturnRings());
	}

	public void RegisterTileEntities() {
		registerTileEntity(AdvancedRefineryTileEntity.class, "BS AdvancedRefinery");
		registerTileEntity(GasGeneratorTileEntity.class, "BS GasGenerator");
		registerTileEntity(LightningRodBaseTileEntity.class, "BS LightningRodBase");
		registerTileEntity(LightningRodMidTileEntity.class, "BS LightningRodMid");
		registerTileEntity(LightningRodTopTileEntity.class, "BS LightningRodTop");
		registerTileEntity(UltimateOxygenModuleTileEntity.class, "BS UltimateOxygenModule");
		registerTileEntity(UltimateFurnaceTileEntity.class, "BS UltimateFurnace");
		registerTileEntity(HoloMapTileEntity.class, "BS HoloMap");
	}

	public void RegisterArmor() {
		//registerItem(highPressureResistantModularArmorHead, getItemName(highPressureResistantModularArmorHead));
		//registerItem(highPressureResistantModularArmorBody, getItemName(highPressureResistantModularArmorBody));
		//registerItem(highPressureResistantModularArmorLegs, getItemName(highPressureResistantModularArmorLegs));
		//registerItem(highPressureResistantModularArmorBoot, getItemName(highPressureResistantModularArmorBoot));
	}

	public void RegisterEntities() {
		registerEntity(IonPlasmaBurstEntity.class, "IonPlasmaBurst", BeyondSpace.instance);
		registerEntity(RedLightningEntity.class, "RedLightning", BeyondSpace.instance);
		registerEntityEgg(FloaterEntity.class, "Floater", 0x8B0000, 0xFF8C00, BeyondSpace.instance);
	}

	public void RegisterRecipes() {
		/** CRAFT TABLE  */
		addRecipe(new ItemStack(wolframBattery), //Battery
			new Object[] {"WPW", "SBS", "VVV",
			'W', new ItemStack(GCBlocks.aluminumWire, 1, 0),
			'B', GCItems.battery,
			'P', new ItemStack(GCItems.basicItem, 1, 14),
			'S', new ItemStack(GSItems.CompressedPlates, 1, 8),
			'V', new ItemStack(GSItems.CompressedPlates, 1, 9)});
		
		addRecipe(new ItemStack(advancedOxygenTank), //Ox Tank 4 level
			new Object[] {"TRT", "POP", "PWP",
			'O', new ItemStack(GCItems.oxTankHeavy, 1, 0),
			'P', new ItemStack(GCItems.basicItem, 1, 9),
			'W', new ItemStack(GCItems.battery, 1, 0),
			'T', new ItemStack(AsteroidsItems.basicItem, 1, 6),
			'R', new ItemStack(floaterDrop, 1, 0)});
		
		addRecipe(new ItemStack(sulfurTorch, 4, 0),
			new Object[] {"S", "P",
			'S', new ItemStack(GSItems.BasicItems, 1, 9),
			'P', new ItemStack(Items.stick, 1, 0)});
		
		addRecipe(new ItemStack(advancedRefinery, 1, 0),
			new Object[] {"2C2", "DCD", "SRS",
			'R', GCBlocks.refinery,
			'S', new ItemStack(GCItems.basicItem, 1, 9),
			'D', new ItemStack(MarsItems.marsItemBasic, 1, 5),
			'C', new ItemStack(GCItems.canister, 1, 1)});
		
		addRecipe(new ItemStack(ultimateFurnace),
			new Object[] {"PPP", "PFP", "SWS",
			'P', new ItemStack(AsteroidsItems.basicItem, 1, 0),
			'F', new ItemStack(GCBlocks.machineTiered, 1, 12),
			'S', new ItemStack(GSItems.CompressedPlates, 1, 4),
			'W', new ItemStack(GCItems.basicItem, 1, 14)});			// waferAdvanced
			
		addRecipe(new ItemStack(lightningrodTop),
			new Object[] {"SSS", "SIS", "PPP",
			'S', new ItemStack(GCItems.basicItem, 1, 9),			// compressedSteel
			'I', Blocks.iron_block,
			'P', GCItems.flagPole});
		
		addRecipe(new ItemStack(lightningrodMid),
			new Object[] {"P P", "PPP", "P P",
			'P', GCItems.flagPole});
		
		addRecipe(new ItemStack(lightningrodBase),
			new Object[] {"PWP", "PWP", "SMS",
			'P', GCItems.heavyPlatingTier1,
			'W', new ItemStack(GCBlocks.aluminumWire, 1, 1),
			'S', new ItemStack(GCItems.basicItem, 1, 9),			// compressedSteel
			'M', new ItemStack(GCBlocks.machineTiered, 1, 8)});
		
		addRecipe(new ItemStack(ultimateOxygenModule),
			new Object[] {"FOF", "PCP", "MWM",
			'F', GCItems.oxygenFan,
			'O', new ItemStack(GCBlocks.machineBase2, 1, 8),
			'P', GCBlocks.oxygenPipe,
			'W', new ItemStack(GCItems.basicItem, 1, 14),			// waferAdvanced
			'M', GSBlocks.OxStorageModuleT2,
			'C', GCItems.oxygenConcentrator});
		
		addRecipe(new ItemStack(plasmaAmmo),
			new Object[] {"TTT", "CBC", "TTT",
			'T', new ItemStack(AsteroidsItems.basicItem, 1, 6),		// compressedTitanium
			'C', new ItemStack(GSItems.BasicItems, 1, 10),
			'B', new ItemStack(GCItems.battery, 1, 0)});
		
		addRecipe(new ItemStack(fuelTank),
			new Object[] {"SSS", "AFA", "AAA",
			'F', new ItemStack(GCItems.fuelCanister, 1, 1),
			'S', new ItemStack(GSItems.BasicItems, 1, 9),
			'A', new ItemStack(GCItems.basicItem, 1, 8)});			// compressedAluminum
		
		addRecipe(new ItemStack(repairKit),
			new Object[] {"SSS", "SWS", "SSS",
			'S', new ItemStack(GCItems.basicItem, 1, 9),
			'W', GCItems.wrench,
			});
		
		addShapelessRecipe(new ItemStack(netherCore), Items.nether_star, Items.nether_star, Items.nether_star, Items.nether_star, Items.nether_star, Items.nether_star, Items.nether_star, Items.nether_star, Items.nether_star);
		addShapelessRecipe(new ItemStack(GCItems.basicItem, 1, 2), silicate, silicate, silicate, silicate, silicate, silicate, silicate, silicate, silicate);
		addShapelessRecipe(new ItemStack(Items.iron_ingot), FeNiMetal, FeNiMetal, FeNiMetal, FeNiMetal, FeNiMetal, FeNiMetal, FeNiMetal, FeNiMetal, FeNiMetal);
		addShapelessRecipe(new ItemStack(Items.iron_ingot), HMetal, HMetal, HMetal, HMetal, HMetal, HMetal, HMetal, HMetal, HMetal);
		for (int i = 0; i < DehydratedFood.dehydrated.length; i++) {
			Item food = Items.cooked_beef;
			switch (i) {
				case 0: food = Items.cooked_beef; break;
				case 1: food = Items.beef; break;
				case 2: food = GCItems.cheeseCurd; break;
				case 3: food = Items.cooked_chicken; break;
				case 4: food = Items.chicken; break;
				case 5: food = Items.cooked_fished; break;
				case 6: food = Items.fish; break;
				case 7: food = Items.mushroom_stew; break;
				case 8: food = Items.cooked_porkchop; break;
				case 9: food = Items.porkchop; break;
				case 10: food = Items.baked_potato; break;
				case 11: food = Items.pumpkin_pie; break;
				default: food = Items.golden_apple; break;
			}
			addShapelessRecipe(new ItemStack(dehydratedFood, 1, i), GCItems.canister, food, food);
		}
		/** ASSEMBLY  */
		
		/*AssemblyRecipes.addRecipe(new ItemStack(resonantCasing, 2),
			new Object[] {"DTD", "MCM", "DTD",
			'D', new ItemStack(MarsItems.marsItemBasic, 1, 5),
			'T', new ItemStack(GSItems.CompressedPlates, 1, 9),
			'M', new ItemStack(GSItems.CompressedPlates, 1, 5),
			'C', GSItems.UnknowCrystal});
	
		AssemblyRecipes.addRecipe(new ItemStack(machineFrame, 8),
			new Object[] {"SWS", "ALA", "SWS",
			'S', new ItemStack(GCItems.basicItem, 1, 9),
			'W', new ItemStack(GCBlocks.aluminumWire, 1, 0),
			'A', new ItemStack(GCItems.basicItem, 1, 8),
			'L', new ItemStack(GSItems.CompressedPlates, 1, 3)});
	
		AssemblyRecipes.addRecipe(new ItemStack(energyConductor, 2),
			new Object[] {" W ", "WFW", " W ",
			'W', new ItemStack(GCBlocks.aluminumWire, 1, 1),
			'F', machineFrame});
		
		AssemblyRecipes.addRecipe(new ItemStack(warpControlMonitor),
			new Object[] {" W ", "WSW", " F ",
			'W', new ItemStack(GCBlocks.aluminumWire, 1, 1),
			'F', machineFrame,
			'S', GCBlocks.screen});*/
		/*
		AssemblyRecipes.addRecipe(new ItemStack(highPressureResistantModularArmorBoot), 
			new Object[] {"C C", "C C", "HBH",
			'C', GSItems.CompressedSDHD120,
			'H', GCItems.heavyPlatingTier1,
			'B', GCItems.steelBoots});
		
		AssemblyRecipes.addRecipe(new ItemStack(highPressureResistantModularArmorLegs), 
			new Object[] {"HCH", "CLC", "H H",
			'C', GSItems.CompressedSDHD120,
			'H', GCItems.heavyPlatingTier1,
			'L', GCItems.steelLeggings});
		
		AssemblyRecipes.addRecipe(new ItemStack(highPressureResistantModularArmorBody), 
			new Object[] {"HPH", "CVC", "HCH",
			'C', GSItems.CompressedSDHD120,
			'H', GCItems.heavyPlatingTier1,
			'P', GCItems.steelChestplate,
			'V', GCItems.oxygenVent});
		
		AssemblyRecipes.addRecipe(new ItemStack(highPressureResistantModularArmorHead), 
			new Object[] {"HDH", "CGC", "HOH",
			'C', GSItems.CompressedSDHD120,
			'H', GCItems.heavyPlatingTier1,
			'D', GCItems.steelHelmet,
			'G', Blocks.glass_pane,
			'O', GCItems.oxygenGear});
		*/
		
		
		AssemblyRecipes.instance.addRecipe(new ItemStack(ionPlasmaRifle),
			new Object[] {"LTA", "CWC", "TBT",
			'L', new ItemStack(AsteroidBlocks.beamReceiver),
			'T', new ItemStack(AsteroidsItems.basicItem, 1, 6),		// compressedTitanium
			'C', new ItemStack(GSItems.CompressedPlates, 1, 1),		// compressedCobalt
			'W', new ItemStack(GCItems.basicItem, 1, 14),			// waferAdvanced
			'A', new ItemStack(plasmaAmmo),
			'B', new ItemStack(Blocks.stone_button)});
		
		AssemblyRecipes.instance.addRecipe(new ItemStack(flameThrower), 
			new Object[] {"FP ", "PS ", "QBS",
			'F', new ItemStack(Items.flint_and_steel),
			'P', new ItemStack(GCBlocks.oxygenPipe),
			'S', new ItemStack(GCItems.flagPole),
			'Q', new ItemStack(fuelTank),
			'B', new ItemStack(Blocks.stone_button)});
		
		AssemblyRecipes.instance.addRecipe(new ItemStack(plasmaOmnitool, 1, 100000),
			new Object[] {"CCC", "BRC", "PWC",
			'C', new ItemStack(GSItems.CompressedPlates, 1, 1),
			'B', new ItemStack(wolframBattery, 1, 0),
			'R', new ItemStack(AsteroidBlocks.beamReceiver),
			'P', new ItemStack(GCItems.flagPole),
			'W', new ItemStack(GCBlocks.aluminumWire, 1, 1)});
			
			
		/*
		AssemblyRecipes.instance.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.BLANK.ordinal()),			// Blank
			new Object[] {"WAW", "CBC", "WAW",
			'W', new ItemStack(GCBlocks.aluminumWire, 1, 0),
			'B', new ItemStack(GCItems.basicItem, 1, 13),		// waferBasic
			'A', new ItemStack(GCItems.basicItem, 1, 8),		// compressedAluminum
			'C', new ItemStack(GCItems.basicItem, 1, 6)});		// compressedCopper
		
		AssemblyRecipes.instance.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.ANTIGRAV.ordinal()),		// Anti-gravitational
			new Object[] {"LNL", "SUS", "EWE",
			'U', new ItemStack(armorUpgrade, 1, 0),
			'E', new ItemStack(GCItems.rocketEngine, 1, 0),
			'N', new ItemStack(GCItems.fuelCanister, 1, 1),
			'S', new ItemStack(GCItems.basicItem, 1, 9),		// compressedSteel
			'L', new ItemStack(AsteroidsItems.basicItem, 1, 8),	// beamCore
			'W', new ItemStack(GCItems.basicItem, 1, 14)});		// waferAdvanced
		
		AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.GRAV.ordinal()),			// Gravitational
			new Object[] {"WEW", "SUS", "ICI",
			'U', new ItemStack(armorUpgrade, 1, 0),
			'W', new ItemStack(GCBlocks.aluminumWire, 1, 0),
			'E', GCItems.rocketEngine,
			'S', new ItemStack(GCItems.basicItem, 1, 9),		// compressedSteel
			'I', Blocks.iron_block,
			'C', new ItemStack(GCItems.basicItem, 1, 11)});		// compressedIron
		
		AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.SHOCKWAVE.ordinal()),		// Shockwave
			new Object[] {"EFE", "PUP", "VPV",
			'U', new ItemStack(armorUpgrade, 1, 0),
			'E', GCItems.rocketEngine,
			'F', new ItemStack(GCItems.fuelCanister, 1, 1),
			'P', Blocks.piston,
			'V', GCItems.oxygenFan});
		
		AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.HIGHJUMP.ordinal()),		// High Jump
			new Object[] {"PFP", "AUA", "BEB",
			'U', new ItemStack(armorUpgrade, 1, 0),
			'P', GCBlocks.oxygenPipe,
			'F', new ItemStack(GCItems.fuelCanister, 1, 1),
			'A', new ItemStack(GCBlocks.aluminumWire, 1, 0),
			'E', GCItems.basicItem,
			'B', new ItemStack(GCItems.battery, 1, GCItems.battery.getMaxDamage())});
		
		AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.KINETIC.ordinal()),		// Kinetic
			new Object[] {"FWF", "SUS", "HGH",
			'U', new ItemStack(armorUpgrade, 1, 0),
			'W', new ItemStack(GCItems.basicItem, 1, 14),		// waferAdvanced
			'F', GCItems.oxygenFan,
			'S', new ItemStack(GCItems.basicItem, 1, 9),		// compressedSteel
			'H', new ItemStack(GCBlocks.aluminumWire, 1, 1),
			'G', new ItemStack(GCBlocks.machineBase, 1, 0)});	// coalGenerator
		
		AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.STEPASSIST.ordinal()),		// Stepassist
			new Object[] {"FWF", "PUP", "SPS",
			'U', new ItemStack(armorUpgrade, 1, 0),
			'F', GCItems.oxygenFan,
			'W', new ItemStack(GCBlocks.aluminumWire, 1, 0),
			'P', Blocks.piston,
			'S', new ItemStack(GCItems.basicItem, 1, 9)});		// compressedSteel
		
		AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.FROSTWALK.ordinal()),		// Frostwalk
			new Object[] {"PTP", "FUF", "SVS",
			'U', new ItemStack(armorUpgrade, 1, 0),
			'P', GCBlocks.oxygenPipe,
			'T', new ItemStack(GCItems.oxTankHeavy, 1, 0),
			'F', GCItems.oxygenFan,
			'S', Blocks.snow,
			'V', GCItems.oxygenVent});
		
		AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.SPEED.ordinal()),			// Speed
			new Object[] {"FVF", "WUW", "BPB",
			'U', new ItemStack(armorUpgrade, 1, 0),
			'F', GCItems.oxygenFan,
			'V', GCItems.oxygenVent,
			'W', new ItemStack(GCBlocks.aluminumWire, 1, 0),
			'P', new ItemStack(GCItems.battery, 1, GCItems.battery.getMaxDamage()),
			'B', new ItemStack(GCItems.basicItem, 1, 13)});		// waferBasic
		
		AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.ANTIRAD.ordinal()),		// Anti-radiational
			new Object[] {"LTL", "LUL", "LTL",
			'U', new ItemStack(armorUpgrade, 1, 0),
			'L', new ItemStack(GSItems.CompressedPlates, 1, 3),	// compressedLead
			'T', new ItemStack(AsteroidsItems.basicItem, 1, 7)});// thermalCloth
		
		AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.EXPLPROF.ordinal()),		// Explosion Protection
			new Object[] {"SOS", "SUS", "SOS",
			'U', new ItemStack(armorUpgrade, 1, 0),
			'O', Blocks.obsidian,
			'S', GSItems.CompressedSDHD120});
		
		AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.FASTDIGGING.ordinal()),	// Fast Digging
			new Object[] {"SPS", "PUP", "TFT",
			'U', new ItemStack(armorUpgrade, 1, 0),
			'S', Blocks.piston,
			'P', GCBlocks.oxygenPipe,
			'F', GCItems.oxygenFan,
			'T', new ItemStack(GCItems.oxTankMedium, 1, 0)});
		
		AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.FIREPROF.ordinal()),		// Fire Protection
			new Object[] {"TCT", "TUT", "LCL",
			'U', new ItemStack(armorUpgrade, 1, 0),
			'T', new ItemStack(AsteroidsItems.basicItem, 1, 7),	// thermalCloth
			'L', Items.leather,
			'C', new ItemStack(GCItems.basicItem, 1, 20)});		// ambientThermalController
		
		AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.FIREPROF.ordinal()),		// Invisibility
			new Object[] {"ABA", "CUC", "PWP",
			'U', new ItemStack(armorUpgrade, 1, 0),
			'A', new ItemStack(GCBlocks.aluminumWire, 1, 0),
			'B', new ItemStack(GCItems.battery, 1, GCItems.battery.getMaxDamage()),
			'C', new ItemStack(AsteroidsItems.basicItem, 1, 8),	// beamCore
			'P', new ItemStack(AsteroidsItems.basicItem, 1, 7),	// thermalCloth
			'W', new ItemStack(GCItems.basicItem, 1, 14)});		// waferAdvanced
		
		AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.JETPACK.ordinal()),		// Jetpack
			new Object[] {"FWF", "PUP", "EAE",
			'U', new ItemStack(armorUpgrade, 1, 0),
			'F', new ItemStack(GCItems.fuelCanister, 1, 1),
			'W', new ItemStack(GCItems.basicItem, 1, 14),		// waferAdvanced
			'P', GCBlocks.oxygenPipe,
			'A', new ItemStack(GCBlocks.aluminumWire, 1, 1),
			'E', GCItems.rocketEngine});
		
		AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.PROTECTION.ordinal()),		// Protection
			new Object[] {"SHS", "HUH", "SHS",
			'U', new ItemStack(armorUpgrade, 1, 0),
			'S', new ItemStack(GCItems.basicItem, 1, 9),		// compressedSteel
			'H', GSItems.CompressedSDHD120});
		
		AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.TESLA.ordinal()),			// Tesla
			new Object[] {"PWP", "BUB", "IMI",
			'U', new ItemStack(armorUpgrade, 1, 0),
			'P', GCItems.flagPole,
			'W', new ItemStack(GCItems.basicItem, 1, 14),		// waferAdvanced
			'B', Blocks.iron_bars,
			'I', Blocks.iron_block,
			'M', new ItemStack(GCBlocks.machineTiered, 1, 0)});
		
		AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.AUTOFEED.ordinal()),		// Autofeed
			new Object[] {"AHM", "PUP", "KCG",
			'U', new ItemStack(armorUpgrade, 1, 0),
			'H', Blocks.hopper,
			'C', Items.cake,
			'P', GCBlocks.oxygenPipe,
			'A', new ItemStack(GCItems.basicItem, 1, 15),		// dehydratedApple
			'G', new ItemStack(GCItems.basicItem, 1, 16),		// dehydratedCarrot
			'M', new ItemStack(GCItems.basicItem, 1, 17),		// dehydratedMelon
			'K', new ItemStack(GCItems.basicItem, 1, 18)});		// dehydratedPotato
		
		AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.DETOXICATOR.ordinal()),	// Detoxicate
			new Object[] {"PGP", "MUM", "ZWZ",
			'U', new ItemStack(armorUpgrade, 1, 0),
			'P', GCBlocks.oxygenPipe,
			'G', new ItemStack(Items.golden_apple, 1, 1),
			'M', Items.milk_bucket,
			'W', new ItemStack(GCItems.basicItem, 1, 14),		// advancedWafer
			'Z', Items.speckled_melon});
		
		AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.NIGHTVIS.ordinal()),		// Night Vision
			new Object[] {"ABA", "PUP", "GWG",
			'U', new ItemStack(armorUpgrade, 1, 0),
			'A', new ItemStack(GCBlocks.aluminumWire, 1, 1),
			'B', new ItemStack(GCItems.battery, 1, GCItems.battery.getMaxDamage()),
			'W', new ItemStack(GCItems.basicItem, 1, 14),		// advancedWafer
			'P', Blocks.glowstone,
			'G', new ItemStack(Blocks.stained_glass_pane, 1, 5)});
		
		AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.SENSOR.ordinal()),			// Sensor
			new Object[] {"SDS", "SUS", "GCG",
			'U', new ItemStack(armorUpgrade, 1, 0),
			'D', Items.diamond,
			'S', Items.string,
			'G', GCItems.sensorLens,
			'C', new ItemStack(GCItems.basicItem, 1, 9)});		// compressedSteel
		
		AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.SOLAR.ordinal()),			// Solar Panel
			new Object[] {"TPT", "WUW", "SAS",
			'U', new ItemStack(armorUpgrade, 1, 0),
			'P', new ItemStack(GCBlocks.solarPanel, 1, 0),
			'T', new ItemStack(GCItems.basicItem, 1, 7),		// compressedTin
			'W', new ItemStack(GCBlocks.aluminumWire, 1, 1),
			'A', new ItemStack(GCItems.basicItem, 1, 14),		// advancedWafer
			'S', new ItemStack(GCItems.basicItem, 1, 9)});		// compressedSteel
		
		AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.WATERBR.ordinal()),		// Water Breathing
			new Object[] {"PGP", "BUB", "WOW",
			'U', new ItemStack(armorUpgrade, 1, 0),
			'G', GCItems.oxygenGear,
			'P', GCBlocks.oxygenPipe,
			'B', new ItemStack(GCItems.oxTankHeavy, 1, 0),
			'O', GCItems.oxygenConcentrator,
			'W', GCItems.oxygenFan});
		
		AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.BATLV.ordinal()),			// Bat LV
			new Object[] {"BSB", "WUW", "BAB",
			'U', new ItemStack(armorUpgrade, 1, 0),
			'B', new ItemStack(GCItems.battery, 1, GCItems.battery.getMaxDamage()),
			'S', new ItemStack(GCItems.basicItem, 1, 9),		// compressedSteel
			'A', new ItemStack(GCItems.basicItem, 1, 14),		// advancedWafer
			'W', new ItemStack(GCBlocks.aluminumWire, 1, 1)});
		
		AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.BATMV.ordinal()),			// Bat MV
			new Object[] {"BSB", "WUW", "AMA",
			'U', new ItemStack(armorUpgrade, 1, 0),
			'B', new ItemStack(GSItems.LeadBattery, 1, GSItems.LeadBattery.getMaxDamage()),
			'S', new ItemStack(GCItems.basicItem, 1, 9),		// compressedSteel
			'A', new ItemStack(GCItems.basicItem, 1, 14),		// advancedWafer
			'W', new ItemStack(GCBlocks.aluminumWire, 1, 1),
			'M', new ItemStack(GCBlocks.machineTiered, 1, 0)});
		
		AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.BATHV.ordinal()),			// Bat HV
			new Object[] {"BSB", "WUW", "AMA",
			'U', new ItemStack(armorUpgrade, 1, 0),
			'B', new ItemStack(wolframBattery, 1, wolframBattery.getMaxDamage()),
			'S', new ItemStack(GCItems.basicItem, 1, 9),		// compressedSteel
			'A', new ItemStack(GCItems.basicItem, 1, 14),		// advancedWafer
			'W', new ItemStack(GCBlocks.aluminumWire, 1, 1),
			'M', new ItemStack(GCBlocks.machineTiered, 1, 0)});*/
	}

	public void RegisterRecipesPost() {
		/*if (Loader.isModLoaded("Botania"))
			AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.MANAPROF.ordinal()),	// Proficiency
				new Object[] {"DSD", "WUW", "PSP",
				'U', new ItemStack(armorUpgrade, 1, 0),
				'D', new ItemStack(findItem("Botania", "manaResource"), 1, 2),
				'S', findItem("Botania", "obedienceStick"),
				'W', findItem("Botania", "twigWand"),
				'P', new ItemStack(findItem("Botania", "manaResource"), 1, 1)});
		
		if (Loader.isModLoaded("Thaumcraft"))
			AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.REVEALING.ordinal()),	// Revealing
				new Object[] {"GLG", "LUL", "MLM",
				'U', new ItemStack(armorUpgrade, 1, 0),
				'L', Items.leather,
				'G', Items.gold_ingot,
				'M', findItem("Thaumcraft", "ItemThaumometer")});
		
		if (Loader.isModLoaded("Botania"))
			AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.MANADISC.ordinal()),	// Mana Discount
				new Object[] {"DHD", "BUL", "MNM",
				'U', new ItemStack(armorUpgrade, 1, 0),
				'D', new ItemStack(findItem("Botania", "manaResource"), 1, 2),
				'M', new ItemStack(findItem("Botania", "manaResource"), 1, 0),
				'H', findItem("Botania", "manaweaveHelm"),
				'B', findItem("Botania", "manaweaveChest"),
				'L', findItem("Botania", "manaweaveLegs"),
				'N', findItem("Botania", "manaweaveBoots")});
			
		if (Loader.isModLoaded("Thaumcraft"))
			AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.RUNIC.ordinal()),		// Runic Shielding
				new Object[] {"SGS", "CUC", "HAH",
				'U', new ItemStack(armorUpgrade, 1, 0),
				'G', Items.gold_ingot,
				'S', new ItemStack(findItem("Thaumcraft", "ItemResource"), 1, 14),
				'C', new ItemStack(findItem("Thaumcraft", "ItemResource"), 1, 15),
				'H', new ItemStack(findItem("Thaumcraft", "ItemShard"), 1, 6),
				'A', findItem("Thaumcraft", "ItemAmuletRunic")});
		
		if (Loader.isModLoaded("Thaumcraft"))
			AssemblyRecipes.addRecipe(new ItemStack(armorUpgrade, 1, UpgradeList.VISDISC.ordinal()),	// Vis Discount
				new Object[] {"HCH", "BUL", "HNH",
				'U', new ItemStack(armorUpgrade, 1, 0),
				'H', new ItemStack(findItem("Thaumcraft", "ItemShard"), 1, 6),
				'C', new ItemStack(findItem("Thaumcraft", "ItemResource"), 1, 15),
				'B', findItem("Thaumcraft", "ItemChestplateRobe"),
				'L', findItem("Thaumcraft", "ItemLeggingsRobe"),
				'N', findItem("Thaumcraft", "ItemBootsRobe")});*/
	}
	
	public static void addOreDictRecipe(ItemStack output, Object... recipe) {
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(output, recipe));
	}
}
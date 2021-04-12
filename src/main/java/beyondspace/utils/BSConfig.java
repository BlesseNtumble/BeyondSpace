package beyondspace.utils;

import beyondspace.BeyondSpace;
import net.minecraftforge.common.config.Configuration;

public class BSConfig extends Configuration {
	// Categories
	public static final String DIMIDS = Configuration.CATEGORY_GENERAL + Configuration.CATEGORY_SPLITTER + "Dimension IDs";
	public static final String BIOMEIDS = Configuration.CATEGORY_GENERAL + Configuration.CATEGORY_SPLITTER + "Biome IDs";
	public static final String PLANETS = Configuration.CATEGORY_GENERAL + Configuration.CATEGORY_SPLITTER + "Planets";
	public static final String FEATURES = Configuration.CATEGORY_GENERAL + Configuration.CATEGORY_SPLITTER + "Features";
	
	// DIM IDs
	public static int space = 9000;
	public static int jupiter = 100;
	public static int saturn = 101;
	public static int saturnRings = 200;
	public static int uranus = 102;
	public static int neptune = 103;
	public static int thanatos = -1000;
	public static int newYear = 1000;
	public static int halloween = 666;

	// Biome IDs
	public static int BiomeJupiter = 50;
	public static int BiomeSaturn = 51;
	public static int BiomeSaturnRings = 52;
	public static int BiomeUranus = 53;
	public static int BiomeNeptune = 54;
	public static int BiomeThanatos = 55;
	public static int BiomeNewYear = 56;
	public static int BiomeHalloween = 57;
	
	// Planets
	public static boolean isNewYear = false;
	public static boolean isHalloween = false;
	
	// Features
	public static boolean isSaturnDiamondRain = true;
	public static boolean isOverworldMeteors = true;
	
	public static double debugscale = 1;
	/** Absolute Star Size */
	public static double StarSize = 16384.0;
	/** Absolute Planet Size */
	public static double PlanetSize = 1024.0;
	/** Absolute Moon Size */
	public static double MoonSize = 192.0;
	/** Absolute Star <-> Star Distance */
	public static double StarStarDistance = 1000.0;
	/** Absolute Star <-> Planet Distance */
	public static double StarPlanetDistance = 65536.0;
	/** Absolute Planet <-> Moon Distance */
	public static double PlanetMoonDistance = 2048.0;

	public static void syncConfig() {
		BeyondSpace.config.addCustomCategoryComment(DIMIDS, "Change dimension IDs here");
		BeyondSpace.config.addCustomCategoryComment(BIOMEIDS, "Change biome IDs here");
		BeyondSpace.config.addCustomCategoryComment(PLANETS, "Disable unwanted planets here here");
		BeyondSpace.config.addCustomCategoryComment(FEATURES, "Turn off features here");
		
		space = BeyondSpace.config.getInt("Space dimension ID", DIMIDS, space, Integer.MIN_VALUE/2, Integer.MAX_VALUE/2, "ID of Space dimension");
		
		jupiter = BeyondSpace.config.get(DIMIDS, "Jupiter dimension ID", jupiter).getInt(jupiter);
		saturn = BeyondSpace.config.get(DIMIDS, "Saturn dimension ID", saturn).getInt(saturn);
		saturnRings = BeyondSpace.config.get(DIMIDS, "Saturn Rings dimension ID", saturnRings).getInt(saturnRings);
		uranus = BeyondSpace.config.get(DIMIDS, "Uranus dimension ID", uranus).getInt(uranus);
		neptune = BeyondSpace.config.get(DIMIDS, "Neptune dimension ID", neptune).getInt(neptune);
		thanatos = BeyondSpace.config.get(DIMIDS, "Thanatos dimension ID", thanatos).getInt(thanatos);
		newYear = BeyondSpace.config.get(DIMIDS, "New Year Planet dimension ID", newYear).getInt(newYear);
		halloween = BeyondSpace.config.get(DIMIDS, "Halloween Planet dimension ID", halloween).getInt(halloween);

		BiomeJupiter = BeyondSpace.config.get(BIOMEIDS, "Jupiter biome ID", BiomeJupiter).getInt(BiomeJupiter);
		BiomeSaturn = BeyondSpace.config.get(BIOMEIDS, "Saturn biome ID", BiomeSaturn).getInt(BiomeSaturn);
		BiomeSaturnRings = BeyondSpace.config.get(BIOMEIDS, "Saturn Rings biome ID", BiomeSaturnRings).getInt(BiomeSaturnRings);
		BiomeUranus = BeyondSpace.config.get(BIOMEIDS, "Uranus biome ID", BiomeUranus).getInt(BiomeUranus);
		BiomeNeptune = BeyondSpace.config.get(BIOMEIDS, "Neptune biome ID", BiomeNeptune).getInt(BiomeNeptune);
		BiomeThanatos = BeyondSpace.config.get(BIOMEIDS, "Thanatos biome ID", BiomeThanatos).getInt(BiomeThanatos);
		BiomeNewYear = BeyondSpace.config.get(BIOMEIDS, "New Year Planet biome ID", BiomeNewYear).getInt(BiomeNewYear);
		BiomeHalloween = BeyondSpace.config.get(BIOMEIDS, "Halloween Planet biome ID", BiomeHalloween).getInt(BiomeHalloween);
		
		isNewYear = BeyondSpace.config.get(PLANETS, "Override calendar for New Year Planet", isNewYear).getBoolean(isNewYear);
		isHalloween = BeyondSpace.config.get(PLANETS, "Override calendar for Halloween Planet", isHalloween).getBoolean(isHalloween);

		isSaturnDiamondRain = BeyondSpace.config.get(FEATURES, "Enable Diamond rain on Saturn", isSaturnDiamondRain).getBoolean(isSaturnDiamondRain);
		isOverworldMeteors = BeyondSpace.config.get(FEATURES, "Enable meteors in Overworld", isOverworldMeteors).getBoolean(isOverworldMeteors);

		StarSize = BeyondSpace.config.get(FEATURES, "Absolute Star Size", StarSize).getDouble(StarSize);
		PlanetSize = BeyondSpace.config.get(FEATURES, "Absolute Planet Size", PlanetSize).getDouble(PlanetSize);
		MoonSize = BeyondSpace.config.get(FEATURES, "Absolute Moon Size", MoonSize).getDouble(MoonSize);
		StarStarDistance = BeyondSpace.config.get(FEATURES, "Absolute Star <-> Star Distance", StarStarDistance).getDouble(StarStarDistance);
		StarPlanetDistance = BeyondSpace.config.get(FEATURES, "Absolute Star <-> Planet Distance", StarPlanetDistance).getDouble(StarPlanetDistance);
		PlanetMoonDistance = BeyondSpace.config.get(FEATURES, "AAbsolute Planet <-> Moon Distance", PlanetMoonDistance).getDouble(PlanetMoonDistance);
		
		if (BeyondSpace.config.hasChanged()) {
			BeyondSpace.config.save();
		}
	}
}
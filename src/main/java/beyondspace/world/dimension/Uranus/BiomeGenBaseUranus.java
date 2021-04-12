package beyondspace.world.dimension.Uranus;

import beyondspace.utils.BSConfig;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeGenBaseUranus extends BiomeGenBase {

	public static final BiomeGenBase uranus = new BiomeGenBaseUranus(BSConfig.BiomeUranus);
	
	public BiomeGenBaseUranus(int i) {
		super(i);
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
		
		this.rainfall = 0;
		this.temperature = 500.0F;
		this.setBiomeName("Uranus");
		
		if (!ConfigManagerCore.disableBiomeTypeRegistrations) {
			BiomeDictionary.registerBiomeType(this, new BiomeDictionary.Type[] { BiomeDictionary.Type.COLD, BiomeDictionary.Type.DRY, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.OCEAN });
		}
	}
}
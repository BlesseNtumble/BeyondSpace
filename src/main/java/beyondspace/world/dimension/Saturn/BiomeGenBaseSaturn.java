package beyondspace.world.dimension.Saturn;

import beyondspace.utils.BSConfig;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeGenBaseSaturn extends BiomeGenBase {

	public static final BiomeGenBase saturn = new BiomeGenBaseSaturn(BSConfig.BiomeSaturn);
	
	public BiomeGenBaseSaturn(int i) {
		super(i);
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
		
		this.rainfall = 0;
		this.temperature = 500.0F;
		this.setBiomeName("Saturn");
		
		if (!ConfigManagerCore.disableBiomeTypeRegistrations) {
			BiomeDictionary.registerBiomeType(this, new BiomeDictionary.Type[] { BiomeDictionary.Type.HOT, BiomeDictionary.Type.DRY, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.OCEAN });
		}
	}
}
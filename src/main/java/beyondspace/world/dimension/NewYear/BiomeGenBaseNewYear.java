package beyondspace.world.dimension.NewYear;

import beyondspace.utils.BSConfig;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeGenBaseNewYear extends BiomeGenBase {
	
	public static final BiomeGenBase newYear = new BiomeGenBaseNewYear(BSConfig.BiomeNewYear);

	BiomeGenBaseNewYear(int id) {
        super(id);
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCreatureList.clear();
        
        this.rainfall = Float.MAX_VALUE;
		this.temperature = -24.0F;
        this.setBiomeName("NewYear");
        
        if (!ConfigManagerCore.disableBiomeTypeRegistrations) {
			BiomeDictionary.registerBiomeType(this, BiomeDictionary.Type.COLD, BiomeDictionary.Type.DRY, BiomeDictionary.Type.SNOWY);
		}
    }

    @Override
    public BiomeGenBaseNewYear setColor(int var1) {
        return (BiomeGenBaseNewYear) super.setColor(var1);
    }

    @Override
    public float getSpawningChance() {
        return 0.05F;
    }
}
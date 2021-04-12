package beyondspace.world.dimension.Halloween;

import beyondspace.utils.BSConfig;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeGenBaseHalloween extends BiomeGenBase {
	
	public static final BiomeGenBase halloween = new BiomeGenBaseHalloween(BSConfig.BiomeHalloween);

	BiomeGenBaseHalloween(int id) {
        super(id);
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCreatureList.clear();
        
        this.rainfall = 0;
		this.temperature = 15.0F;
        this.setBiomeName("Halloween");
        
        if (!ConfigManagerCore.disableBiomeTypeRegistrations) {
			BiomeDictionary.registerBiomeType(this, BiomeDictionary.Type.MAGICAL, BiomeDictionary.Type.DRY, BiomeDictionary.Type.HILLS);
		}
    }

    @Override
    public BiomeGenBaseHalloween setColor(int var1) {
        return (BiomeGenBaseHalloween) super.setColor(var1);
    }

    @Override
    public float getSpawningChance() {
        return 0.05F;
    }
}
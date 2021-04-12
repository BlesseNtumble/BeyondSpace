package beyondspace.world.dimension.Thanatos;

import beyondspace.utils.BSConfig;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeGenBaseThanatos extends BiomeGenBase {

	public static final BiomeGenBase thanatos = new BiomeGenBaseThanatos(BSConfig.BiomeThanatos);

	public BiomeGenBaseThanatos(int id) {
        super(id);
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCreatureList.clear();
        
        this.rainfall = 0;
		this.temperature = -273.0F;
        this.setBiomeName("Thanatos");
        
        if (!ConfigManagerCore.disableBiomeTypeRegistrations) {
			BiomeDictionary.registerBiomeType(this, BiomeDictionary.Type.COLD, BiomeDictionary.Type.DRY, BiomeDictionary.Type.DEAD);
		}
    }

    @Override
    public BiomeGenBaseThanatos setColor(int var1) {
        return (BiomeGenBaseThanatos) super.setColor(var1);
    }

    @Override
    public float getSpawningChance() {
        return 0.05F;
    }
}

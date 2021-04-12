package beyondspace.world.dimension.Jupiter;

import beyondspace.entity.FloaterEntity;
import beyondspace.utils.BSConfig;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeGenBaseJupiter extends BiomeGenBase
{
    public static final BiomeGenBase jupiter = new BiomeGenBaseJupiter(BSConfig.BiomeJupiter).setBiomeName("Jupiter");

    @SuppressWarnings("unchecked")
    private BiomeGenBaseJupiter(int var1)
    {
        super(var1);
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(FloaterEntity.class, 50, 4, 4));
        this.rainfall = 0F;
        this.temperature = 500F;
        if (!ConfigManagerCore.disableBiomeTypeRegistrations)
        {
            BiomeDictionary.registerBiomeType(this, BiomeDictionary.Type.HOT, BiomeDictionary.Type.DRY, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.SPOOKY);
        }
    }

    @Override
    public BiomeGenBaseJupiter setColor(int var1)
    {
        return (BiomeGenBaseJupiter) super.setColor(var1);
    }

    @Override
    public float getSpawningChance()
    {
        return 0.01F;
    }
}
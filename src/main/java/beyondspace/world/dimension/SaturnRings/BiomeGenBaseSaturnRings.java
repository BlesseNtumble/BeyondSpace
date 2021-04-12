package beyondspace.world.dimension.SaturnRings;

import beyondspace.utils.BSConfig;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedZombie;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeGenBaseSaturnRings extends BiomeGenBase
{
    public static final BiomeGenBase saturnRings = new BiomeGenBaseSaturnRings(BSConfig.BiomeSaturnRings).setBiomeName("Saturn Rings");

    @SuppressWarnings("unchecked")
    private BiomeGenBaseSaturnRings(int var1)
    {
        super(var1);
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.add(new SpawnListEntry(EntityEvolvedZombie.class, 10, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityEvolvedSpider.class, 10, 4, 4));
        this.rainfall = 0F;
        if (!ConfigManagerCore.disableBiomeTypeRegistrations)
        {
            BiomeDictionary.registerBiomeType(this, BiomeDictionary.Type.COLD, BiomeDictionary.Type.DRY, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.SPOOKY);
        }
    }

    @Override
    public BiomeGenBaseSaturnRings setColor(int var1)
    {
        return (BiomeGenBaseSaturnRings) super.setColor(var1);
    }

    @Override
    public float getSpawningChance()
    {
        return 0.01F;
    }
}
package beyondspace.world.dimension.JupiterNew;

import beyondspace.entity.FloaterEntity;
import galaxyspace.core.world.worldengine.WE_Biome;
import net.minecraft.world.biome.BiomeGenBase;

public class JupiterBiome extends WE_Biome {
	public JupiterBiome() {
		super(0, false);
		this.setBiomeName("Jupiter");
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(FloaterEntity.class, 50, 4, 4));
	}
}
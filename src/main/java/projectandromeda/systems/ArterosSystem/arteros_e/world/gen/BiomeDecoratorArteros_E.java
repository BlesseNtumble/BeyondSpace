package projectandromeda.systems.ArterosSystem.arteros_e.world.gen;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import net.minecraft.world.World;

public class BiomeDecoratorArteros_E extends BiomeDecoratorSpace
{
    private World currentWorld;
  
    public BiomeDecoratorArteros_E()
    {
          
    }

    @Override
    protected void decorate()
    {

    }

    @Override
    protected void setCurrentWorld(World world)
    {
        this.currentWorld = world;
    }

    @Override
    protected World getCurrentWorld()
    {
        return this.currentWorld;
    }
}

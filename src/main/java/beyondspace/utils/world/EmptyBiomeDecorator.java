package beyondspace.utils.world;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import net.minecraft.world.World;

public class EmptyBiomeDecorator extends BiomeDecoratorSpace{
	
	private World world;

	public EmptyBiomeDecorator()
	{
	}	

	@Override
	protected void decorate()
	{

	}

	@Override
	protected void setCurrentWorld(World world)
	{
		this.world = world;
	}

	@Override
	protected World getCurrentWorld()
	{
		return this.world;
	}
}

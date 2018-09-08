package projectandromeda.core.utils;

import net.minecraft.entity.ai.attributes.BaseAttribute;
import net.minecraft.entity.ai.attributes.IAttribute;

public class PAAttributePlayer {

	public static final IAttribute RACE = new BaseAttribute(null, "race", 0)
	{
	    @Override
	    public double clampValue(double value)
	    {
	        return value;
	    }
	}.setShouldWatch(true);
}

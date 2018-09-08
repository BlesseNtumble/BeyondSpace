package projectandromeda.core.proxy;

import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
	public void preload() {}
	public void load() {}
	public void postload() {}
	public void registerVariants() {}
	public void spawnParticle(String particleID, Vector3 position, Vector3 motion, Object[] otherInfo) {}
	public void registerRender() {}

	public void register_event(Object obj) {
		MinecraftForge.EVENT_BUS.register(obj);
	}
}

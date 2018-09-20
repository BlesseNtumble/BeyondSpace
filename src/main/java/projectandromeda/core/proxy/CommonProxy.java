package projectandromeda.core.proxy;

import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraftforge.common.MinecraftForge;
import projectandromeda.core.registers.blocks.PABlocks;

public class CommonProxy {
	public void preload() {
		PABlocks.register();
	}
	public void load() {}
	public void postload() {}
	public void registerVariants() {}
	public void spawnParticle(String particleID, Vector3 position, Vector3 motion, Object[] otherInfo) {}
	public void registerRender() {}

	public void register_event(Object obj) {
		MinecraftForge.EVENT_BUS.register(obj);
	}
}

package beyondspace.bukkit;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredListener;

import com.google.common.collect.Lists;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class BukkitUtils {
	
	public static org.bukkit.entity.Entity toBukkitEntity(Entity entity) throws Exception
	{
		final Method getBukkitEntity;
		{
			try
			{
				getBukkitEntity = Entity.class.getDeclaredMethod("getBukkitEntity");
				getBukkitEntity.setAccessible(true);

			}
			catch (Throwable throwable)
			{
				throw new RuntimeException("Failed hooking CraftBukkit methods!", throwable);
			}
		}
		
		return (org.bukkit.entity.Entity) getBukkitEntity.invoke(entity);
	}

	public static Player toBukkitEntity(EntityPlayer player) throws Exception
	{
		final Method getBukkitEntity;
		{
			try
			{
				getBukkitEntity = Entity.class.getDeclaredMethod("getBukkitEntity");
				getBukkitEntity.setAccessible(true);

			}
			catch (Throwable throwable)
			{
				throw new RuntimeException("Failed hooking CraftBukkit methods!", throwable);
			}
		}
		return (Player) getBukkitEntity.invoke(player);
	}
	
	public static void callEvent(Event event)
	{
		PluginManager plManager = Bukkit.getPluginManager();
		Plugin plugin = plManager.getPlugin("WorldGuard");
		final List<RegisteredListener> listeners = Lists.newArrayList();
		listeners.addAll(HandlerList.getRegisteredListeners(plugin));
		
		for (RegisteredListener listener : listeners)
		{
			try
			{
				listener.callEvent(event);
			}
			catch (Throwable throwable)
			{
				
			}
		}
	}
	
	public static boolean cantBreak(@Nonnull EntityPlayer player, int x, int y, int z)
	{
		try
		{
			Player bPlayer = toBukkitEntity(player);
			BlockBreakEvent event = new BlockBreakEvent(bPlayer.getWorld().getBlockAt(x, y, z), bPlayer);
			callEvent(event);
			return event.isCancelled();
		}
		catch (Throwable throwable)
		{
			return true;
		}
	}

	public static boolean cantBreak(@Nonnull EntityPlayer player, double x, double y, double z)
	{
		int xx = MathHelper.floor_double(x);
		int yy = MathHelper.floor_double(y);
		int zz = MathHelper.floor_double(z);
		return cantBreak(player, xx, yy, zz);
	}

	public static boolean cantDamage(@Nonnull Entity attacker, @Nonnull Entity victim)
	{
		try
		{
			EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(toBukkitEntity(attacker), toBukkitEntity(victim), DamageCause.ENTITY_ATTACK, 0D);
			callEvent(event);
			return event.isCancelled();
		}
		catch (Throwable throwable)
		{
			return true;
		}
	}
	
}

package beyondspace.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.lwjgl.input.Keyboard;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import beyondspace.asjlib.ASJUtilities;
import beyondspace.items.render.AdvancedArmorModel;
import beyondspace.items.render.HighPressureResistantModularArmorRender;
import beyondspace.proxy.network.CommonEventHandler;
import beyondspace.utils.RegistrationsList;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.api.item.IItemPressurized;
import galaxyspace.core.registers.potions.GSPotions;
import micdoodle8.mods.galacticraft.api.item.IArmorGravity;
import micdoodle8.mods.galacticraft.api.item.IItemElectric;
import micdoodle8.mods.galacticraft.core.client.gui.overlay.OverlaySensorGlasses;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.IGoggles;
import thaumcraft.api.IRunicArmor;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.IRevealer;
import vazkii.botania.api.item.IManaProficiencyArmor;
import vazkii.botania.api.mana.IManaDiscountArmor;

@Optional.InterfaceList({
    @Optional.Interface(iface = "thaumcraft.api.IGoggles", modid = "Thaumcraft"),
    @Optional.Interface(iface = "thaumcraft.api.IRunicArmor", modid = "Thaumcraft"),
    @Optional.Interface(iface = "thaumcraft.api.IVisDiscountGear", modid = "Thaumcraft"),
    @Optional.Interface(iface = "thaumcraft.api.IRunicArmor", modid = "Thaumcraft"),
    @Optional.Interface(iface = "thaumcraft.api.nodes.IRevealer", modid = "Thaumcraft"),
    @Optional.Interface(iface = "vazkii.botania.api.item.IManaProficiencyArmor", modid = "Botania"),
    @Optional.Interface(iface = "vazkii.botania.api.mana.IManaDiscountArmor", modid = "Botania")
})
public class HighPressureResistantModularArmor extends ItemArmor implements IItemPressurized, IArmorGravity,
																			IItemElectric,
																			IRevealer, IGoggles, IVisDiscountGear, IRunicArmor,
																			IManaDiscountArmor, IManaProficiencyArmor {
	
	/** Boots Upgrade */
	public static String antigrav = ModInfo.MIDP + "antigrav", grav = ModInfo.MIDP + "grav", shockwave = ModInfo.MIDP + "shockwave", highjump = ModInfo.MIDP + "highjump", kinetic = ModInfo.MIDP + "kinetic", stepassist = ModInfo.MIDP + "stepassist", frostwalk = ModInfo.MIDP + "frostwalk";
	/** Leggings Upgrade */
	public static String speed = ModInfo.MIDP + "speed";
	/** Chestplate Upgrade */
	public static String antirad = ModInfo.MIDP + "antirad", explosion = ModInfo.MIDP + "explosion", fastdig = ModInfo.MIDP + "fastdig", fire = ModInfo.MIDP + "fire", invis = ModInfo.MIDP + "invis", jetpack = ModInfo.MIDP + "jetpack", proficiency = ModInfo.MIDP + "proficiency", protection = ModInfo.MIDP + "protection", tesla = ModInfo.MIDP + "tesla";
	/** Helmet Upgrade */
	public static String autofeed = ModInfo.MIDP + "autofeed", detoxicate = ModInfo.MIDP + "detoxicate", nightvis = ModInfo.MIDP + "nightvis", revealing = ModInfo.MIDP + "revealing", sensor = ModInfo.MIDP + "sensor", solar = ModInfo.MIDP + "solar", waterbr = ModInfo.MIDP + "waterbr";
	/** Universal Upgrade */
	public static String batlv = ModInfo.MIDP + "batlv", batmv = ModInfo.MIDP + "batmv", bathv = ModInfo.MIDP + "bathv", manadc = ModInfo.MIDP + "manadc", runic = ModInfo.MIDP + "runic", visdc = ModInfo.MIDP + "visdc";
	/** Utility Variable */
	public static String electric = ModInfo.MIDP + "electric", shockactive = ModInfo.MIDP + "shockactive", shocktick = ModInfo.MIDP + "shocktick";
	/** Ability Switch Variable */
	public static String shighjump = ModInfo.MIDP + "shighjump", sspeed = ModInfo.MIDP + "sspeed", sinvis = ModInfo.MIDP + "sinvis", snightvis = ModInfo.MIDP + "snightvis", ssensor = ModInfo.MIDP + "ssensor";
	
	public boolean isB = Loader.isModLoaded("Botania"), isTC = Loader.isModLoaded("Thaumcraft");
	
	public float transferMax = 200.0F;
	
	public HighPressureResistantModularArmor(int type) {
		super(RegistrationsList.HPRMA, 0, type);
		this.setCreativeTab(BeyondSpace.bsTab);
		this.setMaxStackSize(1);
		this.setNoRepair();
	}
	/*
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!stack.hasTagCompound()) addNBT(stack);
		if (!world.isRemote) {
			if (player.isSneaking()) {
				if (getArmorID(stack) == 0) {
					if (!stack.stackTagCompound.getBoolean(antigrav)	&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.ANTIGRAV.ordinal())))		{ if (stack.stackTagCompound.getBoolean(electric)) { stack.stackTagCompound.setBoolean(antigrav, true); }	else { needEl(player);	player.inventory.addItemStackToInventory(new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.ANTIGRAV.ordinal())); } }
					if (!stack.stackTagCompound.getBoolean(grav)		&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.GRAV.ordinal())))			stack.stackTagCompound.setBoolean(grav, true);
					if (!stack.stackTagCompound.getBoolean(shockwave)	&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.SHOCKWAVE.ordinal())))		stack.stackTagCompound.setBoolean(shockwave, true);
					if (!stack.stackTagCompound.getBoolean(highjump)	&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.HIGHJUMP.ordinal())))		{ if (stack.stackTagCompound.getBoolean(electric)) { stack.stackTagCompound.setBoolean(highjump, true); }	else { needEl(player);	player.inventory.addItemStackToInventory(new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.HIGHJUMP.ordinal())); } }
					if (!stack.stackTagCompound.getBoolean(kinetic)		&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.KINETIC.ordinal())))		{ if (stack.stackTagCompound.getBoolean(electric)) { stack.stackTagCompound.setBoolean(kinetic, true); }	else { needEl(player);	player.inventory.addItemStackToInventory(new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.KINETIC.ordinal())); } }
					if (!stack.stackTagCompound.getBoolean(stepassist)	&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.STEPASSIST.ordinal())))		stack.stackTagCompound.setBoolean(stepassist, true);
					if (!stack.stackTagCompound.getBoolean(frostwalk)	&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.FROSTWALK.ordinal())))		stack.stackTagCompound.setBoolean(frostwalk, true);
				}
				
				if (getArmorID(stack) == 1) {
					if (!stack.stackTagCompound.getBoolean(speed)		&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.SPEED.ordinal())))			{ if (stack.stackTagCompound.getBoolean(electric)) { stack.stackTagCompound.setBoolean(speed, true); }		else { needEl(player);	player.inventory.addItemStackToInventory(new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.SPEED.ordinal())); } }
				}
				
				if (getArmorID(stack) == 2) {
					if (!stack.stackTagCompound.getBoolean(antirad)		&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.ANTIRAD.ordinal())))		stack.stackTagCompound.setBoolean(antirad, true);
					if (!stack.stackTagCompound.getBoolean(explosion)	&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.EXPLPROF.ordinal())))		stack.stackTagCompound.setBoolean(explosion, true);
					if (!stack.stackTagCompound.getBoolean(fastdig)		&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.FASTDIGGING.ordinal())))	stack.stackTagCompound.setBoolean(fastdig, true);
					if (!stack.stackTagCompound.getBoolean(fire)		&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.FIREPROF.ordinal())))		stack.stackTagCompound.setBoolean(fire, true);
					if (!stack.stackTagCompound.getBoolean(invis)		&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.INVIS.ordinal())))			{ if (stack.stackTagCompound.getBoolean(electric)) { stack.stackTagCompound.setBoolean(invis, true); }		else { needEl(player);	player.inventory.addItemStackToInventory(new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.INVIS.ordinal())); } }
					if (!stack.stackTagCompound.getBoolean(jetpack)		&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.JETPACK.ordinal()))) 		{ if (stack.stackTagCompound.getBoolean(electric)) { stack.stackTagCompound.setBoolean(jetpack, true); }	else { needEl(player);	player.inventory.addItemStackToInventory(new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.JETPACK.ordinal())); } }
					if (!stack.stackTagCompound.getBoolean(proficiency) && ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.MANAPROF.ordinal())))		{ if (isB) { stack.stackTagCompound.setBoolean(proficiency, true); }										else { needB(player);	player.inventory.addItemStackToInventory(new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.MANAPROF.ordinal())); } }
					if (!stack.stackTagCompound.getBoolean(protection)	&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.PROTECTION.ordinal())))		stack.stackTagCompound.setBoolean(protection, true);
					if (!stack.stackTagCompound.getBoolean(tesla)		&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.TESLA.ordinal())))			{ if (stack.stackTagCompound.getBoolean(electric)) { stack.stackTagCompound.setBoolean(tesla, true); }		else { needEl(player);	player.inventory.addItemStackToInventory(new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.TESLA.ordinal())); } }
				}
				
				if (getArmorID(stack) == 3) {
					if (!stack.stackTagCompound.getBoolean(autofeed)	&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.AUTOFEED.ordinal())))		stack.stackTagCompound.setBoolean(autofeed, true);
					if (!stack.stackTagCompound.getBoolean(detoxicate)	&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.DETOXICATOR.ordinal())))	stack.stackTagCompound.setBoolean(detoxicate, true);
					if (!stack.stackTagCompound.getBoolean(nightvis)	&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.NIGHTVIS.ordinal())))		{ if (stack.stackTagCompound.getBoolean(electric)) { stack.stackTagCompound.setBoolean(nightvis, true); }	else { needEl(player); player.inventory.addItemStackToInventory(new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.NIGHTVIS.ordinal())); } }
					if (!stack.stackTagCompound.getBoolean(revealing)	&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.REVEALING.ordinal())))		{ if (isTC) { stack.stackTagCompound.setBoolean(revealing, true); }											else { needTC(player); player.inventory.addItemStackToInventory(new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.REVEALING.ordinal())); } }
					if (!stack.stackTagCompound.getBoolean(sensor)		&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.SENSOR.ordinal())))			stack.stackTagCompound.setBoolean(sensor, true);
					if (!stack.stackTagCompound.getBoolean(solar)		&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.SOLAR.ordinal())))			{ if (stack.stackTagCompound.getBoolean(electric)) { stack.stackTagCompound.setBoolean(solar, true); }		else { needEl(player); player.inventory.addItemStackToInventory(new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.SOLAR.ordinal())); } }
					if (!stack.stackTagCompound.getBoolean(waterbr)		&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.WATERBR.ordinal())))		{ if (stack.stackTagCompound.getBoolean(electric)) { stack.stackTagCompound.setBoolean(waterbr, true); }	else { needEl(player); player.inventory.addItemStackToInventory(new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.WATERBR.ordinal())); } } 
				}
				
				if (!stack.stackTagCompound.getBoolean(batlv)			&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.BATLV.ordinal())))			{ if (!stack.stackTagCompound.getBoolean(electric)) { stack.stackTagCompound.setBoolean(batlv, true); stack.stackTagCompound.setBoolean(electric, true); } else { needEl(player); player.inventory.addItemStackToInventory(new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.BATLV.ordinal())); } }
				if (stack.stackTagCompound.getBoolean(batlv)			&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.BATMV.ordinal())))			{ stack.stackTagCompound.setBoolean(batmv, true); stack.stackTagCompound.setBoolean(batlv, false); }
				if (stack.stackTagCompound.getBoolean(batmv)			&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.BATHV.ordinal())))			{ stack.stackTagCompound.setBoolean(bathv, true); stack.stackTagCompound.setBoolean(batmv, false); }
				
				if (!stack.stackTagCompound.getBoolean(manadc)			&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.MANADISC.ordinal())))		{ if (isB) { stack.stackTagCompound.setBoolean(manadc, true); }												else { needB(player); player.inventory.addItemStackToInventory(new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.MANADISC.ordinal())); } }
				if (!stack.stackTagCompound.getBoolean(runic)			&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.RUNIC.ordinal())))			{ if (isTC) { stack.stackTagCompound.setBoolean(runic, true); }												else { needTC(player); player.inventory.addItemStackToInventory(new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.RUNIC.ordinal())); } }
				if (!stack.stackTagCompound.getBoolean(visdc)			&& ASJUtilities.consumeItemStack(player.inventory, new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.VISDISC.ordinal())))		{ if (isTC) { stack.stackTagCompound.setBoolean(visdc, true); }												else { needTC(player); player.inventory.addItemStackToInventory(new ItemStack(RegistrationsList.armorUpgrade, 1, UpgradeList.VISDISC.ordinal())); } }
			} else {
				super.onItemRightClick(stack, world, player);
			}
		}
        return stack;
    }
	*/
	public void needB(EntityPlayer player) {
		player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.BLUE + StatCollector.translateToLocal("hprmamki.needbotania")));
	}
	
	public void needEl(EntityPlayer player) {
		player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("hprmamki.needelectricity")));
	}
	
	public void needTC(EntityPlayer player) {
		player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("hprmamki.needthaumcraft")));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack is, int armorSlot) {
		ModelBiped armorModel = new HighPressureResistantModularArmorRender(armorSlot);
		if (is.getItem() instanceof HighPressureResistantModularArmor) {
			armorModel = fillingArmorModel(armorModel, entityLiving);
			if (hasColor(is) && armorModel instanceof AdvancedArmorModel)
				((AdvancedArmorModel)armorModel).color = getColor(is);
		}
		return armorModel;
	}

	public static ModelBiped fillingArmorModel(ModelBiped model, EntityLivingBase entityLiving) {
		if (model == null) return model;
		model.bipedHead.showModel = 
		model.bipedHeadwear.showModel = 
		model.bipedBody.showModel = 
		model.bipedRightArm.showModel = 
		model.bipedLeftArm.showModel = 
		model.bipedRightLeg.showModel = 
		model.bipedLeftLeg.showModel = false;
		model.isSneak = entityLiving.isSneaking();
		model.isRiding = entityLiving.isRiding();
		model.isChild = entityLiving.isChild();
		
		ItemStack held_item = entityLiving.getEquipmentInSlot(0);
		if (held_item != null) {
			model.heldItemRight = 1;

			if (entityLiving instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer)entityLiving;

				if (player.getItemInUseCount() > 0) {
					EnumAction enumaction = held_item.getItemUseAction();
					if (enumaction == EnumAction.bow) model.aimedBow = true;
					else if (enumaction == EnumAction.block) model.heldItemRight = 3;
				}

			}

		} else model.heldItemRight = 0;

		if (entityLiving instanceof EntitySkeleton)
			model.aimedBow = ((EntitySkeleton)entityLiving).getSkeletonType() == 1;

		return model;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean what) {
		if (!stack.hasTagCompound()) addNBT(stack);
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			// Boots
			if (stack.stackTagCompound.getBoolean(antigrav))	list.add(StatCollector.translateToLocal("hprmamki.Antigravitational"));
			if (stack.stackTagCompound.getBoolean(grav))		list.add(StatCollector.translateToLocal("hprmamki.Gravitational"));
			if (stack.stackTagCompound.getBoolean(shockwave))	list.add(StatCollector.translateToLocal("hprmamki.Shockwave"));
			if (stack.stackTagCompound.getBoolean(highjump))	list.add((stack.stackTagCompound.getBoolean(shighjump)	? EnumChatFormatting.GREEN : EnumChatFormatting.DARK_RED) + 	
																		 StatCollector.translateToLocal("hprmamki.Highjump"));
			if (stack.stackTagCompound.getBoolean(kinetic))		list.add(StatCollector.translateToLocal("hprmamki.Kinetic"));
			if (stack.stackTagCompound.getBoolean(stepassist))	list.add(StatCollector.translateToLocal("hprmamki.StepAssist"));
			if (stack.stackTagCompound.getBoolean(frostwalk))	list.add(StatCollector.translateToLocal("hprmamki.FrostWalk"));
			// Leggings
			if (stack.stackTagCompound.getBoolean(speed))		list.add((stack.stackTagCompound.getBoolean(sspeed)		? EnumChatFormatting.GREEN : EnumChatFormatting.DARK_RED) + 	
					 													 StatCollector.translateToLocal("hprmamki.Speed"));
			// Chestpalte
			if (stack.stackTagCompound.getBoolean(antirad))		list.add(StatCollector.translateToLocal("hprmamki.Antiradiational"));
			if (stack.stackTagCompound.getBoolean(explosion))	list.add(StatCollector.translateToLocal("hprmamki.ExplosionProtection"));
			if (stack.stackTagCompound.getBoolean(fastdig))		list.add(StatCollector.translateToLocal("hprmamki.FastDigging"));
			if (stack.stackTagCompound.getBoolean(fire))		list.add(StatCollector.translateToLocal("hprmamki.FireProtection"));
			if (stack.stackTagCompound.getBoolean(invis))		list.add((stack.stackTagCompound.getBoolean(sinvis)		? EnumChatFormatting.GREEN : EnumChatFormatting.DARK_RED) + 	
					 													 StatCollector.translateToLocal("hprmamki.Invisibility"));
			if (stack.stackTagCompound.getBoolean(jetpack))		list.add(StatCollector.translateToLocal("hprmamki.Jetpack"));
			if (stack.stackTagCompound.getBoolean(proficiency))	list.add(EnumChatFormatting.AQUA + 
																		 StatCollector.translateToLocal("hprmamki.ManaProficiency"));
			if (stack.stackTagCompound.getBoolean(protection))	list.add(StatCollector.translateToLocal("hprmamki.Protection"));
			if (stack.stackTagCompound.getBoolean(tesla))		list.add(StatCollector.translateToLocal("hprmamki.Tesla"));
			// Helmet
			if (stack.stackTagCompound.getBoolean(autofeed))	list.add(StatCollector.translateToLocal("hprmamki.AutoFeeder"));
			if (stack.stackTagCompound.getBoolean(detoxicate))	list.add(StatCollector.translateToLocal("hprmamki.Detoxicator"));
			if (stack.stackTagCompound.getBoolean(nightvis))	list.add((stack.stackTagCompound.getBoolean(snightvis)	? EnumChatFormatting.GREEN : EnumChatFormatting.DARK_RED) + 	
					 													 StatCollector.translateToLocal("hprmamki.NightVision"));
			if (stack.stackTagCompound.getBoolean(revealing))	list.add(EnumChatFormatting.DARK_PURPLE + 
					 													 StatCollector.translateToLocal("hprmamki.Revealing"));
			if (stack.stackTagCompound.getBoolean(sensor))		list.add((stack.stackTagCompound.getBoolean(ssensor)	? EnumChatFormatting.GREEN : EnumChatFormatting.DARK_RED) + 	
					 													 StatCollector.translateToLocal("hprmamki.SensorGoggles"));
			if (stack.stackTagCompound.getBoolean(solar))		list.add(StatCollector.translateToLocal("hprmamki.SolarPanel"));
			if (stack.stackTagCompound.getBoolean(waterbr))		list.add(StatCollector.translateToLocal("hprmamki.WaterBreathing"));
			// Universal
			if (stack.stackTagCompound.getBoolean(batlv))		list.add(EnumChatFormatting.GOLD + 
																		 StatCollector.translateToLocal("hprmamki.BatteryLV"));
			if (stack.stackTagCompound.getBoolean(batmv))		list.add(EnumChatFormatting.GOLD + 
					 													 StatCollector.translateToLocal("hprmamki.BatteryMV"));
			if (stack.stackTagCompound.getBoolean(bathv))		list.add(EnumChatFormatting.GOLD + 
					 													 StatCollector.translateToLocal("hprmamki.BatteryHV"));
			if (stack.stackTagCompound.getBoolean(manadc))		list.add(EnumChatFormatting.AQUA + 
					 													 StatCollector.translateToLocal("hprmamki.ManaDiscount"));
			if (stack.stackTagCompound.getBoolean(runic))		list.add(EnumChatFormatting.DARK_PURPLE + 
					 													 StatCollector.translateToLocal("hprmamki.RunicShield"));
			if (stack.stackTagCompound.getBoolean(visdc))		list.add(EnumChatFormatting.DARK_PURPLE + 
					 													 StatCollector.translateToLocal("hprmamki.VisDiscount"));
		} else {
			list.add(ASJUtilities.holdShift());
		}
		
		if (stack.stackTagCompound.getBoolean(manadc)) list.add(EnumChatFormatting.AQUA + StatCollector.translateToLocal("hprmamki.manadiscount") + ": 20%");
		if (stack.stackTagCompound.getBoolean(visdc)) list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tc.visdiscount") + ": 10%");
		
		if (stack.hasTagCompound() && stack.stackTagCompound.getBoolean(electric)) {
			String color = "";
		    float joules = getElectricityStored(stack);
		    if (joules <= getMaxElectricityStored(stack) / 3.0F) {
		      color += EnumChatFormatting.DARK_RED;
		    } else if (joules > getMaxElectricityStored(stack) * 2.0F / 3.0F) {
		      color += EnumChatFormatting.DARK_GREEN;
		    } else {
		      color += EnumChatFormatting.GOLD;
		    }
		    list.add(color + EnergyDisplayHelper.getEnergyDisplayS(joules) + "/" + EnergyDisplayHelper.getEnergyDisplayS(getMaxElectricityStored(stack)));
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks, boolean hasScreen, int mouseX, int mouseY) {
		if (!stack.hasTagCompound()) addNBT(stack);
		if (getArmorID(stack) == 3 && stack.stackTagCompound.getBoolean(ssensor) && stack.stackTagCompound.getBoolean(sensor)) {
			OverlaySensorGlasses.renderSensorGlassesMain(stack, player, resolution, partialTicks, hasScreen, mouseX, mouseY);
			OverlaySensorGlasses.renderSensorGlassesValueableBlocks(stack, player, resolution, partialTicks, hasScreen, mouseX, mouseY);
		}
	}
	
	/** @return The armor type: 3 is helmet, 2 is plate, 1 is legs and 0 is boots */
	public static int getArmorID(ItemStack stack) {
        int i = EntityLiving.getArmorPosition(stack) - 1;
        return i;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if (!stack.hasTagCompound()) addNBT(stack);
		if (stack.stackTagCompound.getBoolean(HighPressureResistantModularArmor.sspeed) && stack.stackTagCompound.getBoolean(HighPressureResistantModularArmor.speed) && stack.stackTagCompound.getBoolean(HighPressureResistantModularArmor.electric) && CommonEventHandler.isEnoughDurability(stack, 1)) {
			player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 10, 2));
			if ((player.motionX != 0 || player.motionZ != 0)) CommonEventHandler.dischargeStack(stack, 1);
		}
		
		/** Freezing water and lava in boots */
		if(player.isSneaking() && (player.getCurrentArmor(0) != null) && (player.getCurrentArmor(0).getItem() == RegistrationsList.highPressureResistantModularArmorBoot && player.getCurrentArmor(0).stackTagCompound.getBoolean(HighPressureResistantModularArmor.frostwalk))){ 
			if (player.worldObj.getBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ)) == Blocks.water && player.worldObj.getBlockMetadata(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ)) == 0) { 
				player.worldObj.setBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ), Blocks.ice); 
			} else if (player.worldObj.getBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ)) == Blocks.lava && player.worldObj.getBlockMetadata(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ)) == 0) { 
				player.worldObj.setBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ), Blocks.obsidian); 
			} 

			if (player.worldObj.getBlock(MathHelper.floor_double(player.posX) + 1, MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ)) == Blocks.water && player.worldObj.getBlockMetadata(MathHelper.floor_double(player.posX + 1), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ)) == 0) { 
				player.worldObj.setBlock(MathHelper.floor_double(player.posX) + 1, MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ), Blocks.ice); 
			} else if (player.worldObj.getBlock(MathHelper.floor_double(player.posX) + 1, MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ)) == Blocks.lava && player.worldObj.getBlockMetadata(MathHelper.floor_double(player.posX + 1), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ)) == 0) { 
				player.worldObj.setBlock(MathHelper.floor_double(player.posX) + 1, MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ), Blocks.obsidian); 
			} 

			if (player.worldObj.getBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ) + 1) == Blocks.water && player.worldObj.getBlockMetadata(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ + 1)) == 0) { 
				player.worldObj.setBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ) + 1, Blocks.ice); 
			} else if (player.worldObj.getBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ) + 1) == Blocks.lava && player.worldObj.getBlockMetadata(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ + 1)) == 0) { 
				player.worldObj.setBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ) + 1, Blocks.obsidian); 
			} 

			if (player.worldObj.getBlock(MathHelper.floor_double(player.posX) - 1, MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ)) == Blocks.water && player.worldObj.getBlockMetadata(MathHelper.floor_double(player.posX - 1), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ)) == 0) { 
				player.worldObj.setBlock(MathHelper.floor_double(player.posX) - 1, MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ), Blocks.ice); 
			} else if (player.worldObj.getBlock(MathHelper.floor_double(player.posX) - 1, MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ)) == Blocks.lava && player.worldObj.getBlockMetadata(MathHelper.floor_double(player.posX - 1), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ)) == 0) { 
				player.worldObj.setBlock(MathHelper.floor_double(player.posX) - 1, MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ), Blocks.obsidian); 
			} 

			if (player.worldObj.getBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ) - 1) == Blocks.water && player.worldObj.getBlockMetadata(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ - 1)) == 0) { 
				player.worldObj.setBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ) - 1, Blocks.ice); 
			} else if (player.worldObj.getBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ) - 1) == Blocks.lava && player.worldObj.getBlockMetadata(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ - 1)) == 0) { 
				player.worldObj.setBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ) - 1, Blocks.obsidian); 
			} 
		}
		
		if (stack.stackTagCompound.getBoolean(HighPressureResistantModularArmor.snightvis) && stack.stackTagCompound.getBoolean(HighPressureResistantModularArmor.nightvis) && stack.stackTagCompound.getBoolean(HighPressureResistantModularArmor.electric) && CommonEventHandler.isEnoughDurability(stack, 0.01F)) {
			CommonEventHandler.dischargeStack(stack, 1);
			player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 210, -1));
		} else if (!stack.stackTagCompound.getBoolean(HighPressureResistantModularArmor.snightvis) && stack.stackTagCompound.getBoolean(HighPressureResistantModularArmor.nightvis)) {
			player.removePotionEffect(Potion.nightVision.id);
		}
		
		if (player.isInWater() && stack.stackTagCompound.getBoolean(HighPressureResistantModularArmor.waterbr) && stack.stackTagCompound.getBoolean(HighPressureResistantModularArmor.electric) && CommonEventHandler.isEnoughDurability(stack, 0.01F)) {
			CommonEventHandler.dischargeStack(stack, 1);
			player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 10, -1));
		}
		
		if (stack.stackTagCompound.getBoolean(HighPressureResistantModularArmor.autofeed) && player.getFoodStats().needFood() && CommonEventHandler.isEnoughDurability(stack, 1)) {
			player.getFoodStats().setFoodLevel(20);
			CommonEventHandler.hurtArmor(stack, player, 1);
		}
		
		if (stack.stackTagCompound.getBoolean(HighPressureResistantModularArmor.stepassist)) {
			player.stepHeight = 1.5F;
		}
		
		if (stack.stackTagCompound.getBoolean(HighPressureResistantModularArmor.detoxicate)) {
			detoxicate(player);
		}
		
		if(stack.stackTagCompound.getBoolean(HighPressureResistantModularArmor.sinvis) && stack.stackTagCompound.getBoolean(HighPressureResistantModularArmor.invis) && stack.stackTagCompound.getBoolean(HighPressureResistantModularArmor.electric) && CommonEventHandler.isEnoughDurability(stack, 0.01F)){
			CommonEventHandler.dischargeStack(stack, 1); 
			player.addPotionEffect(new PotionEffect(Potion.invisibility.id, 10, -1));
		}
		if (stack.stackTagCompound.getBoolean(HighPressureResistantModularArmor.electric) && stack.stackTagCompound.getBoolean(HighPressureResistantModularArmor.kinetic) && ((player.motionX != 0 || player.motionZ != 0))) {
			CommonEventHandler.rechargeStack(player.getCurrentArmor(0), 1F);
		}
			
		if (player.worldObj.isDaytime() && player.worldObj.canBlockSeeTheSky(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) + 2, MathHelper.floor_double(player.posZ)) && stack.stackTagCompound.getBoolean(HighPressureResistantModularArmor.electric) && stack.stackTagCompound.getBoolean(HighPressureResistantModularArmor.solar)) {
			CommonEventHandler.rechargeStack(player.getCurrentArmor(3), 1F);
		}
    }
	
	public void detoxicate(EntityPlayer player) {
		Collection effects = player.getActivePotionEffects();
		if (effects.size() > 0) {
			ArrayList<Potion> badPotions = new ArrayList();
			for (Object effect : effects) {
				if ((effect instanceof PotionEffect)) {
					PotionEffect potion = (PotionEffect) effect;
					if (ASJUtilities.isBadPotion(Potion.potionTypes[potion.getPotionID()])) {
						badPotions.add(Potion.potionTypes[potion.getPotionID()]);
					}
				}
			}
			if (badPotions.size() > 0) {
				for (Potion potion : badPotions) {
					if (CommonEventHandler.isEnoughDurability(player.getCurrentArmor(3), 3)) {
						if (potion.id == GSPotions.radiation.id) continue;
						CommonEventHandler.hurtArmor(player.getCurrentArmor(3), player, 3);
						player.removePotionEffect(potion.id);
					}
				}
			}
		}
	}
	
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		if (!stack.hasTagCompound()) addNBT(stack);
		setElectricity(stack, 0.0F);
	}
	
	@Override
	public int gravityOverrideIfLow(EntityPlayer player) {
		if (player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem() == RegistrationsList.highPressureResistantModularArmorBoot && !player.getCurrentArmor(0).hasTagCompound())  addNBT(player.getCurrentArmor(0));
		return (player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem() == RegistrationsList.highPressureResistantModularArmorBoot && player.getCurrentArmor(0).stackTagCompound.getBoolean(grav)) ? 100 : 0;
	}

	@Override
	public int gravityOverrideIfHigh(EntityPlayer player) {
		if (player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem() == RegistrationsList.highPressureResistantModularArmorBoot && !player.getCurrentArmor(0).hasTagCompound())  addNBT(player.getCurrentArmor(0));
		return (player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem() == RegistrationsList.highPressureResistantModularArmorBoot && player.getCurrentArmor(0).stackTagCompound.getBoolean(antigrav)) ? 100 : 0;
	}

	@Optional.Method(modid = "Thaumcraft")
	@Override
	public boolean showNodes(ItemStack itemstack, EntityLivingBase entity) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (player.getCurrentArmor(3) != null && player.getCurrentArmor(3).getItem() == RegistrationsList.highPressureResistantModularArmorHead && !player.getCurrentArmor(3).hasTagCompound()) addNBT(player.getCurrentArmor(3));
			return (player.getCurrentArmor(3) != null && player.getCurrentArmor(3).getItem() == RegistrationsList.highPressureResistantModularArmorHead && player.getCurrentArmor(3).stackTagCompound.getBoolean(revealing));
		}
		return false;
	}
	
	@Optional.Method(modid = "Thaumcraft")
	@Override
	public boolean showIngamePopups(ItemStack itemstack, EntityLivingBase entity) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (player.getCurrentArmor(3) != null && player.getCurrentArmor(3).getItem() == RegistrationsList.highPressureResistantModularArmorHead && !player.getCurrentArmor(3).hasTagCompound()) addNBT(player.getCurrentArmor(3));
			return (player.getCurrentArmor(3) != null && player.getCurrentArmor(3).getItem() == RegistrationsList.highPressureResistantModularArmorHead && player.getCurrentArmor(3).stackTagCompound.getBoolean(revealing));
		}
		return false;
	}

	@Optional.Method(modid = "Thaumcraft")
	@Override
	public int getVisDiscount(ItemStack stack, EntityPlayer player, Aspect aspect) {
		if (!stack.hasTagCompound()) addNBT(stack);
		return stack.stackTagCompound.getBoolean(visdc) ? 10 : 0;
	}

	@Optional.Method(modid = "Thaumcraft")
	@Override
	public float getDiscount(ItemStack stack, int slot, EntityPlayer player) {
		if (!stack.hasTagCompound()) addNBT(stack);
		return stack.stackTagCompound.getBoolean(manadc) ? 0.2F : 0.0F;
	}

	@Optional.Method(modid = "Thaumcraft")
	@Override
	public int getRunicCharge(ItemStack stack) {
		if (!stack.hasTagCompound()) addNBT(stack);
		return stack.stackTagCompound.getBoolean(runic) ? 8 : 0;
	}

	@Optional.Method(modid = "Botania")
	@Override
	public boolean shouldGiveProficiency(ItemStack stack, int i, EntityPlayer player) {
		if (!stack.hasTagCompound()) addNBT(stack);
		return stack.stackTagCompound.getBoolean(proficiency);
	}

	@Override
	public float recharge(ItemStack stack, float energy, boolean doRecharge) {
		if (!stack.hasTagCompound()) addNBT(stack);
		if (stack.stackTagCompound.getBoolean(electric)) {
			float rejectedElectricity = Math.max(getElectricityStored(stack) + energy - getMaxElectricityStored(stack), 0.0F);
			float energyToReceive = energy - rejectedElectricity;
			
			if (energyToReceive > this.transferMax) {
				rejectedElectricity += energyToReceive - this.transferMax;
				energyToReceive = this.transferMax;
			}
			
			if (doRecharge) {
				setElectricity(stack, getElectricityStored(stack) + energyToReceive);
			}
			
			return energyToReceive;
		} else {
			return 0;
		}
	}

	@Override
	public float discharge(ItemStack stack, float energy, boolean doDischarge) {
		if (!stack.hasTagCompound()) addNBT(stack);
		if (stack.stackTagCompound.getBoolean(electric)) {
			float energyToTransfer = Math.min(getElectricityStored(stack), energy);
			if (doDischarge) {
				setElectricity(stack, getElectricityStored(stack) - energyToTransfer);
			}
			return energyToTransfer;
		} else {
			return 0;
		}
	}

	@Override
	public float getElectricityStored(ItemStack stack) {
		if (!stack.hasTagCompound()) addNBT(stack);
		if (stack.stackTagCompound.getBoolean(electric)) {
			float energyStored = 0.0F;
			if (stack.getTagCompound().hasKey("electricity")) {
				NBTBase obj = stack.getTagCompound().getTag("electricity");
				if ((obj instanceof NBTTagDouble)) {
					energyStored = ((NBTTagDouble) obj).func_150288_h();
				} else if ((obj instanceof NBTTagFloat)) {
					energyStored = ((NBTTagFloat) obj).func_150288_h();
				}
			}
			
			stack.setItemDamage((int)(stack.getMaxDamage() - (energyStored / getMaxElectricityStored(stack) * stack.getMaxDamage())));
			return energyStored;
		} else {
			return 0;
		}
	}

	@Override
	public float getMaxElectricityStored(ItemStack stack) {
		if (!stack.hasTagCompound()) addNBT(stack);
		return stack.stackTagCompound.getBoolean(bathv) ? 800000 : stack.stackTagCompound.getBoolean(batmv) ? 400000 : stack.stackTagCompound.getBoolean(batlv) ? 200000 : 0;
	}

	@Override
	public void setElectricity(ItemStack stack, float joules) {
		if (!stack.hasTagCompound()) addNBT(stack);
		if (stack.stackTagCompound.getBoolean(electric)) {
			float electricityStored = Math.max(Math.min(joules, getMaxElectricityStored(stack)), 0.0F);
			stack.getTagCompound().setFloat("electricity", electricityStored);
			
			stack.setItemDamage((int)(stack.getMaxDamage() - (electricityStored / getMaxElectricityStored(stack) * stack.getMaxDamage())));
		} else {
			return;
		}
	}

	@Override
	public float getTransfer(ItemStack stack) {
		if (!stack.hasTagCompound()) addNBT(stack);
		if (stack.stackTagCompound.getBoolean(electric)) {
			return Math.min(this.transferMax, getMaxElectricityStored(stack) - getElectricityStored(stack));
		} else {
			return 0;
		}
	}

	@Override
	public int getTierGC(ItemStack stack) {
		return 1;
	}
	
	/**
	 * Adds needed NBT data to the armor
	 */
	private void addNBT(ItemStack stack) {
		stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setBoolean(shighjump, false);
		stack.stackTagCompound.setBoolean(sinvis, false);
		stack.stackTagCompound.setBoolean(snightvis, false);
		stack.stackTagCompound.setBoolean(ssensor, false);
		stack.stackTagCompound.setBoolean(sspeed, false);
	}
}
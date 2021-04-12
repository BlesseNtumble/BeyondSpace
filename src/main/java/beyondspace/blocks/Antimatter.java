package beyondspace.blocks;

import java.util.Random;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import beyondspace.utils.RegistrationsList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Antimatter extends Block {

	public Antimatter() {
		super(Material.portal);
		this.setBlockName("Antimatter");
        this.setBlockTextureName(ModInfo.MODID + ":Antimatter");
        this.setBlockUnbreakable();
        this.setLightLevel(1.0F);
        this.setLightOpacity(0);
        this.setCreativeTab(BeyondSpace.gaTab);
        this.setStepSound(soundTypeSnow);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean isNormalCube() {
		return false;
	}
    
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
    	if (!world.isRemote) {
    		if (world.getBlock(x + 1, y, z) != RegistrationsList.antimatter || world.getBlock(x + 1, y, z) != Blocks.air ||
    			world.getBlock(x - 1, y, z) != RegistrationsList.antimatter || world.getBlock(x - 1, y, z) != Blocks.air ||
    			world.getBlock(x, y + 1, z) != RegistrationsList.antimatter || world.getBlock(x, y + 1, z) != Blocks.air ||
    			world.getBlock(x, y - 1, z) != RegistrationsList.antimatter || world.getBlock(x, y - 1, z) != Blocks.air ||
    			world.getBlock(x, y, z + 1) != RegistrationsList.antimatter || world.getBlock(x, y, z + 1) != Blocks.air ||
    			world.getBlock(x, y, z - 1) != RegistrationsList.antimatter || world.getBlock(x, y, z - 1) != Blocks.air) {
				world.newExplosion(null, x, y, z, 50.0F, world.getGameRules().getGameRuleBooleanValue("mobGriefing"), world.getGameRules().getGameRuleBooleanValue("mobGriefing"));
				world.setBlockToAir(x, y, z);
    		}
		}
    }
	
	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
		if (!world.isRemote) {
			world.newExplosion(null, x, y, z, 50.0F, world.getGameRules().getGameRuleBooleanValue("mobGriefing"), world.getGameRules().getGameRuleBooleanValue("mobGriefing"));
			world.setBlockToAir(x, y, z);
		}
	}
	
	public int quantityDropped(Random rand) {
        return 0;
    }

    public Item getItemDropped(int meta, Random rand, int fortune) {
        return null;
    }
    
    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
    	return false;
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
    	if (!world.isRemote) {
			world.newExplosion(null, x, y, z, 50.0F, world.getGameRules().getGameRuleBooleanValue("mobGriefing"), world.getGameRules().getGameRuleBooleanValue("mobGriefing"));
			world.setBlockToAir(x, y, z);
		}
    	return false;
    }
    
    @Override
    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
    	if (!world.isRemote) {
			world.newExplosion(null, x, y, z, 50.0F, world.getGameRules().getGameRuleBooleanValue("mobGriefing"), world.getGameRules().getGameRuleBooleanValue("mobGriefing"));
			world.setBlockToAir(x, y, z);
		}
    }
    
    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
    	if (!world.isRemote) {
			world.newExplosion(null, x, y, z, 50.0F, world.getGameRules().getGameRuleBooleanValue("mobGriefing"), world.getGameRules().getGameRuleBooleanValue("mobGriefing"));
			world.setBlockToAir(x, y, z);
		}
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getBlockColor() {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta) {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        return 0;
    }
    
    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
    	if (!world.isRemote) {
			world.newExplosion(null, x, y, z, 50.0F, world.getGameRules().getGameRuleBooleanValue("mobGriefing"), world.getGameRules().getGameRuleBooleanValue("mobGriefing"));
			world.setBlockToAir(x, y, z);
		}
    }
    
    @Override
    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
    	if (!world.isRemote) {
			world.newExplosion(null, x, y, z, 50.0F, world.getGameRules().getGameRuleBooleanValue("mobGriefing"), world.getGameRules().getGameRuleBooleanValue("mobGriefing"));
			world.setBlockToAir(x, y, z);
		}
    	super.harvestBlock(world, player, x, y, z, meta);
    }
    
    @Override
    public boolean canSilkHarvest() {
    	return false;
    }
    
    @Override
    public ItemStack createStackedBlock(int meta) {
    	return null;
    }
    
    @Override
    public int getMobilityFlag() {
    	return 2;
    }
    
    @Override
    public void onFallenUpon(World world, int x, int y, int z, Entity entity, float distance) {
    	if (!world.isRemote) {
			world.newExplosion(null, x, y, z, 50.0F, world.getGameRules().getGameRuleBooleanValue("mobGriefing"), world.getGameRules().getGameRuleBooleanValue("mobGriefing"));
			world.setBlockToAir(x, y, z);
		}
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z) {
        return null;
    }
    
    @Override
    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
    	return false;
    }
}
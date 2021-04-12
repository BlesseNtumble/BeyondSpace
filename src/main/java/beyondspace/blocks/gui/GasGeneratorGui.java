package beyondspace.blocks.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import beyondspace.ModInfo;
import beyondspace.blocks.container.GasGeneratorContainer;
import beyondspace.blocks.tileentity.GasGeneratorTileEntity;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public class GasGeneratorGui extends GuiContainerGC {
	
	private static final ResourceLocation texture = new ResourceLocation(ModInfo.MODID + ":" + "textures/gui/gasGeneratorGui.png");
    private final GasGeneratorTileEntity tileEntity;
    private GuiButton buttonEnable;
    private GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 139, (this.height - this.ySize) / 2 + 6, 9, 56, new ArrayList<String>(), this.width, this.height, this);
    private GuiElementInfoRegion gasTankRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 22, (this.height - this.ySize) / 2 + 17, 16, 38, new ArrayList<String>(), this.width, this.height, this);
    
    public GasGeneratorGui(InventoryPlayer par1InventoryPlayer, GasGeneratorTileEntity tileEntity) {
        super(new GasGeneratorContainer(par1InventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
        this.ySize = 168;
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton) {
        switch (par1GuiButton.id) {
        case 0:
            GalacticraftCore.packetPipeline.sendToServer(new PacketSimple(EnumSimplePacket.S_UPDATE_DISABLEABLE_BUTTON, new Object[] { this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord, 0 }));
            break;
        }
    }

    @Override
    public void initGui(){
        super.initGui();
        //gas
        this.gasTankRegion.xPosition = (this.width - this.xSize) / 2 + 22;
        this.gasTankRegion.yPosition = (this.height - this.ySize) / 2 + 17;
        this.gasTankRegion.parentWidth = this.width;
        this.gasTankRegion.parentHeight = this.height;
        this.infoRegions.add(this.gasTankRegion);
        //gas end
        List<String> electricityDesc = new ArrayList<String>();
        electricityDesc.add(GCCoreUtil.translate("gui.energyStorage.desc.0"));
        electricityDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.energyStorage.desc.1") + ((int) Math.floor(this.tileEntity.getEnergyStoredGC()) + " / " + (int) Math.floor(this.tileEntity.getMaxEnergyStoredGC())));
        this.electricInfoRegion.tooltipStrings = electricityDesc;
        this.electricInfoRegion.xPosition = (this.width - this.xSize) / 2 + 139;
        this.electricInfoRegion.yPosition = (this.height - this.ySize) / 2 + 6;
        this.electricInfoRegion.parentWidth = this.width;
        this.electricInfoRegion.parentHeight = this.height;
        this.infoRegions.add(this.electricInfoRegion);
        List<String> batterySlotDesc = new ArrayList<String>();
        batterySlotDesc.add(GCCoreUtil.translate("gui.batterySlot.desc.0"));
        batterySlotDesc.add(GCCoreUtil.translate("gui.batterySlot.desc.1"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 151, (this.height - this.ySize) / 2 + 82, 18, 18, batterySlotDesc, this.width, this.height, this));
        //atmospheric valve
        List<String> atmosphericValveDesc = new ArrayList<String>();
        atmosphericValveDesc.add(GCCoreUtil.translate("gui.atmosphericValveDesc.0"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 47, (this.height - this.ySize) / 2 + 17, 18, 18, atmosphericValveDesc, this.width, this.height, this));
        //button
        this.buttonList.add(this.buttonEnable = new GuiButton(0, this.width / 2 - 36, this.height / 2 - 25, 72, 20, GCCoreUtil.translate("gui.button.enable.name")));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        int offsetY = 35;
        World world = this.tileEntity.getWorldObj();
        //button
        this.buttonEnable.enabled = this.tileEntity.disableCooldown == 0;
        this.buttonEnable.displayString = !this.tileEntity.getDisabled(0) ? GCCoreUtil.translate("gui.button.disable.name") : GCCoreUtil.translate("gui.button.enable.name");
        //inventory
        String inventory = this.tileEntity.getInventoryName();
        this.fontRendererObj.drawString(inventory, this.xSize / 2 - this.fontRendererObj.getStringWidth(inventory) / 2, 7, 4210752);
        //status
        String status = GCCoreUtil.translate("gui.message.status.name") + ": " + this.getStatus();
        this.fontRendererObj.drawString(status, this.xSize / 2 - this.fontRendererObj.getStringWidth(status) / 2 + 10, 45 + 23 - 46 + offsetY - 10, 4210752);
        //container
        this.fontRendererObj.drawString(GCCoreUtil.translate("container.inventory"), 8, this.ySize - 94, 4210752);
        //gen
        this.fontRendererObj.drawString(GCCoreUtil.translate("gui.generate.name")+" "+ gen(), this.xSize / 2 - this.fontRendererObj.getStringWidth("gui.generate.name"+" "+ gen()) / 2 + 10, 45 + 23 - 46 + offsetY - 20, 4210752);
    }

    private String gen(){
    	if(this.tileEntity.gas.getFluidAmount() > 0 && this.tileEntity.gas.getFluidAmount() < 500){
    		return EnergyDisplayHelper.getEnergyDisplayS(5);
    	}
    	if(this.tileEntity.gas.getFluidAmount() >= 500 && this.tileEntity.gas.getFluidAmount() < 800){
    		return EnergyDisplayHelper.getEnergyDisplayS(this.tileEntity.min_gen);
    	}
    	if(this.tileEntity.gas.getFluidAmount() >= 800 && this.tileEntity.gas.getFluidAmount() < 1000){
    		return EnergyDisplayHelper.getEnergyDisplayS(70);
    	}
    	if(this.tileEntity.gas.getFluidAmount() >= 1000 && this.tileEntity.gas.getFluidAmount() <= this.tileEntity.gas.getCapacity()){
    		return EnergyDisplayHelper.getEnergyDisplayS(this.tileEntity.max_gen);
    	}
    	return EnergyDisplayHelper.getEnergyDisplayS(0);
    }
    private String getStatus() {
        if (this.tileEntity.getDisabled(0)){
            return EnumColor.ORANGE + GCCoreUtil.translate("gui.status.disabled.name");
        }
        if(this.tileEntity.gas.getFluidAmount() <= 0){
        	return EnumColor.DARK_RED + GCCoreUtil.translate("gui.status.naturegasmissing.name");
        }
        if(this.tileEntity.gas.getFluidAmount() > 0){
        	return EnumColor.DARK_GREEN + GCCoreUtil.translate("gui.status.collectingenergy.name");
        }
        return EnumColor.ORANGE + GCCoreUtil.translate("gui.status.unknown.name");
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        final int width = (this.width - this.xSize) / 2;
        final int height = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(width, height, 0, 0, this.xSize, this.ySize);

        List<String> electricityDesc = new ArrayList<String>();
        EnergyDisplayHelper.getEnergyDisplayTooltip(this.tileEntity.getEnergyStoredGC(), this.tileEntity.getMaxEnergyStoredGC(), electricityDesc);
        this.electricInfoRegion.tooltipStrings = electricityDesc;
        
		int displayInt = this.tileEntity.getScaledGasLevel(38);
		int gasType = this.tileEntity.gasType; // 0 for methane, 1 for // oxygen, 2 for atmospheric // gases
		if (gasType > 2) {
			gasType = 2;
		}
		if (gasType >= 0) {
			this.drawTexturedModalRect(width + 22, height + 55 - displayInt, 176, 0, 16, displayInt);
		}
		this.addToolTips();
        if (this.tileEntity.getEnergyStoredGC() > 0){
            this.drawTexturedModalRect(width + 139, height + 64, 199, 0, 9, 8);
        }
        int electricalInt = this.tileEntity.getScaledElecticalLevel(54);
        this.drawTexturedModalRect(width + 140, height + 54 + 7 - electricalInt, 192, 0, 7, Math.min(this.tileEntity.getScaledElecticalLevel(54), 54));
    }
    
    private void addToolTips() {
    	List<String> gasTankDesc = new ArrayList<String>();
        gasTankDesc.add(GCCoreUtil.translate("gui.gasTankCompressed.desc.0"));
        FluidStack gasTankContents = this.tileEntity.gas != null ? this.tileEntity.gas.getFluid() : null;
		if (gasTankContents != null) {
			String gasname = GCCoreUtil.translate("gui.natureGas.name");
			gasTankDesc.add(gasname);
		}
		int gasLevel = gasTankContents != null ? gasTankContents.amount : 0;
		int gasCapacity = this.tileEntity.gas != null ? this.tileEntity.gas.getCapacity() : 0;
		gasTankDesc.add(EnumColor.YELLOW + " " + gasLevel + " / " + gasCapacity);
		this.gasTankRegion.tooltipStrings = gasTankDesc;
    }
}
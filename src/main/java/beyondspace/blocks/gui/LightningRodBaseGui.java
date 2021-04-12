package beyondspace.blocks.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import beyondspace.ModInfo;
import beyondspace.blocks.container.LightningRodBaseContainer;
import beyondspace.blocks.tileentity.LightningRodBaseTileEntity;
import beyondspace.utils.RegistrationsList;
import galaxyspace.api.dimension.ILightning;
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

public class LightningRodBaseGui extends GuiContainerGC
{
	private static final ResourceLocation texture = new ResourceLocation(ModInfo.MODID + ":" + "textures/gui/lightningRodGui.png");
    private final LightningRodBaseTileEntity tileEntity;
    private GuiButton buttonEnable;
    private GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 107, (this.height - this.ySize) / 2 + 101, 56, 9, new ArrayList<String>(), this.width, this.height, this);

    public LightningRodBaseGui(InventoryPlayer par1InventoryPlayer, LightningRodBaseTileEntity tileEntity)
    {
        super(new LightningRodBaseContainer(par1InventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
        this.ySize = 201;
        this.xSize = 176;
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        switch (par1GuiButton.id)
        {
        case 0:
            GalacticraftCore.packetPipeline.sendToServer(new PacketSimple(EnumSimplePacket.S_UPDATE_DISABLEABLE_BUTTON, new Object[] { this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord, 0 }));
            break;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui()
    {
        super.initGui();
        List<String> electricityDesc = new ArrayList<String>();
        electricityDesc.add(GCCoreUtil.translate("gui.energyStorage.desc.0"));
        electricityDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.energyStorage.desc.1") + ((int) Math.floor(this.tileEntity.getEnergyStoredGC()) + " / " + (int) Math.floor(this.tileEntity.getMaxEnergyStoredGC())));
        this.electricInfoRegion.tooltipStrings = electricityDesc;
        this.electricInfoRegion.xPosition = (this.width - this.xSize) / 2 + 96;
        this.electricInfoRegion.yPosition = (this.height - this.ySize) / 2 + 24;
        this.electricInfoRegion.parentWidth = this.width;
        this.electricInfoRegion.parentHeight = this.height;
        this.infoRegions.add(this.electricInfoRegion);
        List<String> batterySlotDesc = new ArrayList<String>();
        batterySlotDesc.add(GCCoreUtil.translate("gui.batterySlot.desc.0"));
        batterySlotDesc.add(GCCoreUtil.translate("gui.batterySlot.desc.1"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 151, (this.height - this.ySize) / 2 + 82, 18, 18, batterySlotDesc, this.width, this.height, this));
        //base
        List<String> lightningRodBaseDesc = new ArrayList<String>();
        lightningRodBaseDesc.add(GCCoreUtil.translate("tile.LightningRodBase.name"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 14, (this.height - this.ySize) / 2 + 72, 27, 27, lightningRodBaseDesc, this.width, this.height, this));
        //mid
        List<String> lightningRodMidDesc = new ArrayList<String>();
        lightningRodMidDesc.add(GCCoreUtil.translate("tile.LightningRodMid.name"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 13, (this.height - this.ySize) / 2 + 45, 29, 27, lightningRodMidDesc, this.width, this.height, this));
        //top
        List<String> lightningRodTopDesc = new ArrayList<String>();
        lightningRodTopDesc.add(GCCoreUtil.translate("tile.LightningRodTop.name"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 13, (this.height - this.ySize) / 2 + 17, 29, 28, lightningRodTopDesc, this.width, this.height, this));
        //jupiterInfo
        List<String> jupiterDesc = new ArrayList<String>();
        jupiterDesc.add(GCCoreUtil.translate("gui.jupiterInfo.0.name"));
        jupiterDesc.add(GCCoreUtil.translate("gui.jupiterInfo.1.name"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 60, (this.height - this.ySize) / 2 + 22, 15, 15, jupiterDesc, this.width, this.height, this));
      //multiplier info
        List<String> multiplierDesc = new ArrayList<String>();
        multiplierDesc.add(GCCoreUtil.translate("gui.multiplierInfo.0.name"));
        multiplierDesc.add(GCCoreUtil.translate("gui.multiplierInfo.1.name"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 70, (this.height - this.ySize) / 2 + 45, 60, 15, multiplierDesc, this.width, this.height, this));
        //button
        this.buttonList.add(this.buttonEnable = new GuiButton(0, this.width / 2 - 36, this.height / 2 - 19, 72, 20, GCCoreUtil.translate("gui.button.enable.name")));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
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
        this.fontRendererObj.drawString(status, this.xSize / 2 - this.fontRendererObj.getStringWidth(status) / 2 + 10, 45 + 23 - 46 + offsetY, 4210752);
        //multiplier
        String multiplier = GCCoreUtil.translate("gui.multiplier.name") + ": " + (world.provider instanceof ILightning ? GCCoreUtil.translate("1.25") : GCCoreUtil.translate("1.0"));
        this.fontRendererObj.drawString(multiplier, this.xSize / 2 - this.fontRendererObj.getStringWidth(multiplier) / 2 + 10, 34 + 23 - 46 + offsetY, 4210752);
        //container
        this.fontRendererObj.drawString(GCCoreUtil.translate("container.inventory"), 8, this.ySize - 94, 4210752);
    }

    private String getStatus()
    {
        if (this.tileEntity.getDisabled(0)){
            return EnumColor.ORANGE + GCCoreUtil.translate("gui.status.disabled.name");
        }
        if (this.tileEntity.getEnergyStoredGC() > 0){
            return EnumColor.DARK_GREEN + GCCoreUtil.translate("gui.status.collectingenergy.name");
        }
        if(this.tileEntity.getEnergyStoredGC() <= 0){
        	return EnumColor.DARK_RED + GCCoreUtil.translate("gui.status.notGenerating.name");
        }
        return EnumColor.ORANGE + GCCoreUtil.translate("gui.status.unknown.name");
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        final int width = (this.width - this.xSize) / 2;
        final int height = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(width, height, 0, 0, this.xSize, this.ySize);

        List<String> electricityDesc = new ArrayList<String>();
        EnergyDisplayHelper.getEnergyDisplayTooltip(this.tileEntity.getEnergyStoredGC(), this.tileEntity.getMaxEnergyStoredGC(), electricityDesc);
        this.electricInfoRegion.tooltipStrings = electricityDesc;

        if (this.tileEntity.getEnergyStoredGC() > 0){
            this.drawTexturedModalRect(width + 83, height + 24, 176, 0, 11, 10);
        }
        int x = this.tileEntity.xCoord;
        int y = this.tileEntity.yCoord;
        int z = this.tileEntity.zCoord;
        if(this.tileEntity.getWorldObj().getBlock(x, y, z) == RegistrationsList.lightningrodBase){
        	this.drawTexturedModalRect(width + 14, height + 72, 177, 80, 27, 27);
        }
        if(this.tileEntity.getWorldObj().getBlock(x, y + 1, z) == RegistrationsList.lightningrodMid){
        	this.drawTexturedModalRect(width + 13, height + 45, 176, 53, 29, 27);
        }
        if(this.tileEntity.getWorldObj().getBlock(x, y + 2, z) == RegistrationsList.lightningrodTop){
        	this.drawTexturedModalRect(width + 13, height + 17, 176, 25, 29, 28);
        }
        if(this.tileEntity.getWorldObj().provider instanceof ILightning){
            this.drawTexturedModalRect(width + 61, height + 23, 176, 11, 13, 13);
        } 
        this.drawTexturedModalRect(width + 97, height + 25, 187, 0, Math.min(this.tileEntity.getScaledElecticalLevel(54), 54), 7);
    }
}
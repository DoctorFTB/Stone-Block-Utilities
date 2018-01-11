package ftblag.stoneblockutilities.gui;

import org.lwjgl.opengl.GL11;

import ftblag.stoneblockutilities.tileentity.StoneWorkbenchTileEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiWB extends GuiContainer {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    public GuiWB(EntityPlayer player, StoneWorkbenchTileEntity teIn) {
        super(new ContainerWB(player, teIn));
        xSize = 176;
        ySize = 166;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        mc.renderEngine.bindTexture(new ResourceLocation("minecraft", "textures/gui/container/crafting_table.png"));
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
}
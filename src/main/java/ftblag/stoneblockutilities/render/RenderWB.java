package ftblag.stoneblockutilities.render;

import ftblag.stoneblockutilities.tileentity.StoneWorkbenchTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWB extends TileEntitySpecialRenderer<StoneWorkbenchTileEntity> {

    Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void render(StoneWorkbenchTileEntity te, double x1, double y1, double z1, float p, int d, float a) {
        if (te.getWorld().getBlockState(te.getPos().up()).getBlock() != Blocks.AIR)
            return;
        for (int i = 0; i < te.getSizeInventory(); i++) {
            ItemStack is = te.getStackInSlot(i);
            if (!is.isEmpty()) {
                GlStateManager.pushMatrix();
                setLightmapDisabled(true);
                double x = x1 + Math.rint(100.0 * (0.67 - i % 3 * 0.19)) / 100.0, y = y1 + 1.02,
                        z = z1 + Math.rint(100.0 * (0.67 - i / 3 * 0.19)) / 100.0;
                GlStateManager.translate(x, y, z);
                GlStateManager.scale(0.65, 0.65, 0.65);
                double time = Minecraft.getSystemTime() / 800D;
                GlStateManager.translate(0D, Math.sin(time % (2 * Math.PI)) * 0.065, 0D);
                GlStateManager.rotate((float) (time * 40D % 360), 0, 1, 0);
                mc.getRenderItem().renderItem(is, ItemCameraTransforms.TransformType.GROUND);
                GlStateManager.popMatrix();
            }
        }
    }
}
package ftblag.stoneblockutilities.render;

import ftblag.stoneblockutilities.blocks.StoneWorkbenchBlock;
import ftblag.stoneblockutilities.tileentity.StoneWorkbenchTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWB extends TileEntitySpecialRenderer<StoneWorkbenchTileEntity> {

    @Override
    public void render(StoneWorkbenchTileEntity te, double x1, double y1, double z1, float p, int d, float a) {
        if (te.getWorld().getBlockState(te.getPos().up()).getBlock() != Blocks.AIR)
            return;

        double dsc = x1 * x1 + y1 * y1 + z1 * z1;
        int r = 8;
        if (dsc > r * r)
            return;

        EnumFacing facing = te.getWorld().getBlockState(te.getPos()).getValue(StoneWorkbenchBlock.FACING);

        GlStateManager.pushMatrix();
        setLightmapDisabled(true);
        GlStateManager.translate(x1, y1, z1);
        GlStateManager.translate(0.5, 0, 0.5);
        GlStateManager.rotate(-facing.getHorizontalIndex() * 90, 0, 1, 0);
        GlStateManager.translate(-0.5, 0, -0.5);
        GlStateManager.enablePolygonOffset();

        for (int i = 0; i < te.getSizeInventory(); i++) {
            ItemStack is = te.getStackInSlot(i);
            if (!is.isEmpty()) {
                GlStateManager.pushMatrix();

                double x = Math.rint(100.0 * (0.33 + i % 3 * 0.19)) / 100.0;
                double y = 1.05;
                double z = Math.rint(100.0 * (0.33 + i / 3 * 0.19)) / 100.0;

                GlStateManager.doPolygonOffset(i * -0.1F, i * -0.1F);
                GlStateManager.translate(x, y, z);
                GlStateManager.scale(0.4, 0.4, 0.4);
                double time = Minecraft.getSystemTime() / 800D;
                GlStateManager.translate(0D, Math.sin(time % (2 * Math.PI)) * 0.065, 0D);
                GlStateManager.rotate((float) (time * 40D % 360), 0, 1, 0);
                Minecraft.getMinecraft().getRenderItem().renderItem(is, ItemCameraTransforms.TransformType.GROUND);

                GlStateManager.popMatrix();
            }
        }
        GlStateManager.doPolygonOffset(0F, 0F);
        GlStateManager.disablePolygonOffset();
        setLightmapDisabled(false);
        GlStateManager.popMatrix();
    }
}
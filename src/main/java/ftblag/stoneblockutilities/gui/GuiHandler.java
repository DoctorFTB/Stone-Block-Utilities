package ftblag.stoneblockutilities.gui;

import ftblag.stoneblockutilities.tileentity.StoneWorkbenchTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0)
            return new ContainerWB(player, world, (StoneWorkbenchTileEntity) world.getTileEntity(new BlockPos(x, y, z)));
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0)
            return new GuiWB(player, world, (StoneWorkbenchTileEntity) world.getTileEntity(new BlockPos(x, y, z)));
        return null;
    }
}
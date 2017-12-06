package ftblag.stoneblockutilities.gui;

import ftblag.stoneblockutilities.registry.SBURegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerWB extends ContainerWorkbench {

    private final World world;
    private final BlockPos pos;

    public ContainerWB(InventoryPlayer playerInventory, World worldIn, BlockPos posIn) {
        super(playerInventory, worldIn, posIn);
        world = worldIn;
        pos = posIn;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        if (world.getBlockState(pos).getBlock() != SBURegistry.table)
            return false;
        else
            return playerIn.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
    }
}
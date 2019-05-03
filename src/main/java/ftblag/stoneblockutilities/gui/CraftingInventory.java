package ftblag.stoneblockutilities.gui;

import ftblag.stoneblockutilities.tileentity.StoneWorkbenchTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

public class CraftingInventory extends InventoryCrafting {
    private int length;
    private Container container;
    private StoneWorkbenchTileEntity inv;

    public CraftingInventory(Container containerIn, StoneWorkbenchTileEntity parent, int width, int height) {
        super(containerIn, width, height);
        inv = parent;
        length = width * height;
        container = containerIn;
    }

    @Override
    public int getSizeInventory() {
        return length;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return i < 0 || i >= getSizeInventory() ? ItemStack.EMPTY : inv.getStackInSlot(i);
    }

    @Override
    public ItemStack decrStackSize(int i, int size) {
        ItemStack is = inv.decrStackSize(i, size);
        if (is != ItemStack.EMPTY) {
//            container.onCraftMatrixChanged(this);
            inv.updateInvs();
        }
        return is;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inv.setInventorySlotContents(index, stack);
        inv.updateInvs();
//        container.onCraftMatrixChanged(this);
    }

    @Override
    public void markDirty() {
        inv.markDirty();
        IBlockState state = inv.getWorld().getBlockState(inv.getPos());
        inv.getWorld().notifyBlockUpdate(inv.getPos(), state, state, 3);
//        container.onCraftMatrixChanged(this);
    }

    @Override
    public void openInventory(EntityPlayer player) {
        super.openInventory(player);
        inv.onOpen(this);
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        super.closeInventory(player);
        inv.onClose(this);
    }

    public void changed() {
        container.onCraftMatrixChanged(this);
    }
}

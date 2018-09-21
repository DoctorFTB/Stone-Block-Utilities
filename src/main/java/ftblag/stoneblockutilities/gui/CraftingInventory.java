package ftblag.stoneblockutilities.gui;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

public class CraftingInventory extends InventoryCrafting {
    private final int length;
    private final Container container;
    private final IInventory inv;

    public CraftingInventory(Container containerIn, IInventory parent, int width, int height) {
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
        if (is != ItemStack.EMPTY)
            container.onCraftMatrixChanged(this);
        return is;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inv.setInventorySlotContents(index, stack);
        container.onCraftMatrixChanged(this);
    }

    @Override
    public void markDirty() {
        inv.markDirty();
        container.onCraftMatrixChanged(this);
    }
}

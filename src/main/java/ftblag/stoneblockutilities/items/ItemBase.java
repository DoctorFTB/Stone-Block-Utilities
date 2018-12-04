package ftblag.stoneblockutilities.items;

import ftblag.stoneblockutilities.StoneBlockUtilities;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item {

    public ItemBase(String name) {
        setRegistryName(StoneBlockUtilities.MODID, name);
        setUnlocalizedName(StoneBlockUtilities.MODID + "." + name);
    }

    @Override
    protected boolean isInCreativeTab(CreativeTabs targetTab) {
        return false;
    }
}

package ftblag.stoneblockutilities.items;

import ftblag.stoneblockutilities.StoneBlockUtilities;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemHand extends Item {

    public ItemHand() {
        setRegistryName(StoneBlockUtilities.MODID, "hand");
        setUnlocalizedName(StoneBlockUtilities.MODID + ".hand");
    }

    @Override
    protected boolean isInCreativeTab(CreativeTabs targetTab) {
        return false;
    }
}

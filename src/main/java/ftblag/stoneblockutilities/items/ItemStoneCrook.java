package ftblag.stoneblockutilities.items;

import com.google.common.collect.Sets;

import exnihilocreatio.items.tools.ICrook;
import ftblag.stoneblockutilities.StoneBlockUtilities;
import ftblag.stoneblockutilities.creativetabs.CT;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class ItemStoneCrook extends ItemTool implements ICrook {

    public ItemStoneCrook() {
        super(ToolMaterial.STONE, Sets.newHashSet(new Block[] {}));

        setRegistryName(StoneBlockUtilities.MODID, "crook_stone");
        setUnlocalizedName(StoneBlockUtilities.MODID + ".crook_stone");
        setMaxDamage(256);
        setCreativeTab(CT.tab);
    }

    @Override
    public boolean isCrook(ItemStack stack) {
        return true;
    }
}
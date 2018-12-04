package ftblag.stoneblockutilities.items;

import com.google.common.collect.Sets;

import exnihilocreatio.items.tools.ICrook;
import ftblag.stoneblockutilities.StoneBlockUtilities;
import ftblag.stoneblockutilities.creativetabs.CT;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface = "exnihilocreatio.items.tools.ICrook", modid = "exnihilocreatio")
public class ItemStoneCrook extends ItemTool implements ICrook {

    public ItemStoneCrook() {
        super(ToolMaterial.STONE, Sets.newHashSet(new Block[] {}));

        setRegistryName(StoneBlockUtilities.MODID, "crook_stone");
        setUnlocalizedName(StoneBlockUtilities.MODID + ".crook_stone");
        setMaxDamage(256);
        setCreativeTab(CT.tab);
    }

    @Optional.Method(modid = "exnihilocreatio")
    @Override
    public boolean isCrook(ItemStack stack) {
        return true;
    }
}
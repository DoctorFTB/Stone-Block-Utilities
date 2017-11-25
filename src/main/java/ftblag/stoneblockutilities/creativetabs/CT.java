package ftblag.stoneblockutilities.creativetabs;

import ftblag.stoneblockutilities.StoneBlockUtilities;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CT extends CreativeTabs {

	public static final CreativeTabs tab = new CT();

	public CT() {
		super(StoneBlockUtilities.MODID);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTabLabel() {
		return StoneBlockUtilities.MODID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return StoneBlockUtilities.MODID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getTabIconItem() {
		return new ItemStack(Items.DIAMOND);
	}
}
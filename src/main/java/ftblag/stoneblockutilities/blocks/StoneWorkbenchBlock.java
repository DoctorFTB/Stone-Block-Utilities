package ftblag.stoneblockutilities.blocks;

import ftblag.stoneblockutilities.StoneBlockUtilities;
import ftblag.stoneblockutilities.creativetabs.CT;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StoneWorkbenchBlock extends Block {

	public StoneWorkbenchBlock() {
		super(Material.ROCK);
		setCreativeTab(CreativeTabs.DECORATIONS);
		setRegistryName(StoneBlockUtilities.MODID, "stoneworkbench");
		setUnlocalizedName(StoneBlockUtilities.MODID + ".stoneworkbench");
		setCreativeTab(CT.tab);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!world.isRemote)
			player.openGui(StoneBlockUtilities.INSTANCE, 0, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
}
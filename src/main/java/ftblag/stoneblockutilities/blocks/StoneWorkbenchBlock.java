package ftblag.stoneblockutilities.blocks;

import ftblag.stoneblockutilities.StoneBlockUtilities;
import ftblag.stoneblockutilities.creativetabs.CT;
import ftblag.stoneblockutilities.tileentity.StoneWorkbenchTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StoneWorkbenchBlock extends Block implements ITileEntityProvider {

    public StoneWorkbenchBlock() {
        super(Material.WOOD);
        setCreativeTab(CreativeTabs.DECORATIONS);
        setRegistryName(StoneBlockUtilities.MODID, "stoneworkbench");
        setUnlocalizedName(StoneBlockUtilities.MODID + ".stoneworkbench");
        setCreativeTab(CT.tab);
        setHardness(2.5F);
        setSoundType(SoundType.STONE);
        GameRegistry.registerTileEntity(StoneWorkbenchTileEntity.class, "stoneworkbench");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
            EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote)
            player.openGui(StoneBlockUtilities.INSTANCE, 0, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new StoneWorkbenchTileEntity();
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player,
            boolean willHarvest) {
        if (!world.isRemote)
            InventoryHelper.dropInventoryItems(world, pos, (IInventory) world.getTileEntity(pos));
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }
}
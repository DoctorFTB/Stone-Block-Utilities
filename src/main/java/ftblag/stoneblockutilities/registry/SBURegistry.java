package ftblag.stoneblockutilities.registry;

import static ftblag.stoneblockutilities.StoneBlockUtilities.MODID;

import ftblag.stoneblockutilities.blocks.StoneWorkbenchBlock;
import ftblag.stoneblockutilities.items.ItemStoneCrook;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = MODID)
public class SBURegistry {

	public static ItemStoneCrook crook = new ItemStoneCrook();
	public static StoneWorkbenchBlock table = new StoneWorkbenchBlock();

	@SubscribeEvent
	public void item(Register<Item> e) {
		e.getRegistry().registerAll(crook, new ItemBlock(table).setRegistryName(table.getRegistryName()));
	}

	@SubscribeEvent
	public void block(Register<Block> e) {
		e.getRegistry().registerAll(table);
	}

	@SubscribeEvent
	public void recipe(Register<IRecipe> e) {
		// TODO better registr
		//		ShapedPrimer primer = CraftingHelper.parseShaped("## ", "# ", "# ", '#', Blocks.COBBLESTONE);
		//		e.getRegistry().register(new ShapedRecipes(new ResourceLocation(MODID + ":table").toString(), primer.width,
		//				primer.height, primer.input, new ItemStack(SBURegistry.crook)));

		GameRegistry.addShapedRecipe(new ResourceLocation(MODID + ":crook"), new ResourceLocation(MODID + "crook"),
		        new ItemStack(SBURegistry.crook), "## ", "#  ", "#  ", '#', Blocks.COBBLESTONE);

		GameRegistry.addShapedRecipe(new ResourceLocation(MODID + ":table"), new ResourceLocation(MODID + "table"),
		        new ItemStack(SBURegistry.table), "##", "##", '#', Blocks.COBBLESTONE);

		//		NonNullList<Ingredient> l = NonNullList.create();
		//		for (int i = 0; i < 4; i++)
		//			l.add(Ingredient.fromStacks(new ItemStack(Blocks.COBBLESTONE)));
		//		e.getRegistry().register(new ShapelessRecipes(new ResourceLocation(MODID + ":table").toString(),
		//				new ItemStack(SBURegistry.table), l).setRegistryName(new ResourceLocation(MODID + ":table")));
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void models(ModelRegistryEvent e) {
		ModelLoader.setCustomModelResourceLocation(crook, 0,
		        new ModelResourceLocation(crook.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(table), 0,
		        new ModelResourceLocation(table.getRegistryName(), "inventory"));
	}
}
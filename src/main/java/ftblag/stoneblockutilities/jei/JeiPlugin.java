package ftblag.stoneblockutilities.jei;

import java.util.List;

import com.google.common.collect.Lists;

import ftblag.stoneblockutilities.jei.crook.CrookRecipe;
import ftblag.stoneblockutilities.jei.crook.CrookRecipeCategory;
import ftblag.stoneblockutilities.registry.SBURegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JeiPlugin implements IModPlugin {

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new CrookRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void register(IModRegistry registry) {
		List<CrookRecipe> crookRecipes = Lists.newArrayList();
		crookRecipes.add(new CrookRecipe());
		registry.addRecipes(crookRecipes, CrookRecipeCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(SBURegistry.crook), CrookRecipeCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(SBURegistry.table), VanillaRecipeCategoryUid.CRAFTING);
	}
}
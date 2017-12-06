package ftblag.stoneblockutilities.jei;

import com.google.common.collect.Lists;

import ftblag.stoneblockutilities.jei.crook.CrookRecipe;
import ftblag.stoneblockutilities.jei.crook.CrookRecipeCategory;
import ftblag.stoneblockutilities.jei.hand.HandRecipe;
import ftblag.stoneblockutilities.jei.hand.HandRecipeCategory;
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
        registry.addRecipeCategories(new HandRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void register(IModRegistry registry) {
        registry.addRecipes(Lists.newArrayList(new CrookRecipe()), CrookRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(SBURegistry.crook), CrookRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(SBURegistry.table), VanillaRecipeCategoryUid.CRAFTING);

        registry.addRecipes(Lists.newArrayList(new HandRecipe()), HandRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(SBURegistry.hand), HandRecipeCategory.UID);
        registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(SBURegistry.hand));
    }
}
package ftblag.stoneblockutilities.jei;

import com.google.common.collect.Lists;
import ftblag.stoneblockutilities.gson.SBUGsonParser;
import ftblag.stoneblockutilities.gson.SBUGsonUtils;
import ftblag.stoneblockutilities.gui.ContainerWB;
import ftblag.stoneblockutilities.gui.GuiWB;
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

import java.util.ArrayList;
import java.util.List;

@JEIPlugin
public class JeiPlugin implements IModPlugin {

    private static List<HandRecipe> getHandRecipes() {
        List<HandRecipe> ret = new ArrayList<>();
        for (SBUGsonUtils.HandDrop drop : SBUGsonUtils.handDrops.values()) {
            ret.add(new HandRecipe(drop.original.copy(), drop.dropWith.copy(), drop.dropWithout.copy()));
        }
        return ret;
    }

    private static List<CrookRecipe> getCrookRecipes() {
        List<CrookRecipe> ret = new ArrayList<>();
        for (SBUGsonUtils.CrookDrop drop : SBUGsonUtils.crookDrops.values()) {
            ret.add(new CrookRecipe(drop.original.copy(), drop.drops));
        }
        return ret;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new CrookRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new HandRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void register(IModRegistry registry) {
        registry.addRecipes(getCrookRecipes(), CrookRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(SBURegistry.crook), CrookRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(SBURegistry.table), VanillaRecipeCategoryUid.CRAFTING);

        registry.addRecipes(getHandRecipes(), HandRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(SBURegistry.hand), HandRecipeCategory.UID);
//        registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(SBURegistry.hand));
        registry.addRecipeClickArea(GuiWB.class, 88, 32, 28, 23, VanillaRecipeCategoryUid.CRAFTING);
        registry.getRecipeTransferRegistry().addRecipeTransferHandler(ContainerWB.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);
    }
}
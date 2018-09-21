package ftblag.stoneblockutilities.jei.crook;

import javax.annotation.Nonnull;

import ftblag.stoneblockutilities.StoneBlockUtilities;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CrookRecipeCategory implements IRecipeCategory<CrookRecipe> {
    public static final String UID = StoneBlockUtilities.MODID + ":crook";
    private static final ResourceLocation texture = new ResourceLocation("exnihilocreatio",
            "textures/gui/jei_hammer.png");

    private final IDrawableStatic background;

    public CrookRecipeCategory(IGuiHelper helper) {
        background = helper.createDrawable(texture, 0, 0, 166, 128);
    }

    @Override
    @Nonnull
    public String getUid() {
        return UID;
    }

    @Override
    @Nonnull
    public String getTitle() {
        return "Crook";
    }

    @Override
    public String getModName() {
        return StoneBlockUtilities.MODID;
    }

    @Override
    @Nonnull
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CrookRecipe recipeWrapper, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 74, 9);
        recipeLayout.getItemStacks().set(0, recipeWrapper.getInputs().get(0));

        int slotIndex = 1;

        for (int i = 0; i < recipeWrapper.getOutputs().size(); i++) {
            final int slotX = 2 + i % 9 * 18;
            final int slotY = 36 + i / 9 * 18;

            ItemStack outputStack = recipeWrapper.getOutputs().get(i);

            recipeLayout.getItemStacks().init(slotIndex + i, false, slotX, slotY);
            recipeLayout.getItemStacks().set(slotIndex + i, outputStack);
        }
    }
}
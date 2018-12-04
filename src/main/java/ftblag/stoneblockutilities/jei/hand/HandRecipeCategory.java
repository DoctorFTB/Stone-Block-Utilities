package ftblag.stoneblockutilities.jei.hand;

import ftblag.stoneblockutilities.StoneBlockUtilities;
import ftblag.stoneblockutilities.gson.SBUGsonParser;
import ftblag.stoneblockutilities.gson.SBUGsonUtils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.List;

public class HandRecipeCategory implements IRecipeCategory<HandRecipe> {
    public static final String UID = StoneBlockUtilities.MODID + ":hand";
    private static final ResourceLocation texture = new ResourceLocation(StoneBlockUtilities.MODID,
            "textures/gui/jei_page.png");

    private final IDrawableStatic background;

    public HandRecipeCategory(IGuiHelper helper) {
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
        return "Hand";
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
    public void setRecipe(IRecipeLayout recipeLayout, HandRecipe recipeWrapper, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 74, 9);
        ItemStack original = recipeWrapper.getInputs().get(0);
        recipeLayout.getItemStacks().set(0, original);
        SBUGsonUtils.HandDrop drop = SBUGsonUtils.getHandDropFromOriginal(original);

        int slotIndex = 1;

        for (int i = 0; i < recipeWrapper.getOutputs().size(); i++) {
            final int slotX = 2 + i % 9 * 18;
            final int slotY = 36 + i / 9 * 18;

            ItemStack outputStack = recipeWrapper.getOutputs().get(i);

            recipeLayout.getItemStacks().init(slotIndex + i, false, slotX, slotY);
            recipeLayout.getItemStacks().set(slotIndex + i, outputStack);
        }

        recipeLayout.getItemStacks().addTooltipCallback(new ITooltipCallback<ItemStack>() {

            @Override
            public void onTooltip(int slotIndex, boolean input, ItemStack ingredient, List<String> tooltip) {
                if (!input) {
                    tooltip.add("Drop Chance");
                    if (slotIndex == 2)
                        tooltip.add(" * " + drop.dropWithout.getCount() + "x 100%");
                    else
                        tooltip.add(" * " + drop.dropWith.getCount() + "x " + 100 / drop.chance + "%");
                }
            }
        });
    }
}
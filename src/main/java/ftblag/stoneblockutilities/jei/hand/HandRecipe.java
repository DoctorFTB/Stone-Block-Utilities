package ftblag.stoneblockutilities.jei.hand;

import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import exnihilocreatio.ModItems;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class HandRecipe implements IRecipeWrapper {

    private List<ItemStack> inputs;
    private List<ItemStack> outputs;

    public HandRecipe() {
        inputs = Lists.newArrayList(new ItemStack(Blocks.STONE));
        outputs = Lists.newArrayList(new ItemStack(ModItems.pebbles, 1, 0));
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class, inputs);
        ingredients.setOutputs(ItemStack.class, outputs);
    }

    public List<ItemStack> getInputs() {
        return inputs;
    }

    public List<ItemStack> getOutputs() {
        return outputs;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

    }

    @Override
    @Nonnull
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return Lists.newArrayList();
    }

    @Override
    public boolean handleClick(@Nonnull Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }
}
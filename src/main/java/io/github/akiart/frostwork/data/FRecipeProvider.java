package io.github.akiart.frostwork.data;

import io.github.akiart.frostwork.common.item.FItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class FRecipeProvider extends FRecipeProviderBase {
    public FRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        // Tatzelwurm Arrows
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, FItems.TATZELWURM_ARROW, 4)
                .define('T', FItems.TATZELWURM_SCALE)
                .define('S', Items.STICK)
                .define('F', Tags.Items.FEATHERS)
                .pattern("T")
                .pattern("S")
                .pattern("F")
                .unlockedBy(getHasName(FItems.TATZELWURM_SCALE), has(FItems.TATZELWURM_SCALE))
                .save(recipeOutput);

    }
}

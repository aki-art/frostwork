package io.github.akiart.frostwork.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;

import java.util.concurrent.CompletableFuture;

public class FRecipeProvider extends FRecipeProviderBase {
    public FRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }



    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

    }
}

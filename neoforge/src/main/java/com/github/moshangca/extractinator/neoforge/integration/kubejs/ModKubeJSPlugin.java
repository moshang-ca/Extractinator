package com.github.moshangca.extractinator.neoforge.integration.kubejs;

import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.component.IngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.ListRecipeComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;

public class ModKubeJSPlugin implements KubeJSPlugin {

    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry) {
        registry.namespace("extractinator")
            .register(
                "extractinating",
                new RecipeSchema(
                    IngredientComponent.INGREDIENT.inputKey("input"),
                    ListRecipeComponent.create(DropComponent.TYPE.instance(), false, false).outputKey("drops")
                )
            );
    }
}

package com.rw.spring.pizza.web.resource.hateoas;

import com.rw.spring.pizza.domain.Ingredient;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(value="ingredient", collectionRelation="ingredients")
public class IngredientRepresentation extends RepresentationModel<IngredientRepresentation> {

    @Getter
    private String name;
    @Getter
    private Ingredient.Type type;

    public IngredientRepresentation(final Ingredient ingredient) {
        this.name = ingredient.getName();
        this.type = ingredient.getType();
    }
}

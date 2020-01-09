package com.rw.spring.pizza.web.resource.hateoas;

import com.rw.spring.pizza.domain.Ingredient;
import com.rw.spring.pizza.web.api.IngredientController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class IngredientRepresentationAssembler extends RepresentationModelAssemblerSupport<Ingredient, IngredientRepresentation> {

    public IngredientRepresentationAssembler() {
        super(IngredientController.class, IngredientRepresentation.class);
    }

    @Override
    public IngredientRepresentation toModel(Ingredient entity) {
        return createModelWithId(entity.getId(), entity);
    }

    @Override
    protected IngredientRepresentation instantiateModel(Ingredient entity) {
        return new IngredientRepresentation(entity);
    }
}

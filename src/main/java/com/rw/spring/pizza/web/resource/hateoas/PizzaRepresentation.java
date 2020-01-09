package com.rw.spring.pizza.web.resource.hateoas;

import com.rw.spring.pizza.domain.Pizza;
import lombok.Getter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;

@Relation(value="pizza", collectionRelation="pizzas")
public class PizzaRepresentation extends RepresentationModel<PizzaRepresentation> {
    private static final IngredientRepresentationAssembler
            ingredientAssembler = new IngredientRepresentationAssembler();

    @Getter
    private final String name;
    @Getter
    private final Date createdAt;
    @Getter
    private final CollectionModel<IngredientRepresentation> ingredients;

    public PizzaRepresentation(final Pizza pizza) {
        this.name = pizza.getName();
        this.createdAt = pizza.getCreatedAt();
        this.ingredients = ingredientAssembler.toCollectionModel(pizza.getIngredients());
    }
}

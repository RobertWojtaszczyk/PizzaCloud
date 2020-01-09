package com.rw.spring.pizza.web.resource.hateoas;

import com.rw.spring.pizza.domain.Ingredient;
import com.rw.spring.pizza.domain.Pizza;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.List;

public class PizzaRepresentation extends RepresentationModel<PizzaRepresentation> {
    @Getter
    private final String name;
    @Getter
    private final Date createdAt;
    @Getter
    private final List<Ingredient> ingredients;

    public PizzaRepresentation(final Pizza pizza) {
        this.name = pizza.getName();
        this.createdAt = pizza.getCreatedAt();
        this.ingredients = pizza.getIngredients();
    }
}

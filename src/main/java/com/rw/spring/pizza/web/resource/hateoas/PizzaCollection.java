package com.rw.spring.pizza.web.resource.hateoas;

import org.springframework.hateoas.CollectionModel;

import java.util.List;

public class PizzaCollection extends CollectionModel<PizzaCollection> {
    public PizzaCollection(List<PizzaCollection> tacoResources) {
        super(tacoResources);
    }
}

package com.rw.spring.pizza.web.resource.hateoas;

import com.rw.spring.pizza.domain.Pizza;
import com.rw.spring.pizza.web.api.DesignPizzaApiController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class PizzaRepresentationAssembler extends RepresentationModelAssemblerSupport<Pizza, PizzaRepresentation> {

    public PizzaRepresentationAssembler() {
        super(DesignPizzaApiController.class, PizzaRepresentation.class);
    }

    @Override
    protected PizzaRepresentation instantiateModel(Pizza entity) {
        return new PizzaRepresentation(entity);
    }

    @Override
    public PizzaRepresentation toModel(Pizza entity) {
        return createModelWithId(entity.getId(), entity);
    }
}

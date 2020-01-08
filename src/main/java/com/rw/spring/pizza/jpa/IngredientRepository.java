package com.rw.spring.pizza.jpa;

import com.rw.spring.pizza.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}

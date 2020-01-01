package com.rw.spring.pizza.jpa;

import com.rw.spring.pizza.Pizza;
import org.springframework.data.repository.CrudRepository;

public interface PizzaRepository extends CrudRepository<Pizza, Long> {
}

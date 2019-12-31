package com.rw.spring.pizza.data;

import com.rw.spring.pizza.Pizza;

public interface PizzaRepository {
    Pizza save(Pizza design);
}

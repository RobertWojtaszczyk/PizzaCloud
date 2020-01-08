package com.rw.spring.pizza.jpa;

import com.rw.spring.pizza.domain.Pizza;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PizzaRepository extends PagingAndSortingRepository<Pizza, Long> {
}

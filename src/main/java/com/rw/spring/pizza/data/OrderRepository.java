package com.rw.spring.pizza.data;

import com.rw.spring.pizza.domain.Order;

public interface OrderRepository {
    Order save(Order order);
}

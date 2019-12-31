package com.rw.spring.pizza.data;

import com.rw.spring.pizza.Order;

public interface OrderRepository {
    Order save(Order order);
}

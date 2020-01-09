package com.rw.spring.pizza.web.mapper;

import com.rw.spring.pizza.domain.Order;
import com.rw.spring.pizza.web.resource.input.OrderResourceInput;
import com.rw.spring.pizza.web.resource.output.OrderResourceOutput;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class OrderMapper {

    public Order asOrder(OrderResourceInput resourceInput) {
        return Order.builder()
                .placedAt(resourceInput.getPlacedAt())
                .deliveryName(resourceInput.getDeliveryName())
                .deliveryStreet(resourceInput.getDeliveryStreet())
                .deliveryCity(resourceInput.getDeliveryCity())
                .deliveryState(resourceInput.getDeliveryState())
                .deliveryZip(resourceInput.getDeliveryZip())
                .ccNumber(resourceInput.getCcNumber())
                .ccExpiration(resourceInput.getCcExpiration())
                .ccCVV(resourceInput.getCcCVV())
                .pizzas(resourceInput.getPizzas())
                .user(resourceInput.getUser())
                .build();
    }

    public Order asOrder(OrderResourceInput resourceInput, Long orderId) {
        return Order.builder()
                .id(orderId)
                .placedAt(resourceInput.getPlacedAt())
                .deliveryName(resourceInput.getDeliveryName())
                .deliveryStreet(resourceInput.getDeliveryStreet())
                .deliveryCity(resourceInput.getDeliveryCity())
                .deliveryState(resourceInput.getDeliveryState())
                .deliveryZip(resourceInput.getDeliveryZip())
                .ccNumber(resourceInput.getCcNumber())
                .ccExpiration(resourceInput.getCcExpiration())
                .ccCVV(resourceInput.getCcCVV())
                .pizzas(resourceInput.getPizzas())
                .user(resourceInput.getUser())
                .build();
    }

    public OrderResourceInput asInput(Order order) {
        return OrderResourceInput.builder()
                .placedAt(order.getPlacedAt())
                .deliveryName(order.getDeliveryName())
                .deliveryStreet(order.getDeliveryStreet())
                .deliveryCity(order.getDeliveryCity())
                .deliveryState(order.getDeliveryState())
                .deliveryZip(order.getDeliveryZip())
                .ccNumber(order.getCcNumber())
                .ccExpiration(order.getCcExpiration())
                .ccCVV(order.getCcCVV())
                .pizzas(order.getPizzas())
                .user(order.getUser())
                .build();
    }

    public void update(Order order, OrderResourceInput resourceInput) {
        order.setPlacedAt(resourceInput.getPlacedAt());
        order.setDeliveryName(resourceInput.getDeliveryName());
        order.setDeliveryStreet(resourceInput.getDeliveryStreet());
        order.setDeliveryCity(resourceInput.getDeliveryCity());
        order.setDeliveryState(resourceInput.getDeliveryState());
        order.setDeliveryZip(resourceInput.getDeliveryZip());
        order.setCcNumber(resourceInput.getCcNumber());
        order.setCcExpiration(resourceInput.getCcExpiration());
        order.setCcCVV(resourceInput.getCcCVV());
        order.setPizzas(resourceInput.getPizzas());
        order.setUser(resourceInput.getUser());
    }

    public OrderResourceOutput asOutput(Order order) {
        return OrderResourceOutput.builder()
                .id(order.getId())
                .placedAt(order.getPlacedAt())
                .deliveryName(order.getDeliveryName())
                .deliveryStreet(order.getDeliveryStreet())
                .deliveryCity(order.getDeliveryCity())
                .deliveryState(order.getDeliveryState())
                .deliveryZip(order.getDeliveryZip())
                .ccNumber(order.getCcNumber())
                .ccExpiration(order.getCcExpiration())
                .ccCVV(order.getCcCVV())
                .pizzas(order.getPizzas())
                .user(order.getUser())
                .build();
    }

    public List<OrderResourceOutput> asOutput(Iterable<Order> orders) {
        // List<OrderResourceOutput> mappedList = new ArrayList<>();
        // orders.forEach(order -> mappedList.add(asOutput(order)));

        return StreamSupport.stream(orders.spliterator(), false)
                .map(this::asOutput)
                .collect(Collectors.toList());
    }
}

package com.rw.spring.pizza.web.api;

import com.rw.spring.pizza.domain.Order;
import com.rw.spring.pizza.jpa.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/api/orders", produces = "application/json")
@CrossOrigin(origins = "*")
public class OrderApiController {

    private OrderRepository orderRepository;

    public OrderApiController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public Iterable<Order> allOrders() {
        return orderRepository.findAll();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Order postOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    @PutMapping(path = "/{orderId}", consumes = "application/json")
    public ResponseEntity<Order> putOrder(@PathVariable("orderId") Long orderId,
                          @RequestBody Order order) { // co z orderId ???
        return new ResponseEntity<>(orderRepository.save(order), HttpStatus.OK);
    }

    @PatchMapping(path = "/{orderId}", consumes = "application/json")
    public ResponseEntity<Order> patchOrder(@PathVariable("orderId") Long orderId,
                            @RequestBody Order patch) {

        log.info("Processing orderId: " + orderId + " with update: " + patch);

        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()) {
            log.info("Order from db: " + order.get());
        } else log.info("Order: " + orderId + " not found!");

        return order
                .map(ord -> updateOrder(ord, patch))
                .map(ord -> new ResponseEntity<>(ord, HttpStatus.OK))
                .orElseGet(()->new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    private Order updateOrder(Order order, Order update) {
        log.info("Original order: " + order);
        log.info("Update: " + update);
        if (update.getDeliveryName() != null) {
            order.setDeliveryName(update.getDeliveryName());
        }
        if (update.getDeliveryStreet() != null) {
            order.setDeliveryStreet(update.getDeliveryStreet());
        }
        if (update.getDeliveryCity() != null) {
            order.setDeliveryCity(update.getDeliveryCity());
        }
        if (update.getDeliveryState() != null) {
            order.setDeliveryState(update.getDeliveryState());
        }
        if (update.getDeliveryZip() != null) {
            order.setDeliveryZip(update.getDeliveryState());
        }
        if (update.getCcNumber() != null) {
            order.setCcNumber(update.getCcNumber());
        }
        if (update.getCcExpiration() != null) {
            order.setCcExpiration(update.getCcExpiration());
        }
        if (update.getCcCVV() != null) {
            order.setCcCVV(update.getCcCVV());
        }
        log.info("Updated order: " + order);
        return orderRepository.save(order);
    }
}

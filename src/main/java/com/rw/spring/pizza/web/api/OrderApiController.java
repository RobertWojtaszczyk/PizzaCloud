package com.rw.spring.pizza.web.api;

import com.rw.spring.pizza.domain.Order;
import com.rw.spring.pizza.jpa.OrderRepository;
import com.rw.spring.pizza.web.exception.ResourceNotFoundException;
import com.rw.spring.pizza.web.mapper.OrderMapper;
import com.rw.spring.pizza.web.resource.input.OrderResourceInput;
import com.rw.spring.pizza.web.resource.output.OrderResourceOutput;
import com.rw.spring.pizza.web.util.PatchHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonMergePatch;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/api/orders", produces = "application/json")
@CrossOrigin(origins = "*")
public class OrderApiController {

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private PatchHelper patchHelper;

    public OrderApiController(OrderRepository orderRepository, OrderMapper orderMapper, PatchHelper patchHelper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.patchHelper = patchHelper;
    }

    @GetMapping
    public Iterable<OrderResourceOutput> allOrders() {
        return orderMapper.asOutput(orderRepository.findAll());
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Order postOrder(@RequestBody OrderResourceInput order) {
        return orderRepository.save(orderMapper.asOrder(order));
    }

    @PutMapping(path = "/{orderId}", consumes = "application/json")
    public ResponseEntity<Order> putOrder(@PathVariable("orderId") Long orderId,
                            @RequestBody Order order) { // co z orderId ???
        return new ResponseEntity<>(orderRepository.save(order), HttpStatus.OK);
    }

    @PatchMapping(path = "/{orderId}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrderResourceOutput> patchOrder(@PathVariable("orderId") Long orderId,
                            @RequestBody JsonMergePatch mergePatchDocument) {
        log.info("Processing orderId: " + orderId + " with update: " + mergePatchDocument);

        Order order = orderRepository.findById(orderId).orElseThrow(ResourceNotFoundException::new);;
        log.info("Original order: " + order);

        OrderResourceInput orderResource = orderMapper.asInput(order);
        OrderResourceInput orderResourcePatched = patchHelper.mergePatch(mergePatchDocument, orderResource, OrderResourceInput.class);

        orderMapper.update(order, orderResourcePatched);
        log.info("Updated order: " + order);
        orderRepository.save(order);
        return new ResponseEntity<>(orderMapper.asOutput(order), HttpStatus.OK);
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

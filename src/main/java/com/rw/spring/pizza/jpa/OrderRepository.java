package com.rw.spring.pizza.jpa;

import com.rw.spring.pizza.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByDeliveryZip(String deliveryZip);
    List<Order> findOrdersByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);
    List<Order> findByDeliveryStreetAndDeliveryCityOrderByDeliveryCity(String deliveryStreet, String deliveryCity);
    // List<Order> findByDeliveryToAndDeliveryCityAllIgnoresCase(String deliveryTo, String deliveryCity);

    /*@Query("Order o where o.deliveryCity='Gliwice'")
    List<Order> readOrdersDeliveredInGliwice();*/
}

package com.rw.spring.pizza.web.api;

import com.rw.spring.pizza.Pizza;
import com.rw.spring.pizza.jpa.PizzaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/design", produces = "application/json")
@CrossOrigin(origins = "*")
public class DesignPizzaApiController {
    private PizzaRepository pizzaRepository;

    public DesignPizzaApiController(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @GetMapping("/recent")
    public Iterable<Pizza> recentPizzas() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        return pizzaRepository.findAll(page).getContent();
    }

    @GetMapping("{id}")
    public ResponseEntity<Pizza> pizzaById(@PathVariable("id") Long id) {
        log.info("Looking for pizza id: " + id);
        Optional<Pizza> optionalPizza = pizzaRepository.findById(id);

        return optionalPizza
                .map(pizza -> new ResponseEntity<>(pizza, HttpStatus.OK)).orElseGet(()->new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Pizza postPizza(@RequestBody Pizza pizza) {
        return pizzaRepository.save(pizza);
    }
}

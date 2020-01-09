package com.rw.spring.pizza.web.api;

import com.rw.spring.pizza.domain.Ingredient;
import com.rw.spring.pizza.domain.Pizza;
import com.rw.spring.pizza.jpa.IngredientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/api/ingredients", produces="application/json")
@CrossOrigin(origins="*")
public class IngredientController {

    private IngredientRepository ingredientRepository;

    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    public Iterable<Ingredient> allIngredients() {
        return ingredientRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Ingredient> ingredientById(@PathVariable("id") String id) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);

        return optionalIngredient
                .map(ingredient -> new ResponseEntity<>(ingredient, HttpStatus.OK)).orElseGet(()->new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}

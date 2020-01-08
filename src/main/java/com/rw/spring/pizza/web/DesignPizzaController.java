package com.rw.spring.pizza.web;

import com.rw.spring.pizza.domain.Ingredient;
import com.rw.spring.pizza.domain.Order;
import com.rw.spring.pizza.domain.Pizza;
import com.rw.spring.pizza.jpa.IngredientRepository;
import com.rw.spring.pizza.jpa.PizzaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import com.rw.spring.pizza.domain.Ingredient.Type;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignPizzaController {
    private final IngredientRepository ingredientRepo;
    private PizzaRepository pizzaRepo;

    @Autowired
    public DesignPizzaController(IngredientRepository ingredientRepo, PizzaRepository pizzaRepo) {
        this.ingredientRepo = ingredientRepo;
        this.pizzaRepo = pizzaRepo;
    }

    @ModelAttribute(name = "design")
    public Pizza pizza(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(ingredients::add);

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
        return new Pizza();
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    /*@ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "pszenna", Type.WRAP),
                new Ingredient("COTO", "kukurydziana", Type.WRAP),
                new Ingredient("GRBF", "mielona wołowina", Type.PROTEIN),
                new Ingredient("CARN", "kawałki mięsa", Type.PROTEIN),
                new Ingredient("TMTO", "pomidory pokrojone w kostkę", Type.VEGGIES),
                new Ingredient("LETC", "sałata", Type.VEGGIES),
                new Ingredient("CHED", "cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "pikantny sos pomidorowy", Type.SAUCE),
                new Ingredient("SRCR", "śmietana", Type.SAUCE)
        );

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
    }*/

    @GetMapping
    public String showDesignForm(Model model) {
        //model.addAttribute("design", new Pizza());
        /*List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }*/

        return "design";
    }

    @PostMapping
    public String processDesign(@Valid @ModelAttribute("design") Pizza design,
                                Errors errors, @ModelAttribute Order order) {
        log.info("Odebrano obiekt formularza: " + design);
        if (errors.hasErrors()) {
            log.error("Design has some errors!" + errors.getAllErrors());
            return "design";
        }

        log.info("Przetwarzanie projektu pizza: " + design);

        Pizza saved = pizzaRepo.save(design);
        order.addDesign(saved);

        log.info("Redirect to orders!");
        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {

        return ingredients.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());

    }
}

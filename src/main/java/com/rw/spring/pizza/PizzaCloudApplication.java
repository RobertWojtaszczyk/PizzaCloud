package com.rw.spring.pizza;

import com.rw.spring.pizza.Ingredient.Type;
import com.rw.spring.pizza.jpa.IngredientRepository;
import com.rw.spring.pizza.jpa.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PizzaCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(PizzaCloudApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repo, UserRepository userRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                repo.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
                repo.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
                repo.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
                repo.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
                repo.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
                repo.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
                repo.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
                repo.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
                repo.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
                repo.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
                userRepository.save(new User(
                        "robert",
                        "$2a$10$/lkpDjOpWGRg/n2TwVDM2.XDjji6syTV1373qQU33N/MQcbTTyNIe",
                        "Robert W",
                        "Leśna",
                        "Ruda Śląska",
                        "SL",
                        "41-705",
                        "555555555"
                ));
            }
        };
    }
}

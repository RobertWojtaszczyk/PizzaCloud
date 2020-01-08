package com.rw.spring.pizza.data;

import com.rw.spring.pizza.domain.Ingredient;
import com.rw.spring.pizza.domain.Pizza;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Slf4j
//@Repository
public class JdbcPizzaRepository implements PizzaRepository {

    private JdbcTemplate jdbc;

    @Autowired
    public JdbcPizzaRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Pizza save(Pizza design) {
        long pizzaId = savePizzaInfo(design);
        design.setId(pizzaId);
        for (Ingredient ingredient : design.getIngredients()) {
            saveIngredientToPizza(ingredient, pizzaId);
        }
        return design;
    }

    private long savePizzaInfo(Pizza pizza) {
        pizza.setCreatedAt(new Date());
        // insert into Pizza (name, createdAt) values (?, ?)

        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into Pizza (name, createdAt) values (?, ?)",
                Types.VARCHAR, Types.TIMESTAMP);
        pscf.setReturnGeneratedKeys(true);


        PreparedStatementCreator psc =
                pscf.newPreparedStatementCreator(
                        Arrays.asList(
                                pizza.getName(),
                                new Timestamp(pizza.getCreatedAt().getTime())));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);
        Number key = keyHolder.getKey();
        log.info("Generated id: " + keyHolder.getKeyList());

        return keyHolder.getKey().longValue();
    }

    private void saveIngredientToPizza(Ingredient ingredient, long pizzaId) {
        jdbc.update(
                "insert into Pizza_Ingredients (pizza, ingredient) " +
                        "values (?, ?)",
                pizzaId, ingredient.getId());
    }
}

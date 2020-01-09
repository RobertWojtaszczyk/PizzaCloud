package com.rw.spring.pizza.domain;

import com.rw.spring.pizza.domain.Ingredient;
import lombok.Data;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity
@RestResource(rel="pizzas", path="pizzas")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date createdAt;

    @NotNull
    @Size(min = 5, message = "Nazwa musi składać się przynajmniej z 5 znaków.")
    private String name;

    //@NotNull(message = "Lista nie może być pusta")
    //@Size(min = 2, message = "Musisz wybrać przynajmniej dwa składniki.")
    @NotEmpty(message = "Lista nie może być pusta")
    @ManyToMany(targetEntity = Ingredient.class)
    private List<Ingredient> ingredients;

    @PrePersist
    void createdAt() {
        this.createdAt = new Date();
    }
}

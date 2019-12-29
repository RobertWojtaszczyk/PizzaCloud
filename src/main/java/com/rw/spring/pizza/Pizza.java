package com.rw.spring.pizza;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class Pizza {
    @NotNull
    @Size(min = 5, message = "Nazwa musi składać się przynajmniej z 5 znaków.")
    private String name;

    //@NotNull(message = "Lista nie może być pusta")
    //@Size(min = 2, message = "Musisz wybrać przynajmniej dwa składniki.")
    @NotEmpty(message = "Lista nie może być pusta")
    private List<String> ingredients;
}

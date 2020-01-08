package com.rw.spring.pizza.web.resource.output;

import com.rw.spring.pizza.domain.Pizza;
import com.rw.spring.pizza.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResourceOutput {

    private Long id;
    private Date placedAt;

    @NotBlank(message = "Imię i nazwisko jest obowiązkowe")
    private String deliveryName;

    @NotBlank(message = "Ulica jest obowiązkowa")
    private String deliveryStreet;

    @NotBlank(message = "Miejscowość jest obowiązkowa")
    private String deliveryCity;

    @NotBlank(message = "Województwo jest obowiązkowe")
    @Size(max = 2, message = "Województwo może mieć max 2 znaki.")
    private String deliveryState;

    @NotBlank(message = "Kod pocztowy jest obowiązkowy")
    private String deliveryZip;

    @CreditCardNumber(message = "To nie jest prawidłowy numer karty")
    private String ccNumber;

    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
            message="Wartość musi być w formacie MM/RR.")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Nieprawidłowy kod CVV")
    private String ccCVV;

    private List<Pizza> pizzas = new ArrayList<>();

    private User user;

    public void addDesign(Pizza design) {
        this.pizzas.add(design);
    }
}

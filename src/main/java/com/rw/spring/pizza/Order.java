package com.rw.spring.pizza;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class Order {

    private Long id;
    private Date placedAt;

    @NotBlank(message = "Imię i nazwisko jest obowiązkowe")
    private String name;

    @NotBlank(message = "Ulica jest obowiązkowa")
    private String street;

    @NotBlank(message = "Miejscowość jest obowiązkowa")
    private String city;

    @NotBlank(message = "Województwo jest obowiązkowe")
    private String state;

    @NotBlank(message = "Kod pocztowy jest obowiązkowy")
    private String zip;

    @CreditCardNumber(message = "To nie jest prawidłowy numer karty")
    private String ccNumber;

    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
            message="Wartość musi być w formacie MM/RR.")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Nieprawidłowy kod CVV")
    private String ccCVV;
}

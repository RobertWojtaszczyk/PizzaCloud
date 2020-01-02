package com.rw.spring.pizza.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

/*    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("buzz")
                .password("infinity")
                .authorities("ROLE_USER")
                .and()
                .withUser("woody")
                .password("bullseye")
                .authorities("ROLE_USER");
    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                //.passwordEncoder(passwordEncoder())
                .withUser("admin")
                //.password(passwordEncoder().encode("admin"))
                .password("{noop}admin") // java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
                // Błąd ten oznacza, że musisz jawnie podać, w jaki sposób jest zakodowane twoje hasło (w Spring Security 5 zmienił się format przechowywania hasła i teraz przed hasłem trzeba podać algorytm kodowania hasła). Na potrzeby testów korzystamy z inMemoryAuthentication, a dla wygody nie będziemy kodować hasła, więc potrzebujemy przed hasłem dodać {noop}
                .authorities("ROLE_USER");
                //.roles("USER");
    }

/*    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }
}

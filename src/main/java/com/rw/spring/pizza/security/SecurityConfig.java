package com.rw.spring.pizza.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired  //final?
    private UserDetailsService userDetailsService;

    /*public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/design", "/orders")
                .access("hasRole('ROLE_USER')")
                .antMatchers("/", "/**").access("permitAll")
                //end::authorizeRequests[]

                .and()
                .formLogin()
                .loginPage("/login")
                //end::customLoginPage[]

                // tag::enableLogout[]
                .and()
                .logout()
                .logoutSuccessUrl("/")
                // end::enableLogout[]

                // Make H2-Console non-secured; for debug purposes
                // tag::csrfIgnore[]
                .and()
                .csrf()
                .disable();
                //.ignoringAntMatchers("/h2-console/**")
                // end::csrfIgnore[]

                // Allow pages to be loaded in frames from the same origin; needed for H2-Console
                // tag::frameOptionsSameOrigin[]
        http
                .headers()
                .frameOptions()
                .sameOrigin()
        // end::frameOptionsSameOrigin[]

        //tag::authorizeRequests[]
        //tag::customLoginPage[]
        ;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /*@Override  // in Memory Auth
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                //.passwordEncoder(passwordEncoder())
                .withUser("admin")
                //.password(passwordEncoder().encode("admin"))
                .password("{noop}admin") // java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
                // Błąd ten oznacza, że musisz jawnie podać, w jaki sposób jest zakodowane twoje hasło (w Spring Security 5 zmienił się format przechowywania hasła i teraz przed hasłem trzeba podać algorytm kodowania hasła). Na potrzeby testów korzystamy z inMemoryAuthentication, a dla wygody nie będziemy kodować hasła, więc potrzebujemy przed hasłem dodać {noop}
                .authorities("ROLE_USER");
                //.roles("USER");
    }*/

/*    @Bean // niepotrzebne jeśli użyje się {noop} przy haśle
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

    /*@Override  // in Memory Auth
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
    }*/
}

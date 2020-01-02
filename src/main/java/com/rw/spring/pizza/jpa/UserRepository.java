package com.rw.spring.pizza.jpa;

import com.rw.spring.pizza.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}

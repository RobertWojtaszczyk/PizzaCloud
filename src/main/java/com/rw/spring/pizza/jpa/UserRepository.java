package com.rw.spring.pizza.jpa;

import com.rw.spring.pizza.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}

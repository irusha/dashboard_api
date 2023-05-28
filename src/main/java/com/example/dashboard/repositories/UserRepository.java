package com.example.dashboard.repositories;

import com.example.dashboard.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User getById(long id);
    List<User> getByEmail(String email);
    void deleteById(long id);
}

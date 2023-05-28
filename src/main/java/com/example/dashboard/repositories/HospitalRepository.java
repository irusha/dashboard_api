package com.example.dashboard.repositories;

import com.example.dashboard.entities.Hospital;
import org.springframework.data.repository.CrudRepository;

public interface HospitalRepository extends CrudRepository<Hospital, Long> {
    Hospital getById(long id);
    Hospital getByName(String name);
}

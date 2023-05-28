package com.example.dashboard.repositories;

import com.example.dashboard.entities.HospitalBeds;
import org.springframework.data.repository.CrudRepository;

public interface HospitalBedsRepository extends CrudRepository<HospitalBeds, Long> {
}

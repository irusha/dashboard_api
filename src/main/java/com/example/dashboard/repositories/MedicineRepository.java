package com.example.dashboard.repositories;

import com.example.dashboard.entities.Medicine;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MedicineRepository extends CrudRepository<Medicine, Long> {
    Medicine getById(long id);
    Medicine getByMedicineName(String name);
}

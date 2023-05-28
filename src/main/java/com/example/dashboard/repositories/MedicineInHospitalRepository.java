package com.example.dashboard.repositories;

import com.example.dashboard.entities.MedicineInHospital;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MedicineInHospitalRepository extends CrudRepository<MedicineInHospital, Long> {
    List<MedicineInHospital> getAllByHospitalIdOrderByQuantityDesc(long id);
    List<MedicineInHospital> getAllByMedicineId(long id);
}

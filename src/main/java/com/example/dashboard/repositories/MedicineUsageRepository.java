package com.example.dashboard.repositories;

import com.example.dashboard.entities.MedicineStock;
import com.example.dashboard.entities.MedicineUsage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MedicineUsageRepository extends CrudRepository<MedicineUsage, Integer> {
    public List<MedicineUsage> findByYearAndMonth(int year, int month);
    public List<MedicineUsage> findAllByHospitalId(long hospitalId);
    public List<MedicineUsage> findAllByHospitalIdAndYearAndMonth(long hospitalId, int year, int month);
}

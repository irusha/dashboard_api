package com.example.dashboard.repositories;

import com.example.dashboard.entities.HospitalBirths;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HospitalBirthsRepository  extends CrudRepository<HospitalBirths, Long> {
    List<HospitalBirths> getHospitalBirthsByHospitalIdOrderByDate(long hospitalId);
}

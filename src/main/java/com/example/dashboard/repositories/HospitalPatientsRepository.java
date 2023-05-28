package com.example.dashboard.repositories;

import com.example.dashboard.entities.HospitalPatients;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HospitalPatientsRepository  extends CrudRepository<HospitalPatients, Long> {
    HospitalPatients getByHospitalIdAndDate(long hospitalId, String date);
    List<HospitalPatients> findByHospitalIdOrderByDateAsc(long hospitalId);
}

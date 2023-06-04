package com.example.dashboard.repositories;
import com.example.dashboard.entities.MedicineStock;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MedicineStockRepository extends CrudRepository<MedicineStock, Long> {
    //create functions to get the data from the database
    public MedicineStock findByMedicineIdAndHospitalId(int medicineId, int hospitalId);
    public List<MedicineStock> findAllByHospitalId(int medicineId);

}

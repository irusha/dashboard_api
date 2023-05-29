package com.example.dashboard.repositories;
import com.example.dashboard.entities.MedicineStock;
import org.springframework.data.repository.CrudRepository;

public interface MedicineStockRepository extends CrudRepository<MedicineStock, Long> {
    //create functions to get the data from the database
    public MedicineStock findByMedicineIdAndHospitalId(int medicineId, int hospitalId);

}

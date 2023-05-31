package com.example.dashboard.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MedicineStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //create a primary key, medicineId, hospitalId, currentStock and typicalStock
    private long medicineId;
    private long hospitalId;
    private int currentStock;
    private int typicalStock;

    public MedicineStock() {

    }

    public MedicineStock(long medicineId, long hospitalId, int currentStock, int typicalStock) {
        this.medicineId = medicineId;
        this.hospitalId = hospitalId;
        this.currentStock = currentStock;
        this.typicalStock = typicalStock;
    }

    public long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    public int getTypicalStock() {
        return typicalStock;
    }

    public void setTypicalStock(int typicalStock) {
        this.typicalStock = typicalStock;
    }

    @Override
    public String toString() {
        return "MedicineStock{" + "medicineId=" + medicineId + ", hospitalId=" + hospitalId + ", currentStock=" + currentStock + ", typicalStock=" + typicalStock + '}';
    }
}

package com.example.dashboard.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MedicineUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int medicineId;
    private int typicalStock;
    private int hospitalId;
    private boolean isRestocking;
    private int changeInStock;
    private int year;
    private int month;
    private int date;

    public MedicineUsage() {

    }

    public MedicineUsage(int medicineId, int typicalStock, int hospitalId, boolean isRestocking, int changeInStock, int year, int month, int date) {
        this.medicineId = medicineId;
        this.typicalStock = typicalStock;
        this.hospitalId = hospitalId;
        this.isRestocking = isRestocking;
        this.changeInStock = changeInStock;
        this.year = year;
        this.month = month;
        this.date = date;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getTypicalStock() {
        return typicalStock;
    }

    public void setTypicalStock(int typicalStock) {
        this.typicalStock = typicalStock;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public boolean getIsRestocking() {
        return isRestocking;
    }

    public void setIsRestocking(boolean isRestocking) {
        this.isRestocking = isRestocking;
    }

    public int getChangeInStock() {
        return changeInStock;
    }

    public void setChangeInStock(int changeInStock) {
        this.changeInStock = changeInStock;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MedicineUsage{" +
                "medicineId=" + medicineId +
                ", typicalStock=" + typicalStock +
                ", hospitalId=" + hospitalId +
                ", isRestocking=" + isRestocking +
                ", changeInStock=" + changeInStock +
                ", year=" + year +
                ", month=" + month +
                ", date=" + date +
                '}';
    }
}

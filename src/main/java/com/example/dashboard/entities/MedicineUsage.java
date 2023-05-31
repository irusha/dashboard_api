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

    private long medicineId;
    private long hospitalId;
    private boolean isRestocking;
    private int changeInStock;
    private int year;

    public boolean isRestocking() {
        return isRestocking;
    }

    public void setRestocking(boolean restocking) {
        isRestocking = restocking;
    }

    public int getTypicalStock() {
        return typicalStock;
    }

    public void setTypicalStock(int typicalStock) {
        this.typicalStock = typicalStock;
    }

    private int month;
    private int day;
    private int typicalStock;

    public MedicineUsage() {

    }

    public MedicineUsage(long medicineId, long hospitalId, boolean isRestocking, int changeInStock, int year, int month, int day, int typicalStock) {
        this.medicineId = medicineId;
        this.hospitalId = hospitalId;
        this.isRestocking = isRestocking;
        this.changeInStock = changeInStock;
        this.year = year;
        this.month = month;
        this.day = day;
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

    public int getDay() {
        return day;
    }

    public void setDay(int date) {
        this.day = date;
    }

    @Override
    public String toString() {
        return "MedicineUsage{" +
                "medicineId=" + medicineId +
                ", hospitalId=" + hospitalId +
                ", isRestocking=" + isRestocking +
                ", changeInStock=" + changeInStock +
                ", year=" + year +
                ", month=" + month +
                ", date=" + day +
                '}';
    }
}

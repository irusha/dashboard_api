package com.example.dashboard.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class HospitalPatients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long hospitalId;
    private int admissions;
    private int discharges;
    private int opdPatients;
    private String date;

    public HospitalPatients() {

    }

    public Long getId() {
        return id;
    }

    public long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public int getAdmissions() {
        return admissions;
    }

    public void setAdmissions(int admissions) {
        this.admissions = admissions;
    }

    public int getDischarges() {
        return discharges;
    }

    public void setDischarges(int discharges) {
        this.discharges = discharges;
    }

    public int getOpdPatients() {
        return opdPatients;
    }

    public void setOpdPatients(int opdPatients) {
        this.opdPatients = opdPatients;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public HospitalPatients(long hospitalId, int admissions, int discharges, int opdPatients, String date) {
        this.hospitalId = hospitalId;
        this.admissions = admissions;
        this.discharges = discharges;
        this.opdPatients = opdPatients;
        this.date = date;
    }


    @Override
    public String toString() {
        return String.format("Hospital ID: %s, Date: %s, OPD Patients: %s", hospitalId, date, opdPatients);
    }
}

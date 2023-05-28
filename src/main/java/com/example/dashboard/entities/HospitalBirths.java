package com.example.dashboard.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class HospitalBirths {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long hospitalId;
    private int births;
    private int deaths;
    private String date;

    public HospitalBirths(long hospitalId, int births, int deaths, String date) {
        this.hospitalId = hospitalId;
        this.births = births;
        this.deaths = deaths;
        this.date = date;
    }

    public HospitalBirths() {

    }

    public Long getId() {
        return id;
    }

    public long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public int getBirths() {
        return births;
    }

    public void setBirths(int births) {
        this.births = births;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

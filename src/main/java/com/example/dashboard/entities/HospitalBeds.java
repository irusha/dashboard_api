package com.example.dashboard.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class HospitalBeds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long hospitalId;
    private int bedsAmt;
    private int availBedsAmt;
    private int icuBeds;
    private int icuBedsAvail;
    private String date;

    public HospitalBeds(int hospitalId, int bedsAmt, int availBedsAmt, int icuBeds, int icuBedsAvail, String date) {
        this.hospitalId = hospitalId;
        this.bedsAmt = bedsAmt;
        this.availBedsAmt = availBedsAmt;
        this.icuBeds = icuBeds;
        this.icuBedsAvail = icuBedsAvail;
        this.date = date;
    }

    public HospitalBeds() {

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

    public int getBedsAmt() {
        return bedsAmt;
    }

    public void setBedsAmt(int bedsAmt) {
        this.bedsAmt = bedsAmt;
    }

    public int getAvailBedsAmt() {
        return availBedsAmt;
    }

    public void setAvailBedsAmt(int availBedsAmt) {
        this.availBedsAmt = availBedsAmt;
    }

    public int getIcuBeds() {
        return icuBeds;
    }

    public void setIcuBeds(int icuBeds) {
        this.icuBeds = icuBeds;
    }

    public int getIcuBedsAvail() {
        return icuBedsAvail;
    }

    public void setIcuBedsAvail(int icuBedsAvail) {
        this.icuBedsAvail = icuBedsAvail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

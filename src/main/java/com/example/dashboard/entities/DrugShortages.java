package com.example.dashboard.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DrugShortages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long hospitalId;
    private long medicineId;
    private long noOfTimes;
    private int year;
    private int month;
    private int date;
}

package com.example.dashboard.entities;

import jakarta.persistence.*;

@Entity
@Table()
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicineName;
    private String genericName;
    private int quantity;
    private String manufacturer;
    private String dosageForm;
    private boolean prescriptionRequired;
    private String description;

    public Medicine() {

    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public boolean isPrescriptionRequired() {
        return prescriptionRequired;
    }

    public void setPrescriptionRequired(boolean prescriptionRequired) {
        this.prescriptionRequired = prescriptionRequired;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Medicine(String medicineName, String genericName, int quantity, String manufacturer, String dosageForm, boolean prescriptionRequired, String description) {
        this.medicineName = medicineName;
        this.genericName = genericName;
        this.quantity = quantity;
        this.manufacturer = manufacturer;
        this.dosageForm = dosageForm;
        this.prescriptionRequired = prescriptionRequired;
        this.description = description;
    }

    public Long getId() {
        return id;
    }
}

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
    private String manufacturer;
    private int typicalStock;
    private String dosageForm;
    private boolean prescriptionRequired;
    private String description;

    public Medicine() {

    }

    public Medicine(String medicineName, String genericName, String manufacturer, int typicalStock, String dosageForm, boolean prescriptionRequired, String description) {
        this.medicineName = medicineName;
        this.genericName = genericName;
        this.manufacturer = manufacturer;
        this.typicalStock = typicalStock;
        this.dosageForm = dosageForm;
        this.prescriptionRequired = prescriptionRequired;
        this.description = description;
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

    public int getTypicalStock() {
        return typicalStock;
    }

    public void setTypicalStock(int typicalStock) {
        this.typicalStock = typicalStock;
    }

    public Long getId() {
        return id;
    }
}

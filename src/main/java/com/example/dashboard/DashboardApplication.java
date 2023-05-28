package com.example.dashboard;

import com.example.dashboard.entities.Hospital;
import com.example.dashboard.entities.HospitalPatients;
import com.example.dashboard.entities.Medicine;
import com.example.dashboard.entities.MedicineInHospital;
import com.example.dashboard.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

@SpringBootApplication
@RestController
public class DashboardApplication {
    int randomNum = 0;
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final MedicineRepository medicineRepository;

    @Autowired
    private final MedicineInHospitalRepository medicineInHospitalRepository;

    @Autowired
    private final HospitalRepository hospitalRepository;

    @Autowired
    private final HospitalPatientsRepository hospitalPatientsRepository;

    public DashboardApplication(UserRepository userRepository, MedicineRepository medicineRepository, MedicineInHospitalRepository medicineInHospitalRepository, HospitalRepository hospitalRepository, HospitalPatientsRepository hospitalPatientsRepository) {
        this.userRepository = userRepository;
        this.medicineRepository = medicineRepository;
        this.medicineInHospitalRepository = medicineInHospitalRepository;
        this.hospitalRepository = hospitalRepository;
        this.hospitalPatientsRepository = hospitalPatientsRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(DashboardApplication.class, args);
    }

    @GetMapping("/api/medicine-stock")
    public Map<String, Map<String, String>> index(@RequestParam(value = "hospital-id", required = false) String id) {
        SortedMap<String, Map<String, String>> parentJson = new TreeMap<>();
        if (id != null) {
            List<MedicineInHospital> medicineInHospitalList = medicineInHospitalRepository.getAllByHospitalIdOrderByQuantityDesc(Long.parseLong(id));
            Hospital hospital = hospitalRepository.getById(Long.parseLong(id));
            SortedMap<String, String> json = new TreeMap<>();

            int count = 0;
            for (MedicineInHospital medicine : medicineInHospitalList) {
                if (count == 5) break;
                Medicine medicine1 = medicineRepository.getById(medicine.getMedicineId());

                json.put(medicine1.getMedicineName(), String.valueOf(medicine.getQuantity()));
                count++;
            }

            parentJson.put("pharmacyData", json);
            SortedMap<String, String> hospitalData = new TreeMap<>();
            hospitalData.put("name", hospital.getName());
            hospitalData.put("location", hospital.getLocation());
            parentJson.put("hospital-data", hospitalData);
        } else {
            SortedMap<String, String> hospitalData = new TreeMap<>();
            hospitalData.put("name", "global");
            parentJson.put("hospital-data", hospitalData);

            Iterable<Medicine> medicines = medicineRepository.findAll();
            SortedMap<String, Integer> totalMedicineMap = new TreeMap<>();

// Calculate total medicine quantities for each medicine
            for (Medicine medicine : medicines) {
                long medId = medicine.getId();
                List<MedicineInHospital> medicineInHospitalList = medicineInHospitalRepository.getAllByMedicineId(medId);
                int totalMedicine = 0;
                for (MedicineInHospital medicineInHospital : medicineInHospitalList) {
                    totalMedicine += medicineInHospital.getQuantity();
                }
                totalMedicineMap.put(medicine.getMedicineName(), totalMedicine);
            }


            List<Map.Entry<String, Integer>> sortedMedicineList = new ArrayList<>(totalMedicineMap.entrySet());
            sortedMedicineList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));


            SortedMap<String, String> json = new TreeMap<>();
            int count = 0;
            for (Map.Entry<String, Integer> entry : sortedMedicineList) {
                if (count >= 5) {
                    break;  // Stop after the first 5 highest medicines
                }
                String medicineName = entry.getKey();
                String totalMedicine = String.valueOf(entry.getValue());
                json.put(medicineName, totalMedicine);
                count++;
            }

            System.out.println(json);
            System.out.println(sortedMedicineList);
            parentJson.put("pharmacyData", json);

        }
        return parentJson;
    }

    public Map<String, String> createHospitalPatientsObject(String opd, String discharges, String admissions) {
        Map<String, String> retVal = new HashMap<>();
        retVal.put("opd", opd);
        retVal.put("discharges", discharges);
        retVal.put("admissions", admissions);
        return retVal;
    }

    @GetMapping("api/patients")
    public Map<String, Map<String, String>> patients(@RequestParam("hospitalId") long id, @RequestParam(value = "from", required = false) String from, @RequestParam(value = "to", required = false) String to) {
        List<HospitalPatients> hospitalPatientsList = hospitalPatientsRepository.findByHospitalIdOrderByDateAsc(id);
        SortedMap<String, Map<String, String>> retVal = new TreeMap<>();

        if (from != null && to != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd");
            LocalDate fromDate = LocalDate.parse(from, formatter);
            LocalDate toDate = LocalDate.parse(to, formatter);

            List<LocalDate> dates = new ArrayList<>();
            dates.add(fromDate);
            do {
                fromDate = fromDate.plusDays(1);
                dates.add(fromDate);
            } while (!fromDate.isEqual(toDate));

            for (HospitalPatients hospitalPatients : hospitalPatientsList) {
                if (dates.contains(LocalDate.parse(hospitalPatients.getDate(), formatter))) {
                    Map<String, String> patientInfo = createHospitalPatientsObject(String.valueOf(hospitalPatients.getOpdPatients()), String.valueOf(hospitalPatients.getDischarges()), String.valueOf(hospitalPatients.getAdmissions()));

                    retVal.put(hospitalPatients.getDate(), patientInfo);
                }
            }

        } else {
            for (HospitalPatients hospitalPatients : hospitalPatientsList) {
                Map<String, String> patientInfo = createHospitalPatientsObject(String.valueOf(hospitalPatients.getOpdPatients()), String.valueOf(hospitalPatients.getDischarges()), String.valueOf(hospitalPatients.getAdmissions()));

                retVal.put(hospitalPatients.getDate(), patientInfo);
            }
        }

        return retVal;
    }

    @GetMapping("api/births")
    public Map<String, Map<String, String>> births(@RequestParam("hospitalId") long id) {
        return new HashMap<>();
    }
}


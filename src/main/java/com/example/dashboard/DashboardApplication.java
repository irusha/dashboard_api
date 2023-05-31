package com.example.dashboard;

import com.example.dashboard.entities.*;
import com.example.dashboard.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "http://localhost:4200")
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

    @Autowired
    private final MedicineUsageRepository medicineUsageRepository;

    @Autowired
    private final MedicineStockRepository medicineStockRepository;

    public DashboardApplication(UserRepository userRepository, MedicineRepository medicineRepository, MedicineInHospitalRepository medicineInHospitalRepository, HospitalRepository hospitalRepository, HospitalPatientsRepository hospitalPatientsRepository, MedicineUsageRepository medicineUsageRepository, MedicineStockRepository medicineStockRepository) {
        this.userRepository = userRepository;
        this.medicineRepository = medicineRepository;
        this.medicineInHospitalRepository = medicineInHospitalRepository;
        this.hospitalRepository = hospitalRepository;
        this.hospitalPatientsRepository = hospitalPatientsRepository;
        this.medicineUsageRepository = medicineUsageRepository;
        this.medicineStockRepository = medicineStockRepository;
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

    @GetMapping("api/hospitals")
    public List<Map<String, String>> hospitals() {
        List<Map<String, String>> retVal = new ArrayList<>();
        Iterable<Hospital> hospitals = hospitalRepository.findAll();
        for (Hospital hospital : hospitals) {
            Map<String, String> tempVal = new HashMap<>();
            tempVal.put("hospitalName", hospital.getName());
            tempVal.put("hospitalId", String.valueOf(hospital.getId()));
            retVal.add(tempVal);
        }

        return retVal;
    }

    @GetMapping("api/shortages")
    public String shortages(@RequestParam("month") int month, @RequestParam("day") int day, @RequestParam("year") int year, @RequestParam("drug-id") int drug) {
        return "";
    }

    public Map<Long, Double> getFirstThree(Map<Long, Double> medicinePercentages) {
        List<Map.Entry<Long, Double>> first3Values = medicinePercentages.entrySet().stream()
                .limit(3)
                .collect(Collectors.toList());

        Map<Long, Double> retVal = new HashMap<>();

        for (Map.Entry<Long, Double> entry : first3Values) {
            retVal.put(entry.getKey(), entry.getValue());
        }
        return retVal;
    }

    private int[] getPreviousMonthAndYear(int month, int year) {
        int[] retVal = new int[2];
        int prevMonth = month - 1;
        int prevYear = year;
        if (prevMonth == 0) {
            prevMonth = 12;
            prevYear = year - 1;
        }
        retVal[0] = prevMonth;
        retVal[1] = prevYear;

        return retVal;
    }

    @GetMapping("api/demanded")
    public Map<String, Map<String, Integer>> getDemandedMedicines(@RequestParam(value = "hospitalId", required = false) Long hospitalId, @RequestParam("year") int year, @RequestParam("month") int month) {
        Map<String, Map<String, Integer>> retVal = new HashMap<>();

        int prevMonth = month;
        int prevYear = year;

        int numberOfPrevMonths = 3;

        int[] years = new int[numberOfPrevMonths];
        int[] months = new int[numberOfPrevMonths];

        for (int i = 0; i < numberOfPrevMonths; i++) {
            int tempPrevMonth = getPreviousMonthAndYear(prevMonth, prevYear)[0];
            int tempPrevYear = getPreviousMonthAndYear(prevMonth, prevYear)[1];

            years[i] = tempPrevYear;
            months[i] = tempPrevMonth;
            prevMonth = tempPrevMonth;
            prevYear = tempPrevYear;

        }

        if (hospitalId != null) {
            Map<Long, Double> medicinePercentages = new HashMap<>();

            for (int i = 0; i < numberOfPrevMonths; i++) {
                for (Map.Entry<Long, Double> entry : getRestockPercentagesForMonth(years[i], months[i], hospitalId).entrySet()) {
                    long medicineId = entry.getKey();
                    double percentage = entry.getValue();
                    double prevPercentage = medicinePercentages.get(medicineId) == null ? 0 : medicinePercentages.get(medicineId);
                    medicinePercentages.put(medicineId, prevPercentage + percentage);
                }
            }

            Map<Long, Double> demandedMedicines = getFirstThree(sortByValue(medicinePercentages));
            for (int i = 0; i < numberOfPrevMonths; i++) {
                Map<Long, Double> medicineDemand = getRestockPercentagesForMonth(years[i], months[i], hospitalId);
                Map<String, Integer> medicineUsage = new HashMap<>();
                for (Map.Entry<Long, Double> entry : demandedMedicines.entrySet()) {
                    long medicineId = entry.getKey();
                    String medicineName = medicineRepository.findById(medicineId).get().getMedicineName();
                    medicineUsage.put(medicineName, (int) Math.round(medicineDemand.get(medicineId) == null ? 0 : medicineDemand.get(medicineId)));
                }
                retVal.put(getMonthName(months[i]), medicineUsage);
            }
        }

        else {
            Map<Long, Double> medicinePercentages = new HashMap<>();

        }

        return retVal;
    }

    public String getMonthName(int month) {
        String monthName = "";
        switch (month) {
            case 1:
                monthName = "January";
                break;
            case 2:
                monthName = "February";
                break;
            case 3:
                monthName = "March";
                break;
            case 4:
                monthName = "April";
                break;
            case 5:
                monthName = "May";
                break;
            case 6:
                monthName = "June";
                break;
            case 7:
                monthName = "July";
                break;
            case 8:
                monthName = "August";
                break;
            case 9:
                monthName = "September";
                break;
            case 10:
                monthName = "October";
                break;
            case 11:
                monthName = "November";
                break;
            case 12:
                monthName = "December";
        }
        return monthName;
    }

    public Map<Long, Double> getRestockPercentagesForMonth(int year, int month, long hospitalId) {
        medicineUsageRepository.findAllByHospitalIdAndYearAndMonth(hospitalId, year, month);
        Map<Long, Double> medicinePercentages = new HashMap<>();

        for (MedicineUsage medicineUsage : medicineUsageRepository.findAllByHospitalIdAndYearAndMonth(hospitalId, year, month)) {
            long medicineId = medicineUsage.getMedicineId();
            double percentage = (medicineUsage.getChangeInStock() / (double) medicineUsage.getTypicalStock()) * 100;
            double prevPercentage = medicinePercentages.get(medicineId) == null ? 0 : medicinePercentages.get(medicineId);
            medicinePercentages.put(medicineId, prevPercentage + percentage);
        }
        return medicinePercentages;
    }

    //sort Map<Long, Double> by value
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());

        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        Map<K, V> result = new LinkedHashMap<>();

        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    @GetMapping("api/feedData")
    public String restockData(@RequestParam("month") int month) {
        Iterable<Hospital> hospitals = hospitalRepository.findAll();

        for (Hospital hospital : hospitals) {
            long hospitalId = hospital.getId();
            // select 3 - 5 random medicines and iterate over each medicine
            int randomMedicineCount = (int) (Math.random() * 10) + 5;
            List<Medicine> randomMedicines = new ArrayList<>();
            for (int i = 0; i < randomMedicineCount; i++) {
                int randomMedicineIndex = (int) (Math.random() * 78) + 10;
                randomMedicines.add(medicineRepository.getById(randomMedicineIndex));
            }

            for (Medicine medicine : randomMedicines) {
                long medicineId = medicine.getId();
                // select 3 - 5 random dates and iterate over each date
                int randomDateCount = (int) (Math.random() * 2) + 1;
                for (int i = 0; i < randomDateCount; i++) {
                    int randomDay = (int) (Math.random() * 28) + 1;
                    int typicalStock = medicine.getTypicalStock();
                    int randomQuantity = (int) (Math.random() * typicalStock);

                    MedicineUsage medicineUsage = new MedicineUsage(medicineId, hospitalId, true, randomQuantity, 2023, month, randomDay, typicalStock);
                    medicineUsageRepository.save(medicineUsage);


                }
            }
        }
        return "success";
    }
}



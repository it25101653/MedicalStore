package com.example.medicalstore.medicine;

import org.springframework.stereotype.Service;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MedicineService {

    private static final String FILE = "data/medicines.txt";

    public List<Medicine> getAllMedicines() {
        List<Medicine> list = new ArrayList<>();
        File f = new File(FILE);
        if (!f.exists()) return list;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null)
                if (!line.trim().isEmpty())
                    list.add(Medicine.fromFileString(line.trim()));
        } catch (IOException e) { e.printStackTrace(); }
        return list;
    }


    /** Search by name OR category (case-insensitive). */
    public List<Medicine> search(String query) {
        String q = query.toLowerCase();
        return getAllMedicines().stream()
                .filter(m -> m.getName().toLowerCase().contains(q)
                        || m.getCategory().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public Medicine findById(String medId) {
        return getAllMedicines().stream()
                .filter(m -> m.getMedId().equals(medId))
                .findFirst().orElse(null);
    }

    /** Add a new medicine (admin action). */
    public void add(OTCMedicine m) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE, true))) {
            bw.write(m.toFileString()); bw.newLine();
        } catch (IOException e) { e.printStackTrace(); }
    }

    /** Admin edit: update price, stock, and description. */
    public void update(String medId, double newPrice, int newStock, String newDescription) {
        List<Medicine> list = getAllMedicines();
        for (Medicine m : list)
            if (m.getMedId().equals(medId)) {
                m.setPrice(newPrice);
                m.setStock(newStock);
                m.setDescription(newDescription);
            }
        saveAll(list);
    }

    public void delete(String medId) {
        List<Medicine> list = getAllMedicines().stream()
                .filter(m -> !m.getMedId().equals(medId))
                .collect(Collectors.toList());
        saveAll(list);
    }

    private void saveAll(List<Medicine> list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE, false))) {
            for (Medicine m : list) { bw.write(m.toFileString()); bw.newLine(); }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public String generateId() {
        return "MED" + System.currentTimeMillis();
    }
}

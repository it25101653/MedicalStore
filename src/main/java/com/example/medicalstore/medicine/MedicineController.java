package com.example.medicalstore.medicine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    /** Browse page – card grid with search bar. No add-medicine form here. */
    @GetMapping("/medicines")
    public String listMedicines(@RequestParam(required = false) String search,
                                Model model) {
        List<Medicine> list = (search != null && !search.isEmpty())
                ? medicineService.search(search)
                : medicineService.getAllMedicines();
        model.addAttribute("medicines", list);
        model.addAttribute("search", search);
        return "medicines";
    }

    /** Detail page for a single medicine. */
    @GetMapping("/medicines/view")
    public String viewMedicine(@RequestParam String id, Model model) {
        Medicine med = medicineService.findById(id);
        if (med == null) return "redirect:/medicines";
        model.addAttribute("med", med);
        return "medicine-detail";
    }

    /** Admin-only: add medicine (POST from admin panel). */
    @PostMapping("/medicines/add")
    public String addMedicine(@RequestParam String name,
                              @RequestParam String category,
                              @RequestParam double price,
                              @RequestParam int stock,
                              @RequestParam(defaultValue = "No description available.") String description) {
        OTCMedicine m = new OTCMedicine(
                medicineService.generateId(), name, category, price, stock, description);
        medicineService.add(m);
        return "redirect:/medicines";
    }

    /** Admin-only: show edit form. */
    @GetMapping("/medicines/edit")
    public String editForm(@RequestParam String id, Model model) {
        model.addAttribute("med", medicineService.findById(id));
        return "medicine-edit";
    }

    /** Admin-only: process edit. */
    @PostMapping("/medicines/edit")
    public String doEdit(@RequestParam String medId,
                         @RequestParam double price,
                         @RequestParam int stock,
                         @RequestParam(defaultValue = "No description available.") String description) {
        medicineService.update(medId, price, stock, description);
        return "redirect:/medicines";
    }

    /** Admin-only: delete. */
    @GetMapping("/medicines/delete")
    public String doDelete(@RequestParam String id) {
        medicineService.delete(id);
        return "redirect:/medicines";
    }
}

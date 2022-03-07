package com.revature.controller;

import com.revature.model.Doctor;
import com.revature.model.Patient;
import com.revature.model.Pharmacist;
import com.revature.model.User;
import com.revature.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/newUser")
    public String loadNewUser(){
        return "register_user";
    }

    @GetMapping("/newPatient")
    public String newPatientForm(Model model){
        User user = new User();
        Patient patient = new Patient();
        model.addAttribute("user", user);
        model.addAttribute("patient", patient);
        return "patient/new_patient";
    }

    @PostMapping("/newPatient")
    public String createNewPatient(@ModelAttribute("user") User user, @ModelAttribute("patient") Patient patient){
        userService.createPatient(user, patient);
        return "register_success";
    }

    @GetMapping("/newDoctor")
    public String newDoctorForm(Model model){
        User user = new User();
        Doctor doctor = new Doctor();
        model.addAttribute("user", user);
        model.addAttribute("doctor", doctor);
        return "doctor/new_doctor";
    }

    @PostMapping("/newDoctor")
    public String createNewDoctor(@ModelAttribute("user") User user, @ModelAttribute("doctor") Doctor doctor){
        userService.createDoctor(user, doctor);
        return "register_success";
    }

    @GetMapping("/newPharmacist")
    public String newPharmacistForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "pharmacist/new_pharmacist";
    }

    @PostMapping("/newPharmacist")
    public String createNewPharmacist(@ModelAttribute("user") User user, Pharmacist pharmacist){
        userService.createPharmacist(user, pharmacist);
        return "register_success";
    }

}

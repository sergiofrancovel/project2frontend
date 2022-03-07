package com.revature.controller;

import com.revature.dto.DoctorDTO;
import com.revature.dto.PatientDTO;
import com.revature.model.Email;
import com.revature.service.DoctorService;
import com.revature.service.PatientService;
import com.revature.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
public class PatientController {

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final UserService userService;

    @Autowired
    public PatientController(PatientService patientService, DoctorService doctorService, UserService userService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.userService = userService;
    }

    @GetMapping("/patient/{patientId}")
    public String loadPatientHome(Model model, @PathVariable Integer patientId){
        PatientDTO patientDTO = patientService.getPatientById(patientId);
        model.addAttribute("patientDTO", patientDTO);
        return "patient/patient_home";
    }

    @GetMapping("/patient/{patientId}/contactPhysician")
    public String loadEmailForm(Model model, @PathVariable Integer patientId){
        Email email = new Email();
        PatientDTO patientDTO = patientService.getPatientById(patientId);
        DoctorDTO doctorDTO = new DoctorDTO();
        model.addAttribute("email", email);
        model.addAttribute("doctorDTO", doctorDTO);
        model.addAttribute("patientDTO", patientDTO);
        return "patient/contact_doctor";
    }

    @PostMapping("/patient/{patientId}/contactPhysician")
    public ResponseEntity<Email> sendEmail(@PathVariable Integer patientId,
                                           String firstName, String lastName){
        PatientDTO patientDTO = patientService.getPatientById(patientId);
        DoctorDTO doctorDTO = doctorService.getDoctorByName(firstName, lastName);
        patientService.contactPhysician(patientDTO, doctorDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("patient/{patientId}/updateInformation")
    public String loadInformationUpdateForm(Model model, @PathVariable Integer patientId){
        PatientDTO patientDTO = patientService.getPatientById(patientId);
        PatientDTO patientData = new PatientDTO();
        model.addAttribute("patientDTO", patientDTO);
        model.addAttribute("patientData", patientData);
        return "patient/update_info";
    }

    @PatchMapping(value = "patient/{patientId}/updateInformation")
    public String updateInformation(@ModelAttribute("patientData") PatientDTO patientData,
                                    @PathVariable Integer patientId, String email, Long phoneNumber,
                                    String primaryDoctor){

        PatientDTO getPatient = patientService.getPatientById(patientId);
        BeanUtils.copyProperties(getPatient, patientData);

        if (!Objects.equals(email, "")){
            patientService.updateEmail(getPatient.getId(), email);
            userService.updateEmail(getPatient.getId(), email);
        }

        if (phoneNumber != null)
            patientService.updatePhoneNumber(getPatient.getId(), phoneNumber);

        if (!Objects.equals(primaryDoctor, ""))
            patientService.updatePrimaryDoctor(getPatient.getId(), primaryDoctor);

        return "patient/updated_info";
    }
}

package com.revature.controller;

import com.revature.dto.DoctorDTO;
import com.revature.dto.PatientDTO;
import com.revature.model.Prescription;
import com.revature.model.Status;
import com.revature.service.DoctorService;
import com.revature.service.PatientService;
import com.revature.service.PrescriptionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@Controller
public class PharmacistController {

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final PrescriptionService prescriptionService;

    @Autowired
    public PharmacistController(DoctorService doctorService, PatientService patientService,
                                PrescriptionService prescriptionService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.prescriptionService = prescriptionService;
    }

    @GetMapping("/pharmacy")
    public String prescriptionRequestsList(Model model){
        Set<Prescription> unapprovedPrescriptions = prescriptionService.getAllUncheckedPrescriptions();
        model.addAttribute("unapprovedPrescriptions", unapprovedPrescriptions);
        return "pharmacist/pharmacist_home";
    }

    @GetMapping("/pharmacy/approveRequests/{prescriptionId}")
    public String loadPrescriptionRequest(Model model, @PathVariable Integer prescriptionId){
        Prescription prescription = prescriptionService.getPrescriptionById(prescriptionId);
        DoctorDTO doctorDTO = doctorService.getDoctorById(prescription.getDoctorId());
        PatientDTO patientDTO = patientService.getPatientById(prescription.getPatientId());
        model.addAttribute("prescription", prescription);
        model.addAttribute("doctorDTO", doctorDTO);
        model.addAttribute("patientDTO", patientDTO);
        return "pharmacist/prescription_approval";
    }

    @PatchMapping("/pharmacy/approveRequests/{prescriptionId}")
    public String approvePrescriptionRequest(@ModelAttribute("prescription") Prescription prescription,
                                             @PathVariable Integer prescriptionId, Status status){
        Prescription getPrescription = prescriptionService.getPrescriptionById(prescriptionId);
        prescriptionService.updateStatus(getPrescription.getId(), status);
        BeanUtils.copyProperties(getPrescription, prescription);
        return "redirect:/pharmacy";
    }
}

package com.revature.controller;

import com.revature.dto.DoctorDTO;
import com.revature.dto.EmailAppointmentDTO;
import com.revature.dto.EmailPrescriptionDTO;
import com.revature.dto.PatientDTO;
import com.revature.model.Appointment;
import com.revature.model.Prescription;
import com.revature.service.DoctorService;
import com.revature.service.PatientService;
import com.revature.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class DoctorController {

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final PrescriptionService prescriptionService;

    @Autowired
    RestTemplate restTemplate;

//    @Value("${api.config.email-url:http:localhost:4001/email}")
//    private String url;

    @Autowired
    public DoctorController(DoctorService doctorService, PatientService patientService,
                            PrescriptionService prescriptionService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.prescriptionService = prescriptionService;
    }

    @GetMapping("/doctor/{doctorId}")
    public String loadDoctorHome(Model model, @PathVariable Integer doctorId){
        DoctorDTO doctorDTO = doctorService.getDoctorById(doctorId);
        model.addAttribute("doctorDTO", doctorDTO);
        return "doctor/doctor_home";
    }

    @GetMapping("/doctor/{doctorId}/patientSearch")
    public String loadPatientSearchForm(Model model, @PathVariable Integer doctorId){
        DoctorDTO doctorDTO = doctorService.getDoctorById(doctorId);
        PatientDTO patientDTO = new PatientDTO();
        model.addAttribute("doctorDTO", doctorDTO);
        model.addAttribute("patientDTO", patientDTO);
        return "doctor/patient_search";
    }

    @PostMapping(value = "/doctor/{doctorId}/patientSearch")
    public String getPatientData(Model model, @ModelAttribute("patientDTO") PatientDTO patientDTO,
                                 @PathVariable Integer doctorId) {
        DoctorDTO doctorDTO = doctorService.getDoctorById(doctorId);
        PatientDTO patientData = patientService.getPatientByName(patientDTO.getFirstName(), patientDTO.getLastName());
        model.addAttribute("doctorDTO", doctorDTO);
        model.addAttribute("patientData", patientData);
        return "doctor/patient_search_result";
    }

    @GetMapping("/doctor/{doctorId}/pharmacyRequest")
    public String newPharmacyRequestForm(Model model, @PathVariable Integer doctorId){
        DoctorDTO doctorDTO = doctorService.getDoctorById(doctorId);
        PatientDTO patientDTO = new PatientDTO();
        Prescription prescription = new Prescription();
        model.addAttribute("doctorDTO", doctorDTO);
        model.addAttribute("patientDTO", patientDTO);
        model.addAttribute("prescription", prescription);
        return "doctor/pharmacy_request";
    }

    @PostMapping("/doctor/{doctorId}/pharmacyRequest")
    public String pharmacyRequest(@PathVariable Integer doctorId,
                                  @ModelAttribute("prescription") Prescription prescription,
                                  @ModelAttribute("patientDTO") PatientDTO patientDTO){
        prescriptionService.prescriptionRequest(prescription, doctorId, patientDTO.getFirstName(),
                patientDTO.getLastName());

        PatientDTO patient = patientService.getPatientByName(patientDTO.getFirstName(),patientDTO.getLastName());
        EmailPrescriptionDTO dto = new EmailPrescriptionDTO(patient.getPrimaryDoctor(), patient.getFirstName(), prescription.getMedicineName(),
                prescription.getDosage(), prescription.getStatus(), patient.getEmail());
        restTemplate.postForEntity("", dto, null);
        return "prescription_success";
    }

    @GetMapping("/doctor/{doctorId}/scheduleAppointment")
    public String newAppointmentForm(Model model, @PathVariable Integer doctorId){
        DoctorDTO doctorDTO = doctorService.getDoctorById(doctorId);
        PatientDTO patientDTO = new PatientDTO();
        Appointment appointment = new Appointment();
        model.addAttribute("doctorDTO", doctorDTO);
        model.addAttribute("patientDTO", patientDTO);
        model.addAttribute("appointment", appointment);
        return "doctor/schedule_appointment";
    }

    @PostMapping("/doctor/{doctorId}/scheduleAppointment")
    public String createNewAppointment(@PathVariable Integer doctorId,
                                       @ModelAttribute("appointment") Appointment appointment,
                                       @ModelAttribute("patientDTO") PatientDTO patientDTO) {
        doctorService.createNewAppointment(appointment, doctorId, patientDTO.getFirstName(),
                patientDTO.getLastName());
        PatientDTO patient = patientService.getPatientByName(patientDTO.getFirstName(),patientDTO.getLastName());
        EmailAppointmentDTO dto = new EmailAppointmentDTO(patient.getEmail(), patient.getPrimaryDoctor(),
                patient.getFirstName(), patient.getLastName(), appointment.getAppointmentTime(), appointment.getAppointmentDate());
        restTemplate.postForEntity("", dto, null);

        return "appointment_success";
    }
}

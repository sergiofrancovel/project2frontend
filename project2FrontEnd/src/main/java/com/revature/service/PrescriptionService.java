package com.revature.service;

import com.revature.dao.DoctorRepository;
import com.revature.dao.PatientRepository;
import com.revature.dao.PrescriptionRepository;
import com.revature.model.Doctor;
import com.revature.model.Patient;
import com.revature.model.Prescription;
import com.revature.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PrescriptionService {

    private PrescriptionRepository prescriptionRepository;
    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;

    @Autowired
    public void setPrescriptionRepository(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    @Autowired
    public void setDoctorRepository(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Autowired
    public void setPatientRepository(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void prescriptionRequest(Prescription prescription, Integer doctorId, String firstName, String lastName){
        Doctor doctor = doctorRepository.getById(doctorId);
        Patient patient = patientRepository.getPatientByFirstNameAndLastName(firstName, lastName);

        prescription.setDoctorId(doctor.getId());
        prescription.setPatientId(patient.getId());
        prescription.setStatus(Status.PENDING);

        prescriptionRepository.save(prescription);
    }

    public Prescription getPrescriptionById(Integer id){
        return prescriptionRepository.getById(id);
    }

    public Set<Prescription> getAllUncheckedPrescriptions() {
        return prescriptionRepository.getAllUnCheckedPrescriptions();
    }

    public void updateStatus(Integer id, Status status){
        prescriptionRepository.updateStatus(id, status);
    }
}

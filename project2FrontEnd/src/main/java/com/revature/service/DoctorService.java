package com.revature.service;

import com.revature.dao.AppointmentRepository;
import com.revature.dao.DoctorRepository;
import com.revature.dao.PatientRepository;
import com.revature.dto.DoctorDTO;
import com.revature.model.Appointment;
import com.revature.model.Doctor;
import com.revature.model.Patient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;
    private AppointmentRepository appointmentRepository;

    @Autowired
    public void setDoctorRepository(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Autowired
    public void setPatientRepository(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Autowired
    public void setAppointmentRepository(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public DoctorDTO getDoctorById(Integer id){
        Doctor doctor = doctorRepository.getById(id);
        return copyToDTO(doctor);
    }

    public DoctorDTO getDoctorByName(String firstName, String lastName){
        Doctor doctor = doctorRepository.getDoctorByFirstNameAndLastName(firstName, lastName);
        return copyToDTO(doctor);
    }

    public void createNewAppointment(Appointment appointment, Integer doctorId, String firstName, String lastName){
        Doctor doctor = doctorRepository.getById(doctorId);
        Patient patient = patientRepository.getPatientByFirstNameAndLastName(firstName, lastName);

        appointment.setDoctorId(doctor.getId());
        appointment.setPatientId(patient.getId());

        appointmentRepository.save(appointment);
    }

    private DoctorDTO copyToDTO(Doctor doctor){
        DoctorDTO doctorDTO = new DoctorDTO();
        BeanUtils.copyProperties(doctor, doctorDTO);
        return doctorDTO;
    }
}

package com.revature.service;

import com.revature.dao.DoctorRepository;
import com.revature.dao.PatientRepository;
import com.revature.dao.PharmacistRepository;
import com.revature.dao.UserRepository;
import com.revature.dto.LoginDTO;
import com.revature.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * This class serves as a service for adding users to the user database and their respective databases based on their
 * role.
 * @author Joseph Barr
 */
@Service
public class UserService {

    private UserRepository userRepository;
    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;
    private PharmacistRepository pharmacistRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPatientRepository(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Autowired
    public void setDoctorRepository(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Autowired
    public void setPharmacistRepository(PharmacistRepository pharmacistRepository) {
        this.pharmacistRepository = pharmacistRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Creates a new user for the user database
     * @param user - The new user
     * @param role - The user's role
     */
    private void createUser(User user, Role role){
        user.setRole(role);
        String encoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(encoded);
        userRepository.save(user);
    }


    /**
     * Creates a new patient and adds them to the user and patient databases
     * @param user - The user to be added
     * @param patient - The patient to be added
     */
    public void createPatient(User user, Patient patient){
        createUser(user, Role.PATIENT);
        BeanUtils.copyProperties(user, patient);
        patientRepository.save(patient);
    }

    /**
     * Creates a new doctor and adds them to the user and doctor databases
     * @param user - The user to be added
     * @param doctor - The doctor to be added
     */
    public void createDoctor(User user, Doctor doctor){
        createUser(user, Role.PHYSICIAN);
        BeanUtils.copyProperties(user, doctor);
        doctorRepository.save(doctor);
    }

    /**
     * Creates a new pharmacist and adds them to the user and pharmacist databases
     * @param user - The user to be added
     * @param pharmacist - The pharmacist to be added
     */
    public void createPharmacist(User user, Pharmacist pharmacist){
        createUser(user, Role.PHARMACIST);
        BeanUtils.copyProperties(user, pharmacist);
        pharmacistRepository.save(pharmacist);
    }

    /**
     * Retrieves a user's email for login
     * @param email - The user's email
     * @return - LoginDTO
     */
    public LoginDTO getUserByUsername(String email){
        LoginDTO loginDTO = new LoginDTO();
        User user = userRepository.getUserByEmail(email);
        BeanUtils.copyProperties(user, loginDTO);
        return loginDTO;
    }

    /**
     * Updates the user's email in the database
     * @param email - The new email
     */
    public void updateEmail(Integer id, String email){
        userRepository.updateEmail(id, email);
    }
}

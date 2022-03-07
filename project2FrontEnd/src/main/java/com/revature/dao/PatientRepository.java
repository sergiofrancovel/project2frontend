package com.revature.dao;

import com.revature.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Set;

@Repository
@Transactional
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Query("from Patient p order by p.lastName")
    Set<Patient> getAllPatients();

    @Query("FROM Patient p where p.firstName = :firstName and  p.lastName = :lastName")
    Patient getPatientByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Modifying
    @Query("update Patient  p set p.email = :email where p.id = :patientId")
    void updateEmail(@Param("patientId") Integer patientId, @Param("email") String email);

    @Modifying
    @Query("update Patient  p set p.phoneNumber = :phoneNumber where p.id = :patientId")
    void updatePhoneNumber(@Param("patientId") Integer patientId, @Param("phoneNumber") Long phoneNumber);

    @Modifying
    @Query("update Patient  p set p.primaryDoctor = :primaryDoctor where p.id = :patientId")
    void updatePrimaryDoctor(@Param("patientId") Integer patientId, @Param("primaryDoctor") String primaryDoctor);

}
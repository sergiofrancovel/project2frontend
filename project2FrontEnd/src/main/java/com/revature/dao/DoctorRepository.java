package com.revature.dao;

import com.revature.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    @Query("from Doctor d where d.firstName = :firstName and d.lastName = :lastName")
    Doctor getDoctorByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query("from Doctor d order by d.lastName")
    Set<Doctor> getAllDoctors();
}
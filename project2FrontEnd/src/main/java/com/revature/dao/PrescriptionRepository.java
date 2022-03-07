package com.revature.dao;

import com.revature.model.Prescription;
import com.revature.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Set;

@Repository
@Transactional
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {

    @Query("FROM Prescription p where p.status = 'PENDING'")
    Set<Prescription> getAllUnCheckedPrescriptions();

    @Modifying
    @Query("update Prescription p set p.status = :status where p.id = :prescriptionId")
    void updateStatus(@Param("prescriptionId") Integer prescriptionId, @Param("status")Status status);
}
package com.revature.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "prescription")
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "medicine_name", nullable = false)
    private String medicineName;

    @Column(name = "cc", nullable = false)
    private Integer dosage;

    @Column(name = "requesting_doctor", nullable = false)
    private Integer doctorId;

    @Column(name = "patient_for", nullable = false)
    private Integer patientId;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
}
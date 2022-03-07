package com.revature.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailAppointmentDTO {
    private String patientEmail;
    private String doctorfn;

    private String patientfn;
    private String patientln;
    private String schedule;
    private Date appointmentTime;
}

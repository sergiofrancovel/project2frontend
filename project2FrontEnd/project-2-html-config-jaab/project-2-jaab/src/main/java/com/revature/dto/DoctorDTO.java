package com.revature.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class DoctorDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
}

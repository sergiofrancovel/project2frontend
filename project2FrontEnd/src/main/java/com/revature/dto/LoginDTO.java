package com.revature.dto;

import com.revature.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private Integer id;
    private String email;
    private String password;
    private Role role;
}

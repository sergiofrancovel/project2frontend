package com.revature.service;

import com.revature.dto.LoginDTO;
import com.revature.principal.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LoginDTO loginDTO = userService.getUserByUsername(username);

        if (loginDTO == null)
            throw new UsernameNotFoundException("User not found.");

        return new UserPrincipal(loginDTO);
    }
}

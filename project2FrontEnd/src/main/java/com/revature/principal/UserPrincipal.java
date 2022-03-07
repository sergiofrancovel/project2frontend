package com.revature.principal;

import com.revature.dto.LoginDTO;
import com.revature.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserPrincipal implements UserDetails {

    private final LoginDTO loginDTO;

    public UserPrincipal(LoginDTO loginDTO) {
        this.loginDTO = loginDTO;
    }

    /**
     * Loads the roles for user authentication
     * @return - user roles
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("PATIENT"));
        authorities.add(new SimpleGrantedAuthority("PHYSICIAN"));
        authorities.add(new SimpleGrantedAuthority("PHARMACIST"));
        return authorities;
    }

    @Override
    public String getPassword() {
        return loginDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return loginDTO.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public int getUserId() {
        return loginDTO.getId();
    }

    public Role getRole() {
        return loginDTO.getRole();
    }
}

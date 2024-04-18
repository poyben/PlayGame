package es.rgs.playgame.repository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

public interface CustomUserDetails extends Serializable {

    Collection<? extends GrantedAuthority> getAuthorities();


    String getPassword();

    String getUsername();

    String getEmail();
    String getFirstname();
    String getLastname();



    boolean isAccountNonExpired();


    boolean isAccountNonLocked();


    boolean isCredentialsNonExpired();


    boolean isEnabled();
}

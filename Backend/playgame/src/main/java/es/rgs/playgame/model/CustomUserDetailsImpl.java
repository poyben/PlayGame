package es.rgs.playgame.model;

import es.rgs.playgame.repository.CustomUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetailsImpl implements CustomUserDetails {

    private final Usuario usuario;

    public CustomUserDetailsImpl(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Rol rol = usuario.getRol(); // Obtener el rol del usuario
        if (rol != null) {
            // Devolver una lista con una sola autoridad correspondiente al rol del usuario
            return Collections.singletonList(new SimpleGrantedAuthority(rol.getName()));
        } else {
            // Si el usuario no tiene un rol asignado, devolver una lista vacía
            return Collections.emptyList();
        }
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getUsername();
    }

    @Override
    public String getEmail() {
        return usuario.getEmail();
    }

    @Override
    public String getFirstname() {
        return usuario.getFirstname();
    }

    @Override
    public String getLastname() {
        return usuario.getLastname();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implementa la lógica según sea necesario
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implementa la lógica según sea necesario
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implementa la lógica según sea necesario
    }

    @Override
    public boolean isEnabled() {
        return true; // Implementa la lógica según sea necesario
    }
}
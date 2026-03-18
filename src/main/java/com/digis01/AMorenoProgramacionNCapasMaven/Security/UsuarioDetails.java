package com.digis01.AMorenoProgramacionNCapasMaven.Security;

import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Usuario;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioDetails implements UserDetails {

    private Usuario usuario;

    public UsuarioDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String getUsername() {
        return usuario.getUserName(); 
    }

    @Override
    public String getPassword() {
        return usuario.getPassword(); 
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> usuario.getRol().getNombre());
    }

    @Override
    public boolean isEnabled() {
        return usuario.getEstatus() == 1;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return usuario.getEstatus() == 1; }
    @Override public boolean isCredentialsNonExpired() { return true; }
}

package com.digis01.AMorenoProgramacionNCapasMaven.Services;

import com.digis01.AMorenoProgramacionNCapasMaven.DAO.UsuarioDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Result;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {
    
    @Autowired
    private UsuarioDAOJPAImplementacion usuarioDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Result result = usuarioDAO.GetByEmail(username);

        if (result.correct) {
            Usuario usuario = (Usuario) result.object;
            
            if (usuario.getEstatus() != 1) {
                throw new UsernameNotFoundException("Usuario Inactivo");
            }
            
            return User.withUsername(usuario.getEmail()) 
                .password(usuario.getPassword())
                .roles(usuario.getRol().getNombre())
                .disabled(usuario.getEstatus() == 0)  
                .build();
            
        }else {
            throw new UsernameNotFoundException("No se encontro el usuario");
        }
    }
}
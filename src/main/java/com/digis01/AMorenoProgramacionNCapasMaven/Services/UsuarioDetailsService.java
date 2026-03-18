package com.digis01.AMorenoProgramacionNCapasMaven.Services;

import com.digis01.AMorenoProgramacionNCapasMaven.DAO.UsuarioDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Result;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Usuario;
import com.digis01.AMorenoProgramacionNCapasMaven.Security.UsuarioDetails;
import org.springframework.beans.factory.annotation.Autowired;
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

        Result result = usuarioDAO.GetByUserNameOrCorreo(username);

        if (!result.correct || result.object == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        Usuario usuario = (Usuario) result.object;

        return new UsuarioDetails(usuario);
    }
}

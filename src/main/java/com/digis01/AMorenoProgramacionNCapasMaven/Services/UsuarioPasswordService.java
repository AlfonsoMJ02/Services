package com.digis01.AMorenoProgramacionNCapasMaven.Services;

import com.digis01.AMorenoProgramacionNCapasMaven.DAO.UsuarioDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Result;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UsuarioPasswordService {
    
    @Autowired
    private UsuarioDAOJPAImplementacion usuarioDAOJPA;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public Result<Usuario> Add (Usuario usuario){
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioDAOJPA.Add(usuario);
    }
}

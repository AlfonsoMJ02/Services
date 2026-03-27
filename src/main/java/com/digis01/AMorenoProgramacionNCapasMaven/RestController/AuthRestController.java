package com.digis01.AMorenoProgramacionNCapasMaven.RestController;

import com.digis01.AMorenoProgramacionNCapasMaven.DAO.UsuarioDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Result;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Usuario;
import com.digis01.AMorenoProgramacionNCapasMaven.Services.JwtService;
import com.digis01.AMorenoProgramacionNCapasMaven.Services.UsuarioDetailsService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Auth")
public class AuthRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioDAOJPAImplementacion usuarioJPADAO;

    @PostMapping("/Login")
    public ResponseEntity<?> Login(@RequestBody Map<String, String> loginRequest) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.get("email"),
                    loginRequest.get("password")
            ));

            UserDetails user = userDetailsService.loadUserByUsername(loginRequest.get("email"));

            Result resultUsuario = usuarioJPADAO.GetByEmail(loginRequest.get("email"));

            if (!resultUsuario.correct) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Usuario no encontrado");
            }

            Usuario usuario = (Usuario) resultUsuario.object;

            String token = jwtService.generateToken(usuario, user);

            Map<String, Object> response = new HashMap<>();
            response.put("Key", token);

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de autenticasion" + ex.getLocalizedMessage());
        }
    }
}

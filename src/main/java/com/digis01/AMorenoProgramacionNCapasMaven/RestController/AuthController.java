package com.digis01.AMorenoProgramacionNCapasMaven.RestController;

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
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/Login")
    public ResponseEntity<?> Login(@RequestBody Map<String, String> loginRequest) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.get("email"),
                    loginRequest.get("password")
            ));
            UserDetails user = userDetailsService.loadUserByUsername(loginRequest.get("email"));
            
            String token = jwtService.generateToken(user);
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("username", user.getUsername());
            response.put("roles", user.getAuthorities());
            
            return ResponseEntity.ok(response);
            
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
            
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de autenticasion" + ex.getLocalizedMessage());
        }
    }
}

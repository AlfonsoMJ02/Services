package com.digis01.AMorenoProgramacionNCapasMaven.RestController;

import com.digis01.AMorenoProgramacionNCapasMaven.DAO.EstadoDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.PaisDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.RolDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.UsuarioDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Api/Usuario")
public class UsuarioRestController {

    @Autowired
    private UsuarioDAOJPAImplementacion usuarioJPADAO;
    
    @Autowired
    private RolDAOJPAImplementacion rolJPADAO;
    
    @Autowired
    private PaisDAOJPAImplementacion paisJPADAO;
    
    @Autowired
    private EstadoDAOJPAImplementacion estadoJPADAO;

    @GetMapping("/GetAll")
    public ResponseEntity<Result> GetAll() {

        Result result = usuarioJPADAO.GetAll();

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    @GetMapping("Rol")
    public ResponseEntity<Result> Rol(){
        Result result = rolJPADAO.GetAll();
        
        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    @GetMapping("Pais")
    public ResponseEntity<Result> Pais(){
        Result result = paisJPADAO.GetAll();
        
        if (result.correct) {            
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    @GetMapping("Estado/GetByPais/{idPais}")
    public ResponseEntity<Result> Estado(@PathVariable int idPais){
        Result result = estadoJPADAO.GetAll(idPais);
        
        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}

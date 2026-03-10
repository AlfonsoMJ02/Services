package com.digis01.AMorenoProgramacionNCapasMaven.RestController;

import com.digis01.AMorenoProgramacionNCapasMaven.DAO.ColoniaDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.EstadoDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.MunicipioDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.PaisDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.RolDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.UsuarioDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Result;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    MunicipioDAOJPAImplementacion municipioJPADAO;

    @Autowired
    ColoniaDAOJPAImplementacion coloniaJPADAO;

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
    public ResponseEntity<Result> Rol() {
        Result result = rolJPADAO.GetAll();

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @GetMapping("Pais")
    public ResponseEntity<Result> Pais() {
        Result result = paisJPADAO.GetAll();

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @GetMapping("Estado/GetByPais/{idPais}")
    public ResponseEntity<Result> Estado(@PathVariable int idPais) {
        Result result = estadoJPADAO.GetAll(idPais);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @GetMapping("Municipio/GetByEstado/{idEstado}")
    public ResponseEntity<Result> Municipio(@PathVariable int idEstado) {
        Result result = municipioJPADAO.GetAll(idEstado);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @GetMapping("Colonia/GetByMunicipio/{idMunicipio}")
    public ResponseEntity<Result> Colonia(@PathVariable int idMunicipio) {
        Result result = coloniaJPADAO.GetAll(idMunicipio);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @DeleteMapping("Delete/{idUsuario}")
    @ResponseBody
    public ResponseEntity<Result> DeleteUsuario(@PathVariable int idUsuario){
        Result result = usuarioJPADAO.DeleteUser(idUsuario);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @DeleteMapping("Delete/Direccion/{idDireccion}")
    @ResponseBody
    public ResponseEntity<Result> DeleteDireccion(@PathVariable int idDireccion){
        Result result = usuarioJPADAO.Delete(idDireccion);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    @GetMapping("GetById/{idUsuario}")
    public ResponseEntity<Result> GetById(@PathVariable int idUsuario){
        Result result = usuarioJPADAO.GetById(idUsuario);
        
        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity Add(@RequestPart("usuario") Usuario usuario, @RequestPart(name = "imagen", required = false) MultipartFile imagen) {
        Result result = usuarioJPADAO.Add(usuario);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}

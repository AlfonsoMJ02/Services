package com.digis01.AMorenoProgramacionNCapasMaven.RestController;

import com.digis01.AMorenoProgramacionNCapasMaven.DAO.ColoniaDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.EstadoDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.MunicipioDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.PaisDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.RolDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.UsuarioDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Direccion;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Result;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Usuario;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<Result> DeleteUsuario(@PathVariable int idUsuario) {
        Result result = usuarioJPADAO.DeleteUser(idUsuario);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @DeleteMapping("Delete/Direccion/{idDireccion}")
    @ResponseBody
    public ResponseEntity<Result> DeleteDireccion(@PathVariable int idDireccion) {
        Result result = usuarioJPADAO.Delete(idDireccion);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @GetMapping("GetById/{idUsuario}")
    public ResponseEntity<Result> GetById(@PathVariable int idUsuario) {
        Result result = usuarioJPADAO.GetById(idUsuario);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @GetMapping("GetById/Direccion/{idDireccion}")
    public ResponseEntity<Result> GetByIdDireccion(@PathVariable int idDireccion) {
        Result result = usuarioJPADAO.GetByIdDireccion(idDireccion);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PutMapping("Update/Estatus")
    public ResponseEntity<Result> Estatus(@RequestParam("idUsuario") int idUsuario, @RequestParam("estatus") int estatus) {
        Result result = usuarioJPADAO.Estatus(idUsuario, estatus);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PostMapping(value = "Add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Result<Usuario>> Add(@RequestPart("usuario") Usuario usuario, @RequestPart(name = "imagen", required = false) MultipartFile imagen) {

        try {

            if (imagen != null && !imagen.isEmpty()) {

                byte[] bytes = imagen.getBytes();
                String base64 = Base64.getEncoder().encodeToString(bytes);

                usuario.setImagen(base64);
            }

            Result<Usuario> result = usuarioJPADAO.Add(usuario);

            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result);
            }

        } catch (Exception ex) {
            Result result = new Result();
            ex.printStackTrace();
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;

            return ResponseEntity.badRequest().body(result);
        }
    }

    @PutMapping("/Update/Imagen")
    @ResponseBody
    public Result UpdateImagen(@RequestParam("idUsuario") int idUsuario, @RequestParam("imagenFile") MultipartFile imagenFile) {
        Result result = new Result();

        try {
            if (imagenFile != null && !imagenFile.isEmpty()) {
                if (imagenFile.getContentType().startsWith("image/")) {
                    byte[] arregloBytes = imagenFile.getBytes();
                    String base64Img = Base64.getEncoder().encodeToString(arregloBytes);

                    result = usuarioJPADAO.UpdateImagen(idUsuario, base64Img);
                } else {
                    result.correct = false;
                    result.errorMessage = "Formato no valido";
                }
            } else {
                result.correct = false;
                result.errorMessage = "No se selecciono ninguna imagen";
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @PutMapping("Update")
    public ResponseEntity Update(@RequestBody Usuario usuario) {

        Result result = usuarioJPADAO.UpdateUser(usuario);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @PostMapping("Add/Direccion")
    public ResponseEntity<Result> AddDireccion(@RequestBody Direccion direccion) {
    
        Result result = usuarioJPADAO.AddDireccion(direccion);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PutMapping("Update/Direccion")
    public ResponseEntity<Result> UpdateDireccion(@RequestBody Direccion direccion) {
        Result result = usuarioJPADAO.UpdateDireccion(direccion);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @GetMapping("Search")
    public ResponseEntity<Result> Search(@RequestParam(required = false) String nombre, @RequestParam(required = false) String apellidoPaterno, @RequestParam(required = false) String apellidoMaterno, @RequestParam(required = false) Integer idRol) {

        Result result = usuarioJPADAO.Search(nombre, apellidoPaterno, apellidoMaterno, idRol);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}

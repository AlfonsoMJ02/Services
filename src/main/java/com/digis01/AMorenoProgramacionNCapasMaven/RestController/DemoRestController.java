package com.digis01.AMorenoProgramacionNCapasMaven.RestController;


import com.digis01.AMorenoProgramacionNCapasMaven.DAO.UsuarioDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Result;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Usuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Demo/Api")
public class DemoRestController {

    @GetMapping
    public String HolaMundo() {
        return "Hola Mundo";
    }

    @GetMapping("Saludo/{nombre}")
    public String Saludo(@PathVariable("nombre") String nombre) {
        return "Hola " + nombre;
    }

    @GetMapping("Saludo")
    public String Saludo2(@RequestParam("nombre") String nombre) {
        return "Hola " + nombre;
    }

//    @GetMapping("Suma")
//    public int Suma(@RequestParam int numero1, @RequestParam int numero2){
//        return numero1 + numero2;
//    }
    @GetMapping("Suma/{numero1}/{numero2}")
    public int Suma(@PathVariable("numero1") int numero1, @PathVariable("numero2") int numero2) {
        return numero1 + numero2;
    }

    @PostMapping("SumaArreglo")
    public int SumaArrgelo(@RequestBody List<Integer> numeros) {
        int suma = 0;

        for (Integer numero : numeros) {
            suma += numero;
        }
        return suma;
    }

    @Autowired
    public UsuarioDAOJPAImplementacion dao;

    @GetMapping("Usuario/{status}")
    public ResponseEntity<List> ObtenerUsuarios(@PathVariable ("status") int status) {
        Result result = dao.GetAll();

        if (result.correct) {
            return ResponseEntity.status(status).body(result.objects);
        } else {
            return ResponseEntity.status(status).body(null);
        }
    }
}

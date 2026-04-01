package com.digis01.AMorenoProgramacionNCapasMaven;

import com.digis01.AMorenoProgramacionNCapasMaven.DAO.UsuarioDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Colonia;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Direccion;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Result;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Rol;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Usuario;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AMorenoProgramacionNCapasMavenApplicationTests {

    @Autowired
    private UsuarioDAOJPAImplementacion usuarioDAOJPA;

    @Autowired
    private EntityManager entityManager;

    @Test
    void AddTest() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellidoPaterno("Rubio");
        usuario.setApellidoMaterno("Escamilla");
        usuario.setEmail("juan@outlook.com");

        usuario.setFechaNacimiento(LocalDate.of(2002, 3, 22));

        usuario.setPassword("Juan12345$");
        usuario.setSexo("H");
        usuario.setTelefono("1234567890");
        usuario.setCelular("1234567890");
        usuario.setCurp("JUAN12345TGFR56786");
        usuario.setUserName("Juuan123");

        Rol rol = entityManager.find(Rol.class, 1);
        usuario.setRol(rol);

        Direccion direccion = new Direccion();
        direccion.setCalle("Av Siempre Viva");
        direccion.setNumeroInterior("10");
        direccion.setNumeroExterior("20");

        Colonia colonia = new Colonia();
        colonia.setIdColonia(1);
        direccion.setColonia(colonia);

        List<Direccion> direcciones = new ArrayList<>();
        direcciones.add(direccion);
        usuario.setDirecciones(direcciones);

        Result result = usuarioDAOJPA.Add(usuario);

        Assertions.assertTrue(result.correct);
    }

    @Test
    void GetAllTest() {
        Result result = usuarioDAOJPA.GetAll();

        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.objects);
        Assertions.assertTrue(result.objects.size() > 0);

        Usuario usuario = (Usuario) result.objects.get(0);

        Assertions.assertNotNull(usuario.getNombre());
        Assertions.assertNotNull(usuario.getDirecciones());
    }

    @Test
    void GetByIdTest() {

        Result result = usuarioDAOJPA.GetByIdUser(12);

        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.object);
        Usuario usuario = (Usuario) result.object;

        Assertions.assertEquals(12, usuario.getIdUsuario());
        Assertions.assertNotNull(usuario.getNombre());

        Assertions.assertNotNull(usuario.getRol());
        Assertions.assertNotNull(usuario.getDirecciones());

        if (!usuario.getDirecciones().isEmpty()) {
            Direccion direccion = usuario.getDirecciones().get(0);
            Assertions.assertNotNull(direccion.getColonia());
            Assertions.assertNotNull(direccion.getColonia().getMunicipio());
            Assertions.assertNotNull(direccion.getColonia().getMunicipio().getEstado());
            Assertions.assertNotNull(direccion.getColonia().getMunicipio().getEstado().getPais());
        }
    }

    @Test
    void DeleteUserTest() {

        int idUsuario = 143;
        String email = "Luisa@gmail.com";

        Result result = usuarioDAOJPA.DeleteUser(idUsuario, email);

        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.object);
    }

    @Test
    void DeleteDireccionTest() {

        int idDireccion = 184;
        String email = "Luisa@gmail.com";

        Result result = usuarioDAOJPA.Delete(idDireccion, email);

        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.object);
    }

    @Test
    void AddDireccionTest() {

        String email = "Luisa@gmail.com";

        Direccion direccion = new Direccion();
        direccion.setCalle("Av Siempre Viva");
        direccion.setNumeroInterior("10");
        direccion.setNumeroExterior("20");

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(12);
        direccion.setUsuario(usuario);

        Colonia colonia = new Colonia();
        colonia.setIdColonia(1);
        direccion.setColonia(colonia);

        Result result = usuarioDAOJPA.AddDireccion(direccion, email);

        Assertions.assertTrue(result.correct);
    }

    @Test
    void EstatusTest() {

        int estatus = 0;
        String email = "Luisa@gmail.com";

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(12);
        usuario.setEstatus(estatus);

        Result result = usuarioDAOJPA.Estatus(12, estatus, email);

        Assertions.assertTrue(result.correct);
    }

    @Test
    void UpdateUserTest() {
        String email = "Luisa@gmail.com";
        Usuario usuario = new Usuario();

        usuario.setIdUsuario(12);
        usuario.setNombre("Brandon");
        usuario.setApellidoPaterno("Ramos");
        usuario.setApellidoMaterno("Lopez");
        usuario.setEmail("brandon@gmail.com");
        usuario.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        usuario.setSexo("H");
        usuario.setTelefono("1234567809");
        usuario.setCelular("1234567809");
        usuario.setCurp("ABCD010203HDFRRL09");
        usuario.setUserName("Brandoon123");

        Rol rol = new Rol();
        rol.setIdRol(1);
        usuario.setRol(rol);

        Result result = usuarioDAOJPA.UpdateUser(usuario, email);

        Assertions.assertTrue(result.correct);
    }

    @Test
    void UpdateDireccionTest() {
        
        String email = "Luisa@gmail.com";
        
        Direccion direccion = new Direccion();
        direccion.setIdDireccion(183); 

        direccion.setCalle("Av. San juan");
        direccion.setNumeroInterior("3");
        direccion.setNumeroExterior("43");

        Colonia colonia = new Colonia();
        colonia.setIdColonia(1);
        direccion.setColonia(colonia);

        Result result = usuarioDAOJPA.UpdateDireccion(direccion, email);

        Assertions.assertTrue(result.correct);
    }
}

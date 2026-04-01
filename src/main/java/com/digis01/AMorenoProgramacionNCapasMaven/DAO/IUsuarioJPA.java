package com.digis01.AMorenoProgramacionNCapasMaven.DAO;


import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Direccion;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Result;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Usuario;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface IUsuarioJPA {
    Result GetAll();
    Result GetByIdUser(int idUsuario);
    Result<Usuario> Add(Usuario usuario);
    Result GetById(int idUsuario);
    Result Delete(int idDireccion, String email);
    Result DeleteUser(int idUsuario, String email);
    Result AddDireccion(Direccion direccion, String email);
    Result GetByIdDireccion(int idDireccion);
    Result UpdateDireccion(Direccion direccion, String email);
    Result UpdateUser(Usuario usuario, String email);
    Result UpdateImagen(int idUsuario, String imagenBase64, Authentication auth);
    Result Estatus(int idUsuario, int estatus, String email);
    Result Search(String nombre, String apellidoPaterno, String apellidoMaterno, Integer idRol);
    Result AddAll(List<Usuario> usuarios);
    Result GetByEmail(String email);
}

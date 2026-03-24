package com.digis01.AMorenoProgramacionNCapasMaven.DAO;


import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Direccion;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Result;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Usuario;
import java.util.List;

public interface IUsuarioJPA {
    Result GetAll();
    Result<Usuario> Add(Usuario usuario);
    Result GetById(int idUsuario);
    Result Delete(int idDireccion);
    Result DeleteUser(int idUsuario);
    Result AddDireccion(Direccion direccion);
    Result GetByIdDireccion(int idDireccion);
    Result UpdateDireccion(Direccion direccion);
    Result UpdateUser(Usuario usuario);
    Result UpdateImagen(int idUsuario, String imagenBase64);
    Result Estatus(int idUsuario, int estatus);
    Result Search(String nombre, String apellidoPaterno, String apellidoMaterno, Integer idRol);
    Result AddAll(List<Usuario> usuarios);
    Result GetByEmail(String email);
}

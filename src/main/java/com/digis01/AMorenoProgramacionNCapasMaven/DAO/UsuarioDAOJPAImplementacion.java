package com.digis01.AMorenoProgramacionNCapasMaven.DAO;

import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Usuario;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Rol;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Colonia;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Direccion;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioDAOJPAImplementacion implements IUsuarioJPA {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAll() {

        Result result = new Result();

        try {

            TypedQuery<Usuario> queryUsuario = entityManager.createQuery(
                    "FROM Usuario LEFT JOIN FETCH Direcciones",
                    Usuario.class
            );

            List<Usuario> usuarios = queryUsuario.getResultList();

            result.objects = new ArrayList<>(usuarios);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result GetByIdUser(int idUsuario) {

        Result result = new Result();

        try {

            TypedQuery<Usuario> query = entityManager.createQuery(
                    "SELECT u FROM Usuario u "
                    + "LEFT JOIN FETCH u.Rol "
                    + "LEFT JOIN FETCH u.Direcciones d "
                    + "LEFT JOIN FETCH d.Colonia c "
                    + "LEFT JOIN FETCH c.Municipio m "
                    + "LEFT JOIN FETCH m.Estado e "
                    + "LEFT JOIN FETCH e.Pais "
                    + "WHERE u.IdUsuario = :idUsuario",
                    Usuario.class
            );

            query.setParameter("idUsuario", idUsuario);

            Usuario usuario = query.getSingleResult();

            result.object = usuario;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }

    @Transactional
    @Override
    public Result<Usuario> Add(Usuario usuario) {

        Result<Usuario> result = new Result<>();

        try {

            usuario.setRol(entityManager.find(Rol.class, usuario.getRol().getIdRol()));

            if (usuario.getDirecciones() != null) {

                for (Direccion direccion : usuario.getDirecciones()) {

                    direccion.setColonia(
                            entityManager.find(Colonia.class, direccion.getColonia().getIdColonia())
                    );

                    direccion.setUsuario(usuario);
                }
            }

            entityManager.persist(usuario);

            result.object = usuario;
            result.correct = true;

        } catch (Exception ex) {

            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional
    public Result Delete(int idDireccion, String email) {

        Result result = new Result();

        try {

            Direccion direccion = entityManager.find(Direccion.class, idDireccion);

            if (direccion == null) {
                result.correct = false;
                result.errorMessage = "La direccion no existe";
                return result;
            }

            Usuario usuario = entityManager
                    .createQuery("FROM Usuario WHERE Email = :email", Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();

            if (usuario.getRol() != null
                    && usuario.getRol().getNombre().toUpperCase().contains("ADMIN")) {

                entityManager.remove(direccion);
                result.correct = true;
                result.object = "Direccion eliminada correctamente (ADMIN)";
                return result;
            }

            if (direccion.getUsuario().getIdUsuario() == usuario.getIdUsuario()) {

                entityManager.remove(direccion);
                result.correct = true;
                result.object = "Direccion eliminada correctamente (PROPIA)";
                return result;
            }

            result.correct = false;
            result.errorMessage = "No tienes permisos para eliminar esta direccion";
            return result;

        } catch (Exception ex) {

            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional
    public Result DeleteUser(int idUsuario) {

        Result result = new Result();

        try {

            Usuario usuario = entityManager.find(Usuario.class, idUsuario);

            if (usuario != null) {

                entityManager.remove(usuario);
                result.correct = true;

            } else {

                result.correct = false;
                result.errorMessage = "Usuario no encontrado";
            }

        } catch (Exception ex) {

            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional
    public Result AddDireccion(Direccion direccion) {

        Result result = new Result();

        try {

            direccion.setUsuario(
                    entityManager.find(Usuario.class, direccion.getUsuario().getIdUsuario())
            );

            direccion.setColonia(
                    entityManager.find(Colonia.class, direccion.getColonia().getIdColonia())
            );

            if (direccion.getUsuario() != null && direccion.getColonia() != null) {

                entityManager.persist(direccion);

                result.correct = true;

            } else {

                result.correct = false;
                result.errorMessage = "Usuario o colonia no encontrados";
            }

        } catch (Exception ex) {

            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result GetByIdDireccion(int idDireccion) {

        Result result = new Result();

        try {

            TypedQuery<Direccion> queryDireccion = entityManager.createQuery(
                    "SELECT d FROM Direccion d "
                    + "LEFT JOIN FETCH d.usuario "
                    + "LEFT JOIN FETCH d.colonia "
                    + "WHERE d.idDireccion = :idDireccion",
                    Direccion.class
            );

            queryDireccion.setParameter("idDireccion", idDireccion);

            Direccion direccion = queryDireccion.getSingleResult();

            result.object = direccion;
            result.correct = true;

        } catch (Exception ex) {

            result.correct = false;
            ex.printStackTrace();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional
    public Result UpdateDireccion(Direccion direccion) {

        Result result = new Result();

        try {

            Direccion direccionJPA = entityManager.find(Direccion.class, direccion.getIdDireccion());

            if (direccionJPA != null) {

                direccionJPA.setCalle(direccion.getCalle());
                direccionJPA.setNumeroInterior(direccion.getNumeroInterior());
                direccionJPA.setNumeroExterior(direccion.getNumeroExterior());

                direccionJPA.setColonia(
                        entityManager.find(Colonia.class, direccion.getColonia().getIdColonia())
                );

                result.correct = true;

            } else {

                result.correct = false;
                result.errorMessage = "Direccion no encontrada";
            }

        } catch (Exception ex) {

            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result GetById(int idUsuario) {

        Result result = new Result();

        try {

            TypedQuery<Usuario> queryUsuario = entityManager.createQuery(
                    "FROM Usuario LEFT JOIN FETCH Direcciones WHERE IdUsuario = :idUsuario",
                    Usuario.class
            );

            queryUsuario.setParameter("idUsuario", idUsuario);

            Usuario usuario = queryUsuario.getSingleResult();

            result.object = usuario;
            result.correct = true;

        } catch (Exception ex) {

            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional
    public Result UpdateUser(Usuario usuario) {

        Result result = new Result();

        try {
            System.out.println("ID recibido JSON: " + usuario.getIdUsuario());
            Usuario usuarioJPA = entityManager.find(Usuario.class, usuario.getIdUsuario());

            if (usuarioJPA != null) {

                usuarioJPA.setNombre(usuario.getNombre());
                usuarioJPA.setApellidoPaterno(usuario.getApellidoPaterno());
                usuarioJPA.setApellidoMaterno(usuario.getApellidoMaterno());
                usuarioJPA.setEmail(usuario.getEmail());
                usuarioJPA.setFechaNacimiento(usuario.getFechaNacimiento());
                usuarioJPA.setSexo(usuario.getSexo());
                if (usuario.getPassword() != null) {
                    usuarioJPA.setPassword(usuario.getPassword());
                }
                usuarioJPA.setTelefono(usuario.getTelefono());
                usuarioJPA.setCelular(usuario.getCelular());
                usuarioJPA.setCurp(usuario.getCurp());
                usuarioJPA.setUserName(usuario.getUserName());

                if (usuario.getRol() != null) {
                    usuarioJPA.setRol(
                            entityManager.find(Rol.class, usuario.getRol().getIdRol())
                    );
                }

                entityManager.merge(usuarioJPA);

                result.correct = true;

            } else {

                result.correct = false;
                result.errorMessage = "Usuario no encontrado";
            }

        } catch (Exception ex) {

            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional
    public Result UpdateImagen(int idUsuario, String imagenBase64) {

        Result result = new Result();

        try {

            Query query = entityManager.createQuery("UPDATE Usuario SET  Imagen = :imagen WHERE IdUsuario = :idUsuario");

            query.setParameter("imagen", imagenBase64);
            query.setParameter("idUsuario", idUsuario);
            int filas = query.executeUpdate();
            result.correct = filas > 0;

        } catch (Exception ex) {

            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Transactional
    @Override
    public Result Estatus(int idUsuario, int estatus) {

        Result result = new Result();

        try {

            Query query = entityManager.createQuery(
                    "UPDATE Usuario SET Estatus = :estatus WHERE IdUsuario = :idUsuario"
            );

            query.setParameter("estatus", estatus);
            query.setParameter("idUsuario", idUsuario);

            int filas = query.executeUpdate();

            result.correct = filas > 0;

        } catch (Exception ex) {

            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result Search(String nombre, String apellidoPaterno, String apellidoMaterno, Integer idRol) {

        Result result = new Result();

        try {

            String jpql = "SELECT DISTINCT u FROM Usuario u LEFT JOIN FETCH u.Direcciones d WHERE 1=1";

            if (nombre != null && !nombre.isEmpty()) {
                jpql += " AND LOWER(u.Nombre) LIKE LOWER(:nombre)";
            }

            if (apellidoPaterno != null && !apellidoPaterno.isEmpty()) {
                jpql += " AND LOWER(u.ApellidoPaterno) LIKE LOWER(:apellidoPaterno)";
            }

            if (apellidoMaterno != null && !apellidoMaterno.isEmpty()) {
                jpql += " AND LOWER(u.ApellidoMaterno) LIKE LOWER(:apellidoMaterno)";
            }

            if (idRol != null) {
                jpql += " AND u.Rol.IdRol = :idRol";
            }

            TypedQuery<Usuario> query = entityManager.createQuery(jpql, Usuario.class);

            if (nombre != null && !nombre.isEmpty()) {
                query.setParameter("nombre", "%" + nombre + "%");
            }

            if (apellidoPaterno != null && !apellidoPaterno.isEmpty()) {
                query.setParameter("apellidoPaterno", "%" + apellidoPaterno + "%");
            }

            if (apellidoMaterno != null && !apellidoMaterno.isEmpty()) {
                query.setParameter("apellidoMaterno", "%" + apellidoMaterno + "%");
            }

            if (idRol != null) {
                query.setParameter("idRol", idRol);
            }

            List<Usuario> usuarios = query.getResultList();

            result.objects = new ArrayList<>(usuarios);
            result.correct = true;

        } catch (Exception ex) {

            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional
    public Result AddAll(List<Usuario> usuarios) {

        Result result = new Result();

        try {

            int batchSize = 50;
            int i = 0;

            for (Usuario usuario : usuarios) {

                Rol rol = entityManager.find(Rol.class, usuario.getRol().getIdRol());
                usuario.setRol(rol);

                for (Direccion direccion : usuario.getDirecciones()) {

                    Integer idColonia = direccion.getColonia().getIdColonia();
                    Colonia colonia = entityManager.find(Colonia.class, idColonia);

                    direccion.setColonia(colonia);
                    direccion.setUsuario(usuario);
                }

                entityManager.persist(usuario);

                if (i > 0 && i % batchSize == 0) {

                    entityManager.flush();
                    entityManager.clear();
                }

                i++;
            }

            result.correct = true;

        } catch (Exception ex) {

            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result GetByEmail(String email) {

        Result result = new Result();

        try {

            TypedQuery<Usuario> query = entityManager.createQuery(
                    "FROM Usuario WHERE Email = :pEmail",
                    Usuario.class
            );

            query.setParameter("pEmail", email);

            Usuario usuario = query.getSingleResult();

            result.object = usuario;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }
}

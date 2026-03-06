package com.digis01.AMorenoProgramacionNCapasMaven.DAO;


import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Result;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Rol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RolDAOJPAImplementacion implements IRolJPA {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result<Rol> GetAll() {

        Result<Rol> result = new Result<>();

        try {

            TypedQuery<Rol> query =
                    entityManager.createQuery("FROM Rol", Rol.class);

            List<Rol> rolesJPA = query.getResultList();


            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }
}

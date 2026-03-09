package com.digis01.AMorenoProgramacionNCapasMaven.DAO;

import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Estado;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoDAOJPAImplementacion implements IEstadoJPA {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAll(int idPais) {

        Result result = new Result<>();

        try {

            TypedQuery<Estado> queryEstado = entityManager.createQuery(
                            "FROM Estado e WHERE e.Pais.IdPais = :idPais",
                            Estado.class
                    );

            queryEstado.setParameter("idPais", idPais);

            List<Estado> estados =  queryEstado.getResultList();
            result.objects = new ArrayList<>(estados);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }
}

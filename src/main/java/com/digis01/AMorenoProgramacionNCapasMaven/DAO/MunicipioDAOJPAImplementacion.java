package com.digis01.AMorenoProgramacionNCapasMaven.DAO;

import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Municipio;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioDAOJPAImplementacion implements IMunicipioJPA{

    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result GetAll(int idEstado) {
        Result result = new Result();
        
        try {
            TypedQuery<Municipio> query =
                entityManager.createQuery("FROM Municipio m WHERE m.Estado.IdEstado = :idEstado",
                    Municipio.class
                );

            query.setParameter("idEstado", idEstado);

            List<Municipio> estadoJPA = query.getResultList();
            
            result.objects = new ArrayList<>(estadoJPA);

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
}

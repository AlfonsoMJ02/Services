package com.digis01.AMorenoProgramacionNCapasMaven.DAO;

import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Colonia;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaDAOJPAImplementacion implements IColoniaJPA{
    
    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result<Colonia> GetAll(int idMunicipio) {
        Result result = new Result();
        
        try {
            TypedQuery<Colonia> query = 
                    entityManager.createQuery("FROM Colonia c WHERE c.Municipio.IdMunicipio = :idMunicipio",
                            Colonia.class);
            
            query.setParameter("idMunicipio", idMunicipio);
            
            List<Colonia> municipioJPA = query.getResultList();
            
            result.objects = new ArrayList<>(municipioJPA);
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
}

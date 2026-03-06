package com.digis01.AMorenoProgramacionNCapasMaven.DAO;

import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Result;

public interface IColoniaJPA {
    Result GetAll(int idMunicipio);
}

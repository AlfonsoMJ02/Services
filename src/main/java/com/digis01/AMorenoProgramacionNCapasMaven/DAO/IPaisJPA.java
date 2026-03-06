package com.digis01.AMorenoProgramacionNCapasMaven.DAO;

import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Pais;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Result;


public interface IPaisJPA {
    Result<Pais>  GetAll();
}
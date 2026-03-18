package com.digis01.AMorenoProgramacionNCapasMaven.JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public class Result<T> {
    public boolean correct;
    public String errorMessage;
    @Schema(hidden = true)
    @JsonIgnore
    public Exception ex;
    public Object object;
    public List<T> objects; 
}

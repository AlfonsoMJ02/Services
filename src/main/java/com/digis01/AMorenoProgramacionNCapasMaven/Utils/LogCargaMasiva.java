package com.digis01.AMorenoProgramacionNCapasMaven.Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;


public class LogCargaMasiva {
    
    private static final String RUTA_LOG = "src/main/resources/Log/LOG_CargaMasiva.txt";
    
    public static void escribir(String key, String ruta, String status, String detalle){
        
        try {
            File file = new File(RUTA_LOG);
            
            file.getParentFile().mkdirs();
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            
            String registro = key + "|" + ruta + "|" + status + "|" + LocalDateTime.now() + "|" + detalle;
            
            writer.write(registro);
            writer.newLine();
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

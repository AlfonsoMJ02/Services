package com.digis01.AMorenoProgramacionNCapasMaven.Utils;

public class SwaggerExamples {

    public static final String SUCCESS = """
    {
      "correct": true,
      "message": "Operación realizada correctamente",
      "data": {}
    }
    """;

    public static final String BAD_REQUEST = """
    {
      "correct": false,
      "message": "La solicitud contiene parámetros inválidos"
    }
    """;

    public static final String NOT_FOUND = """
    {
      "correct": false,
      "message": "El recurso solicitado no fue encontrado"
    }
    """;

    public static final String INTERNAL_ERROR = """
    {
      "correct": false,
      "message": "Ocurrió un error interno del servidor"
    }
    """;

}

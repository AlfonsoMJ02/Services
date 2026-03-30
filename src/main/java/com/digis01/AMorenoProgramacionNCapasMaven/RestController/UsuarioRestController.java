package com.digis01.AMorenoProgramacionNCapasMaven.RestController;

import com.digis01.AMorenoProgramacionNCapasMaven.DAO.ColoniaDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.EstadoDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.MunicipioDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.PaisDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.RolDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.UsuarioDAOJPAImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Colonia;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Direccion;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.ErroresArchivo;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Result;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Rol;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Usuario;
import com.digis01.AMorenoProgramacionNCapasMaven.Services.JwtService;
import com.digis01.AMorenoProgramacionNCapasMaven.Services.ValidationService;
import com.digis01.AMorenoProgramacionNCapasMaven.Utils.LogCargaMasiva;
import com.digis01.AMorenoProgramacionNCapasMaven.Utils.SHA256Generator;
import com.digis01.AMorenoProgramacionNCapasMaven.Utils.SwaggerExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/Api/Usuario")

@ApiResponses(value = {
    @ApiResponse(
            responseCode = "400",
            description = "Solicitud incorrecta",
            content = @Content(
                    examples = @ExampleObject(value = SwaggerExamples.BAD_REQUEST)
            )
    ),

    @ApiResponse(
            responseCode = "404",
            description = "Recurso no encontrado",
            content = @Content(
                    examples = @ExampleObject(value = SwaggerExamples.NOT_FOUND)
            )
    ),

    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(
                    examples = @ExampleObject(value = SwaggerExamples.INTERNAL_ERROR)
            )
    )
})
public class UsuarioRestController {

    @Autowired
    private UsuarioDAOJPAImplementacion usuarioJPADAO;

    @Autowired
    private RolDAOJPAImplementacion rolJPADAO;

    @Autowired
    private PaisDAOJPAImplementacion paisJPADAO;

    @Autowired
    private EstadoDAOJPAImplementacion estadoJPADAO;

    @Autowired
    MunicipioDAOJPAImplementacion municipioJPADAO;

    @Autowired
    ColoniaDAOJPAImplementacion coloniaJPADAO;

    @Operation(summary = "Mostrar usuarios", description = "Permite mostrar todos los usuarios del sisetma")
    @ApiResponse(
            responseCode = "200",
            description = "Usuarios mostrados correctamente",
            content = @Content(
                    schema = @Schema(implementation = Result.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS)
            )
    )
    @GetMapping("/GetAll")
    public ResponseEntity<Result> GetAll() {

        Result result = usuarioJPADAO.GetAll();

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @GetMapping("GetByIdUser/{idUsuario}")
    public ResponseEntity<Result> GetByIdFull(@PathVariable int idUsuario) {

        Result result = usuarioJPADAO.GetByIdUser(idUsuario);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @Operation(summary = "Mostrar roles", description = "Permite mostrar todos los roles")
    @ApiResponse(
            responseCode = "200",
            description = "Roles mostrados correctamente",
            content = @Content(
                    schema = @Schema(implementation = Result.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS)
            )
    )
    @GetMapping("Rol")
    public ResponseEntity<Result> Rol() {
        Result result = rolJPADAO.GetAll();

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @Operation(summary = "Mostrar paises", description = "Permite mostrar todos los paises")
    @ApiResponse(
            responseCode = "200",
            description = "Paises mostrados correctamente",
            content = @Content(
                    schema = @Schema(implementation = Result.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS)
            )
    )
    @GetMapping("Pais")
    public ResponseEntity<Result> Pais() {
        Result result = paisJPADAO.GetAll();

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @Operation(summary = "Mostrar estados", description = "Permite mostrar todos los estados pertenecientes a el pais seleccionado")
    @ApiResponse(
            responseCode = "200",
            description = "Estados mostrados correctamente",
            content = @Content(
                    schema = @Schema(implementation = Result.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS)
            )
    )
    @GetMapping("Estado/GetByPais/{idPais}")
    public ResponseEntity<Result> Estado(@PathVariable int idPais) {
        Result result = estadoJPADAO.GetAll(idPais);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @Operation(summary = "Mostrar municipios", description = "Permite mostrar todos los municipios pertenecientes a el estado seleccionado")
    @ApiResponse(
            responseCode = "200",
            description = "Municipios mostrados correctamente",
            content = @Content(
                    schema = @Schema(implementation = Result.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS)
            )
    )
    @GetMapping("Municipio/GetByEstado/{idEstado}")
    public ResponseEntity<Result> Municipio(@PathVariable int idEstado) {
        Result result = municipioJPADAO.GetAll(idEstado);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @Operation(summary = "Mostrar colonias", description = "Permite mostrar todas las colonias pertenecientes al municipio seleccionado")
    @ApiResponse(
            responseCode = "200",
            description = "Colonias mostradas correctamente",
            content = @Content(
                    schema = @Schema(implementation = Result.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS)
            )
    )
    @GetMapping("Colonia/GetByMunicipio/{idMunicipio}")
    public ResponseEntity<Result> Colonia(@PathVariable int idMunicipio) {
        Result result = coloniaJPADAO.GetAll(idMunicipio);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @Operation(summary = "Eliminar usuario", description = "Permite eliminar toda la informacion relacionada con ese usuario")
    @ApiResponse(
            responseCode = "200",
            description = "Usuario eliminado correctamente",
            content = @Content(
                    schema = @Schema(implementation = Result.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS)
            )
    )
    @PreAuthorize("hasRole('Administrador')")
    @DeleteMapping("Delete/{idUsuario}")
    @ResponseBody
    public ResponseEntity<Result> DeleteUsuario(@PathVariable int idUsuario) {

        Result result = usuarioJPADAO.DeleteUser(idUsuario);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @Operation(summary = "Eliminar direccion", description = "Permite eliminar la direccion de un usuario ya creado")
    @ApiResponse(
            responseCode = "200",
            description = "Usuario actualizado correctamente",
            content = @Content(
                    schema = @Schema(implementation = Result.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS)
            )
    )
    @DeleteMapping("/Delete/Direccion/{idDireccion}")
    public ResponseEntity<?> deleteDireccion(@PathVariable int idDireccion) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Result result = usuarioJPADAO.Delete(idDireccion, email);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
        }
    }

    @Operation(summary = "Mostrar solo un usuario", description = "Permite mostrar la informacion personal de un usuario ya creado")
    @ApiResponse(
            responseCode = "200",
            description = "Informacion mostrada correctamente",
            content = @Content(
                    schema = @Schema(implementation = Result.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS)
            )
    )
    @GetMapping("GetById/{idUsuario}")
    public ResponseEntity<Result> GetById(@PathVariable int idUsuario) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Result resultUsuario = usuarioJPADAO.GetByEmail(email);

        if (!resultUsuario.correct) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(resultUsuario);
        }

        Usuario usuarioLogueado = (Usuario) resultUsuario.object;

        boolean esAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_Administrador"));

        if (!esAdmin && usuarioLogueado.getIdUsuario() != idUsuario) {
            Result resultError = new Result();
            resultError.correct = false;
            resultError.errorMessage = "No puede ver informacion de otro usuario";

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resultError);
        }

        Result result = usuarioJPADAO.GetById(idUsuario);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @Operation(summary = "Mostrar usuarios", description = "Permite mostrar toda la informacion de todos los usuarios del sistema")
    @ApiResponse(
            responseCode = "200",
            description = "Usuarios mostrados correctamente",
            content = @Content(
                    schema = @Schema(implementation = Result.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS)
            )
    )
    @GetMapping("GetById/Direccion/{idDireccion}")
    public ResponseEntity<Result> GetByIdDireccion(@PathVariable int idDireccion) {
        Result result = usuarioJPADAO.GetByIdDireccion(idDireccion);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @Operation(summary = "Actualizar estatus", description = "Permite actualizar el estatus de un usuario ya creado")
    @ApiResponse(
            responseCode = "200",
            description = "Estatus de usuario actualizado correctamente",
            content = @Content(
                    schema = @Schema(implementation = Result.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS)
            )
    )
    @PreAuthorize("hasRole('Administrador')")
    @PutMapping("Update/Estatus")
    public ResponseEntity<Result> Estatus(@RequestParam("idUsuario") int idUsuario, @RequestParam("estatus") int estatus) {
        Result result = usuarioJPADAO.Estatus(idUsuario, estatus);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @Operation(summary = "Agregar usuario", description = "Permite agregar a un nuevo usuario")
    @ApiResponse(
            responseCode = "200",
            description = "Usuario agregado correctamente",
            content = @Content(
                    schema = @Schema(implementation = Result.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS)
            )
    )
    @PreAuthorize("hasRole('Administrador')")
    @PostMapping(value = "Add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Result<Usuario>> Add(@RequestPart("usuario") Usuario usuario, @RequestPart(name = "imagen", required = false) MultipartFile imagen) {

        try {

            if (imagen != null && !imagen.isEmpty()) {

                byte[] bytes = imagen.getBytes();
                String base64 = Base64.getEncoder().encodeToString(bytes);

                usuario.setImagen(base64);
            }

            Result<Usuario> result = usuarioJPADAO.Add(usuario);

            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result);
            }

        } catch (Exception ex) {
            Result result = new Result();
            ex.printStackTrace();
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;

            return ResponseEntity.badRequest().body(result);
        }
    }

    @Operation(summary = "Actualizar imagen", description = "Permite actualizar la imagen de un usuario ya creado")
    @ApiResponse(
            responseCode = "200",
            description = "Imagen actualizada correctamente",
            content = @Content(
                    schema = @Schema(implementation = Result.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS)
            )
    )
    @PutMapping("/Update/Imagen")
    @ResponseBody
    public Result UpdateImagen(@RequestParam("idUsuario") int idUsuario, @RequestParam("imagenFile") MultipartFile imagenFile) {
        Result result = new Result();

        try {
            if (imagenFile != null && !imagenFile.isEmpty()) {
                if (imagenFile.getContentType().startsWith("image/")) {
                    byte[] arregloBytes = imagenFile.getBytes();
                    String base64Img = Base64.getEncoder().encodeToString(arregloBytes);

                    result = usuarioJPADAO.UpdateImagen(idUsuario, base64Img);
                } else {
                    result.correct = false;
                    result.errorMessage = "Formato no valido";
                }
            } else {
                result.correct = false;
                result.errorMessage = "No se selecciono ninguna imagen";
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Autowired
    private JwtService jwtService;

    @Operation(summary = "Actualizar usuario", description = "Permite actualizar la infromacion personal de un usuario ya creado")
    @ApiResponse(
            responseCode = "200",
            description = "Usuario actualizado correctamente",
            content = @Content(
                    schema = @Schema(implementation = Result.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS)
            )
    )
    @PutMapping("Update")
    public ResponseEntity Update(@RequestBody Usuario usuario, HttpServletRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);

        Integer idUsuarioToken = jwtService.extractIdUsuario(token);

        boolean esAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_Administrador"));

        if (!esAdmin && !idUsuarioToken.equals(usuario.getIdUsuario())) {
            Result result = new Result();
            result.correct = false;
            result.errorMessage = "No puedes editar otro usuario";

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
        }

        Result result = usuarioJPADAO.UpdateUser(usuario);

        return result.correct
                ? ResponseEntity.ok(result)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @Operation(summary = "Agregar nueva direccion", description = "Permite agregar una nueva direccion al usuario ya creada")
    @ApiResponse(
            responseCode = "200",
            description = "Direccion agregada correctamente",
            content = @Content(
                    schema = @Schema(implementation = Result.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS)
            )
    )
    @PostMapping("Add/Direccion")
    public ResponseEntity<Result> AddDireccion(@RequestBody Direccion direccion) {

        Result result = usuarioJPADAO.AddDireccion(direccion);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @Operation(summary = "Actualizar direccion", description = "Permite actualizar una direccion del usuario ya creada")
    @ApiResponse(
            responseCode = "200",
            description = "Direccion actualizada correctamente",
            content = @Content(
                    schema = @Schema(implementation = Result.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS)
            )
    )
    @PutMapping("Update/Direccion")
    public ResponseEntity<Result> UpdateDireccion(@RequestBody Direccion direccion) {
        Result result = usuarioJPADAO.UpdateDireccion(direccion);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @Operation(summary = "Buscar usuarios", description = "Permite buscar usuarios por nombre, apellido paterno, apellido materno o rol")
    @ApiResponse(
            responseCode = "200",
            description = "Usuarios encontrados correctamente",
            content = @Content(
                    schema = @Schema(implementation = Result.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS)
            )
    )

    @GetMapping("Search")
    public ResponseEntity<Result> Search(@RequestParam(required = false) String nombre, @RequestParam(required = false) String apellidoPaterno, @RequestParam(required = false) String apellidoMaterno, @RequestParam(required = false) Integer idRol) {

        Result result = usuarioJPADAO.Search(nombre, apellidoPaterno, apellidoMaterno, idRol);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PostMapping("/CargaMasiva")
    public ResponseEntity<Result> CargaMasiva(@RequestParam("archivo") MultipartFile archivo) {

        Result result = new Result();

        try {

            String rutaBase = System.getProperty("user.dir");
            String rutaCarpeta = "src/main/resources/archivosCM";
            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String nombreArchivo = fecha + archivo.getOriginalFilename();
            String rutaArchivo = rutaBase + "/" + rutaCarpeta + "/" + nombreArchivo;
            String extension = archivo.getOriginalFilename().split("\\.")[1];
            archivo.transferTo(new File(rutaArchivo));
            List<Usuario> usuarios = null;

            if (extension.equals("txt")) {
                usuarios = LecturaArchivoTxt(new File(rutaArchivo));
            } else if (extension.equals("xlsx")) {
                usuarios = LecturaArchivoXLSX(new File(rutaArchivo));
            }

            List<ErroresArchivo> errores = ValidarDatos(usuarios);

            if (errores.isEmpty()) {
                String key = SHA256Generator.generateKey(rutaArchivo + LocalDateTime.now());
                LogCargaMasiva.escribir(key, rutaArchivo, "VALIDO", "Archivo validado sin errores");
                result.correct = true;
                result.object = key;

            } else {
                LogCargaMasiva.escribir("-", rutaArchivo, "INVALIDO", "Archivo con errores");

                result.correct = false;
                result.objects = errores;
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/ProcesarCargaMasiva/{key}")
    public ResponseEntity<Result> ProcesarCargaMasiva(@PathVariable String key) {

        Result result = new Result();

        try {

            String rutaLog = "src/main/resources/log/Log_CargaMasiva.txt";
            List<String> lineas = Files.readAllLines(Paths.get(rutaLog));
            String lineaEncontrada = null;

            for (String linea : lineas) {
                if (linea.startsWith(key)) {
                    lineaEncontrada = linea;
                    break;
                }
            }

            if (lineaEncontrada == null) {
                result.correct = false;
                result.errorMessage = "Registro no encontrado";

                return ResponseEntity.ok(result);
            }

            String[] datos = lineaEncontrada.split("\\|");
            String rutaArchivo = datos[1];
            LocalDateTime fecharArchivoLog = LocalDateTime.parse(datos[3]);
            long minutos = ChronoUnit.MINUTES.between(fecharArchivoLog, LocalDateTime.now());

            if (minutos > 2) {

                LogCargaMasiva.escribir(key, rutaArchivo, "ERROR", "Tiempo de inserción expirado");

                result.correct = false;
                result.errorMessage = "Tiempo de inserción expirado";

                return ResponseEntity.ok(result);
            }

            List<Usuario> usuarios;

            if (rutaArchivo.endsWith("txt")) {
                usuarios = LecturaArchivoTxt(new File(rutaArchivo));
            } else {
                usuarios = LecturaArchivoXLSX(new File(rutaArchivo));
            }

            result = usuarioJPADAO.AddAll(usuarios);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
        }

        return ResponseEntity.ok(result);
    }

    public List<Usuario> LecturaArchivoTxt(File archivo) {
        List<Usuario> usuarios = null;
        //try with reouces - Garbage collector
        try (InputStream inputStream = new FileInputStream(archivo); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

            usuarios = new ArrayList<>();
            String cadena = "";
            int fila = 1;
            while ((cadena = bufferedReader.readLine()) != null) {
                String[] datosUsuario = cadena.split("\\|");
                Usuario usuario = new Usuario();
                usuario.setRol(new Rol());
                Direccion direccion = new Direccion();
                Colonia colonia = new Colonia();

                direccion.setColonia(colonia);
                usuario.setDirecciones(new ArrayList<>());
                usuario.getDirecciones().add(direccion);

                usuario.setNombre(datosUsuario[0]);
                usuario.setApellidoPaterno(datosUsuario[1]);
                usuario.setApellidoMaterno(datosUsuario[2]);
                usuario.setEmail(datosUsuario[3]);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate fecha = LocalDate.parse(datosUsuario[4], formatter);
                usuario.setFechaNacimiento(fecha);

                usuario.setPassword(datosUsuario[5]);
                usuario.setSexo(datosUsuario[6]);
                usuario.setTelefono(datosUsuario[7]);
                usuario.setCelular(datosUsuario[8]);
                usuario.setCurp(datosUsuario[9]);
                usuario.setUserName(datosUsuario[10]);
                usuario.getRol().setIdRol(Integer.parseInt(datosUsuario[11]));
                direccion.setCalle(datosUsuario[12]);
                direccion.setNumeroInterior(datosUsuario[13]);
                direccion.setNumeroExterior(datosUsuario[14]);
                colonia.setIdColonia(Integer.parseInt(datosUsuario[15]));

                usuarios.add(usuario);
                fila++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return usuarios;
    }

    public List<Usuario> LecturaArchivoXLSX(File archivo) {

        List<Usuario> usuarios = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(archivo); XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();

            int fila = 1;

            for (Row row : sheet) {
                Usuario usuario = new Usuario();
                usuario.setRol(new Rol());
                Direccion direccion = new Direccion();
                Colonia colonia = new Colonia();

                direccion.setColonia(colonia);
                usuario.setDirecciones(new ArrayList<>());
                usuario.getDirecciones().add(direccion);

                usuario.setNombre(row.getCell(0).toString());
                usuario.setApellidoPaterno(row.getCell(1).toString());
                usuario.setApellidoMaterno(row.getCell(2).toString());
                usuario.setEmail(row.getCell(3).toString());

                Cell celdaFecha = row.getCell(4);
                LocalDate fecha;

                if (celdaFecha.getCellType() == CellType.NUMERIC) {
                    fecha = celdaFecha.getLocalDateTimeCellValue().toLocalDate();

                } else {
                    String fechaTexto = celdaFecha.getStringCellValue();

                    try {
                        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        fecha = LocalDate.parse(fechaTexto, formatter1);
                    } catch (Exception ex1) {
                        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("M/d/yy");
                        fecha = LocalDate.parse(fechaTexto, formatter2);
                    }
                }

                usuario.setFechaNacimiento(fecha);
                usuario.setPassword(row.getCell(5).toString());
                usuario.setSexo(row.getCell(6).toString());
                usuario.setTelefono(dataFormatter.formatCellValue(row.getCell(7)));
                usuario.setCelular(dataFormatter.formatCellValue(row.getCell(8)));
                usuario.setCurp(row.getCell(9).toString());
                usuario.setUserName(row.getCell(10).toString());

                usuario.getRol().setIdRol(Integer.parseInt(dataFormatter.formatCellValue(row.getCell(11))));

                direccion.setCalle(dataFormatter.formatCellValue(row.getCell(12)));
                direccion.setNumeroInterior(dataFormatter.formatCellValue(row.getCell(13)));
                direccion.setNumeroExterior(dataFormatter.formatCellValue(row.getCell(14)));

                colonia.setIdColonia(Integer.parseInt(dataFormatter.formatCellValue(row.getCell(15))));

                usuarios.add(usuario);
                fila++;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return usuarios;
    }

    @Autowired
    private ValidationService validationService;

    public List<ErroresArchivo> ValidarDatos(List<Usuario> usuarios) {

        List<ErroresArchivo> errores = new ArrayList<>();

        int fila = 1;

        for (Usuario usuario : usuarios) {

            BindingResult bindingResult = validationService.ValidateObject(usuario);

            System.out.println("Errores en fila " + fila + ": " + bindingResult.getErrorCount());

            if (bindingResult.hasErrors()) {

                for (FieldError fieldError : bindingResult.getFieldErrors()) {

                    ErroresArchivo error = new ErroresArchivo();
                    error.fila = fila;
                    error.dato = fieldError.getField();
                    error.descripcion = fieldError.getDefaultMessage();

                    errores.add(error);
                }
            }

            fila++;
        }

        return errores;
    }
}

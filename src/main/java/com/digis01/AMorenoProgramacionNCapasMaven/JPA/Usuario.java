package com.digis01.AMorenoProgramacionNCapasMaven.JPA;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private int IdUsuario;
    
    @Pattern(regexp = "^[a-zá-úÁ-ÚA-Z\\s]+$", message = "Solo puedes escribir letras en este campo")
    @NotEmpty(message = "Este campo no puede estar vacio")
    @Size(min = 2, max = 50, message = "Debe tener más de dos letras minimo")
    @Column(name = "nombre")
    private String Nombre;
    
    @Pattern(regexp = "^[a-zá-úÁ-ÚA-Z\\s]+$", message = "Solo puedes escribir letras en este campo")
    @NotEmpty(message = "Este campo no puede estar vacio")
    @Size(min = 2, max = 50, message = "Debe tener más de dos letras minimo")
    @Column(name = "apellidopaterno")
    private String ApellidoPaterno;
    
    @Pattern(regexp = "^[a-zá-úÁ-ÚA-Z\\s]+$", message = "Solo puedes escribir letras en este campo")
    @NotEmpty(message = "Este campo no puede estar vacio")
    @Size(min = 2, max = 50, message = "Debe tener más de dos letras minimo")
    @Column(name = "apellidomaterno")
    private String ApellidoMaterno;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Esta dirección de correo no es válida")
    @NotEmpty(message = "Este campo no puede estar vacío")
    @Size(min = 5, max = 100, message = "Debe tener entre 5 y 100 caracteres")
    @Column(name = "email")
    private String Email;
    
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,}$", message = "Este campo debe contener al menos un número, una letra mayúscula, una mínuscula y un caracter especial")
    @NotEmpty(message = "Este campo no puede estar vacio")
    @Size(min = 2, max = 20, message = "Debe tener más de 2 Digitos minimo y maximo 20")
    @Column(name = "password")
    private String Password;
    
    @Pattern(regexp = "^[A-Za-z]+$", message = "Solo puedes escribir una letra en este campo")
    @NotEmpty(message = "Este campo no puede estar vacio, selecciona una opción")
    @Size(min = 1, max = 2, message = "Debe tener más de 2 Digitos minimo y maximo 20")
    @Column(name = "sexo")
    private String Sexo;
    
    @Pattern(regexp = "^[0-9]{10}$", message = "Este campo solo debe contener numeros")
    @NotEmpty(message = "Este campo no puede estar vacio")
    @Size(min = 10, max = 10, message = "Debe tener maximo 10 números")
    @Column(name = "telefono")
    private String Telefono;
    
    @Pattern(regexp = "^[0-9]{10}$", message = "Este campo solo debe contener numeros")
    @NotEmpty(message = "Este campo no puede estar vacio")
    @Size(min = 10, max = 10, message = "Debe tener maximo 10 números")
    @Column(name = "celular")
    private String Celular;
    
    //@Pattern(regexp = "^[A-Z][AEIOU][A-Z]{2}\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])[HM](AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS)[A-Z]{3}[A-Z0-9]\\d$", message = "CURP inválida")
    @NotEmpty(message = "La CURP no puede estar vacía")
    @Size(min = 18, max = 18, message = "La CURP debe tener 18 caracteres")
    @Column(name = "curp")
    private String Curp;
    
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).+$", message = "Debes de al menos escribir un número, una letra mayúscula y una mínuscula")
    @NotEmpty(message = "Este campo no puede estar vacio")
    @Size(min = 5, max = 50, message = "Debe tener más de 5 Digitos minimo y maximo 20")
    @Column(name = "username")
    private String UserName;
    
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha debe ser anterior al día de hoy")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fechanacimiento")
    private LocalDate FechaNacimiento;

    @Lob
    @Column(name = "imagen")
    private String Imagen;

    @Column(name = "estatus")
    private int Estatus = 1;    
    
    @ManyToOne
    @JoinColumn(name = "idrol")
    private Rol Rol;

    @OneToMany(mappedBy = "Usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Direccion> Direcciones;
    
    
    public int getEstatus() {
        return Estatus;
    }

    public void setEstatus(int Estatus) {
        this.Estatus = Estatus;
    }
    
    public Usuario() {
        Direcciones = new ArrayList<>();
        Rol = new Rol();
    }
    
    public int getIdUsuario(){
       return IdUsuario;
    }
    public void setIdUsuario(int IdUsuario){
        this.IdUsuario = IdUsuario;
    }
    
    public String getNombre(){
        return Nombre;
    }
    
    public void setNombre(String Nombre){
        this.Nombre = Nombre;
    }
    
    public String getApellidoPaterno(){
        return ApellidoPaterno;
    }
    
    public void setApellidoPaterno(String ApellidoPaterno){
        this.ApellidoPaterno = ApellidoPaterno;
    }
    
    public String getApellidoMaterno(){
        return ApellidoMaterno;
    }
    
    public void setApellidoMaterno(String ApellidoMaterno){
        this.ApellidoMaterno = ApellidoMaterno;
    }
    
    public String getEmail(){
        return Email;
    }
    
    public void setEmail(String Email){
        this.Email = Email;
    }
    
    public String getPassword(){
        return Password;
    }
    
    public void setPassword(String Password){
        this.Password = Password;
    }
    
    public String getSexo(){
        return Sexo;
    }
    
    public void setSexo(String Sexo){
        this.Sexo = Sexo;
    }
    
    public String getTelefono(){
        return Telefono;
    }
    
    public void setTelefono(String Telefono){
        this.Telefono = Telefono;
    }
    
    public String getCelular(){
        return Celular;
    }
    
    public void setCelular(String Celular){
        this.Celular = Celular;
    }
    
    public String getCurp(){
        return Curp;
    }
    
    public void setCurp(String Curp){
        this.Curp = Curp;
    }
    
    public String getUserName(){
        return UserName;
    }
    
    public void setUserName(String UserName){
        this.UserName = UserName;
    }
    
    public LocalDate getFechaNacimiento(){
        return FechaNacimiento;
    }
    
    public void setFechaNacimiento(LocalDate FechaNacimiento){
        this.FechaNacimiento = FechaNacimiento;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }

    public Rol getRol() {
        return Rol;
    }

    public void setRol(Rol Rol) {
        this.Rol = Rol;
    }

    public List<Direccion> getDirecciones() {
        return Direcciones;
    }

    public void setDirecciones(List<Direccion> Direcciones) {
        this.Direcciones = Direcciones;
    }
}
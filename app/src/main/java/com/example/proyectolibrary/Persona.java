package com.example.proyectolibrary;

public class Persona {
    private String uid;
    private String Nombre;
    private int Identificacion;
    private String FechaNacimiento;
    private String Departamento_N;
    private String Municipio_N;
    private int Telefono;
    private String Email;
    private String Password;




    @Override
    public String toString(){
        return getNombre();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getIdentificacion() {
        return Identificacion;
    }

    public void setIdentificacion(int identificacion) {
        Identificacion = identificacion;
    }

    public String getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        FechaNacimiento = fechaNacimiento;
    }

    public String getDepartamento_N() {
        return Departamento_N;
    }

    public void setDepartamento_N(String departamento_N) {
        Departamento_N = departamento_N;
    }

    public String getMunicipio_N() {
        return Municipio_N;
    }

    public void setMunicipio_N(String municipio_N) {
        Municipio_N = municipio_N;
    }

    public int getTelefono() {
        return Telefono;
    }

    public void setTelefono(int telefono) {
        Telefono = telefono;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}

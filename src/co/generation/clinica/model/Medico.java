package co.generation.clinica.model;

import co.generation.clinica.interfaces.Registrable;

public class Medico implements Registrable {
    private int id;
    private String nombre;
    private String apellido;
    private Especialidad especialidad;

    public Medico(String nombre, String apellido, Especialidad especialidad){
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
    }

    public Medico( int id,String nombre, String apellido,Especialidad especialidad) {
        this.id = id;
        this.especialidad = especialidad;
        this.apellido = apellido;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public boolean esValido(){
        if(nombre == null || nombre.trim().isEmpty()){
            return false;
        }

        if(apellido == null || apellido.trim().isEmpty()){
            return false;
        }

        if(especialidad == null){
            return false;
        }

        return true;
    }

    @Override
    public String getDatosRegistro() {
        return "Médico: " + nombre + " " + apellido
                + " - Especialidad: " + especialidad;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }

        if(!(obj instanceof Medico)){
            return false;
        }

        Medico otro = (Medico) obj;

        if(nombre.equalsIgnoreCase(otro.nombre) && apellido.equalsIgnoreCase(otro.apellido)){
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return "Dr. " +  getNombre() + " " + getApellido() + " - " + getEspecialidad();
    }
}

package co.generation.clinica.model;

public class Paciente {
    private int id;
    private String cedula;
    private String nombre;
    private String apellido;
    private String telefono;

    public Paciente(String cedula, String nombre, String apellido, String telefono) {
        this.setCedula(cedula);
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setTelefono(telefono);
    }

    public Paciente(int id, String cedula, String nombre, String apellido, String telefono) {
        this.id = id;
        this.setCedula(cedula);
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setTelefono(telefono);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            throw new IllegalArgumentException("La cedula no puede ser vacia.");
        }

        if (!cedula.matches("^\\d+$")) {
            throw new IllegalArgumentException("La cedula solo puede contener numeros.");
        }

        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo ni vacio");
        }

        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede ser nulo ni vacio");
        }

        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono == null || !telefono.matches("^[0-9]{7,10}$")) {
            throw new IllegalArgumentException("El telefono debe tener mas de 7 digitos y menos de 10");
        }

        this.telefono = telefono;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Paciente) {
            Paciente paciente = (Paciente) o;

            if (this.cedula.equals(paciente.cedula)) {
                return true;
            }

            return false;
        }
        return false;
    }

    @Override
    public String toString () {
        return nombre + " " + apellido + " - " + cedula + " - " + telefono;
    }
}


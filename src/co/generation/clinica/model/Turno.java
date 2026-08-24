package co.generation.clinica.model;

import java.time.LocalDateTime;

public class Turno {
    private int id;
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime fechaHora;
    private EstadoTurno estado;

    public Turno(Paciente paciente, Medico medico, LocalDateTime fechaHora, EstadoTurno estado) {
        this.paciente = paciente;
        this.medico = medico;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    public Turno(int id, Paciente paciente, Medico medico, LocalDateTime fechaHora, EstadoTurno estado) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public EstadoTurno getEstado() {
        return estado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("No se puede asignar un turno sin especificar el paciente.");
        }

        this.paciente = paciente;
    }

    public void setMedico(Medico medico) {
        if (medico == null) {
            throw new IllegalArgumentException("No se puede asignar un turno sin especificar el médico.");
        }

        this.medico = medico;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        if (fechaHora == null) {
            throw new IllegalArgumentException("No se puede asignar un turno sin especificar la fecha y hora.");
        }

        this.fechaHora = fechaHora;
    }

    public void setEstado(EstadoTurno estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object otroObjeto) {
        if (this == otroObjeto) return true;
        if (otroObjeto == null || getClass() != otroObjeto.getClass()) return false;

        Turno otroTurno = (Turno) otroObjeto;

        return this.medico.equals(otroTurno.medico) && this.fechaHora.equals(otroTurno.fechaHora);
    }

    @Override
    public String toString() {
        String nombrePaciente = this.getPaciente().getNombre() + " " + this.getPaciente().getApellido();
        String nombreMedico = "Dr. " + this.getMedico().getNombre() + " " + this.getMedico().getApellido();
        String especialidad = this.getMedico().getEspecialidad().toString();

        return "[" + this.getEstado() + "] " + nombrePaciente + " — " + nombreMedico + " (" + especialidad + ") — " + this.getFechaHora();
    }
}

package co.generation.clinica.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import co.generation.clinica.interfaces.Consultable;
import co.generation.clinica.model.EstadoTurno;
import co.generation.clinica.model.Medico;
import co.generation.clinica.model.Paciente;
import co.generation.clinica.model.Turno;

public class ClinicaService implements Consultable {

    private List<Paciente> pacientes;
    private List<Medico> medicos;
    private List<Turno> turnos;

    public ClinicaService() {
        this.pacientes = new ArrayList<>();
        this.medicos = new ArrayList<>();
        this.turnos = new ArrayList<>();
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void registrarPaciente(Paciente paciente) {
        if (!paciente.esValido()) {
            throw new IllegalArgumentException(
                    "Los datos del paciente no son válidos.");
        }
        if (buscarPorCedula(paciente.getCedula()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe un paciente con esa cédula.");
        }
        paciente.setId(pacientes.size() + 1);
        pacientes.add(paciente);
    }

    public Paciente buscarPorCedula(String cedula) {
        for (Paciente paciente : pacientes) {
            if (paciente.getCedula().equals(cedula)) {
                return paciente;
            }
        }
        return null;
    }

    public void listarPacientes() {
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
            return;
        }
        List<Paciente> copia = new ArrayList<>(pacientes);
        copia.sort(
                Comparator.comparing(Paciente::getApellido)
                        .thenComparing(Paciente::getNombre)
        );
        for (Paciente paciente : copia) {
            System.out.println(paciente);
        }
    }

    public void registrarMedico(Medico medico) {
        if (!medico.esValido()) {
            throw new IllegalArgumentException(
                    "Los datos del médico no son válidos.");
        }
        medico.setId(medicos.size() + 1);
        medicos.add(medico);
    }

    public Medico buscarPorNombreApellido(
            String nombre,
            String apellido) {
        for (Medico medico : medicos) {
            if (medico.getNombre().equalsIgnoreCase(nombre)
                    && medico.getApellido().equalsIgnoreCase(apellido)) {
                return medico;
            }
        }
        return null;
    }

    public void listarMedicos() {
        if (medicos.isEmpty()) {
            System.out.println("No hay médicos registrados.");
            return;
        }
        List<Medico> copia = new ArrayList<>(medicos);
        copia.sort(
                Comparator.comparing(Medico::getEspecialidad)
                        .thenComparing(Medico::getApellido)
        );
        for (Medico medico : copia) {
            System.out.println(medico);
        }
    }

    public void asignarTurno(Turno turno) {
        if (turno.getPaciente() == null) {
            throw new IllegalArgumentException(
                    "Debe seleccionar un paciente.");
        }
        if (turno.getMedico() == null) {
            throw new IllegalArgumentException(
                    "Debe seleccionar un médico.");
        }

        for (Turno t : turnos) {
            boolean mismoMedico =
                    t.getMedico().equals(turno.getMedico());
            boolean mismaFechaHora =
                    t.getFechaHora().equals(turno.getFechaHora());
            if (mismoMedico && mismaFechaHora) {
                throw new IllegalArgumentException(
                        "El médico ya tiene un turno asignado en ese horario.");
            }
        }
        turno.setId(turnos.size() + 1);
        turnos.add(turno);
    }
    public void cancelarTurno(int idTurno) {

        for (Turno turno : turnos) {
            if (turno.getId() == idTurno) {
                if (turno.getEstado() == EstadoTurno.ATENDIDO) {
                    System.out.println(
                            "No se puede cancelar un turno atendido.");
                    return;
                }
                turno.setEstado(EstadoTurno.CANCELADO);
                System.out.println("Turno cancelado correctamente.");
                return;
            }
        }
        System.out.println("No se encontró el turno.");
    }

    public void atenderTurno(int idTurno) {
        for (Turno turno : turnos) {
            if (turno.getId() == idTurno) {
                if (turno.getEstado() == EstadoTurno.CANCELADO) {
                    System.out.println(
                            "No se puede atender un turno cancelado.");
                    return;
                }
                turno.setEstado(EstadoTurno.ATENDIDO);
                System.out.println("Turno marcado como atendido.");
                return;
            }
        }
        System.out.println("No se encontró el turno.");
    }

    public void listarTurnos() {
        if (turnos.isEmpty()) {
            System.out.println("No hay turnos registrados.");
            return;
        }
        turnos.sort(
                Comparator.comparing(Turno::getFechaHora)
        );

        for (Turno turno : turnos) {
            System.out.println(turno);
        }
    }

    @Override
    public List<Turno> listarTurnosDelDia(LocalDate fecha) {
        List<Turno> resultado = new ArrayList<>();

        for (Turno turno : turnos) {
            if (turno.getFechaHora()
                    .toLocalDate()
                    .equals(fecha)) {
                resultado.add(turno);
            }
        }
        resultado.sort(
                Comparator.comparing(Turno::getFechaHora)
        );
        return resultado;
    }

    @Override
    public List<Turno> buscarPorMedico(Medico medico) {
        List<Turno> resultado = new ArrayList<>();

        for (Turno turno : turnos) {
            if (turno.getMedico().equals(medico)) {
                resultado.add(turno);
            }
        }
        return resultado;
    }

    @Override
    public List<Turno> buscarPorPaciente(Paciente paciente) {
        List<Turno> resultado = new ArrayList<>();

        for (Turno turno : turnos) {
            if (turno.getPaciente().equals(paciente)) {
                resultado.add(turno);
            }
        }
        return resultado;
    }
}
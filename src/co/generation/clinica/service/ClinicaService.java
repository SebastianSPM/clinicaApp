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
        pacientes = new ArrayList<>();
        medicos = new ArrayList<>();
        turnos = new ArrayList<>();
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

    //paciente
    public void registrarPaciente(Paciente p) {

        if (pacientes.contains(p)) {
            System.out.println("Error: ya existe un paciente con esa cédula.");
            return;
        }

        int maxId = 0;

        for (Paciente paciente : pacientes) {
            if (paciente.getId() > maxId) {
                maxId = paciente.getId();
            }
        }

        p.setId(maxId + 1);
        pacientes.add(p);
        System.out.println("Paciente registrado exitosamente.");
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
        copia.sort(Comparator.comparing(Paciente::getApellido).thenComparing(Paciente::getNombre));

        for (Paciente paciente : copia) {
            System.out.println(paciente);
        }
    }

    //medicos
    public void registrarMedico(Medico m) {
        if (!m.esValido()) {
            System.out.println("Error: datos del médico inválidos.");
            return;
        }
        if (medicos.contains(m)) {
            System.out.println("Error: el médico ya existe.");
            return;
        }
        int maxId = 0;

        for (Medico medico : medicos) {
            if (medico.getId() > maxId) {
                maxId = medico.getId();
            }
        }

        m.setId(maxId + 1);
        medicos.add(m);
        System.out.println("Médico registrado exitosamente.");
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
        copia.sort(Comparator.comparing(Medico::getEspecialidad).thenComparing(Medico::getApellido));

        for (Medico medico : copia) {
            System.out.println(medico);
        }
    }

    //turnos
    public void asignarTurno(Turno t) {
        if (!pacientes.contains(t.getPaciente())) {
            System.out.println("Error: paciente no registrado.");
            return;
        }

        if (!medicos.contains(t.getMedico())) {
            System.out.println("Error: médico no registrado.");
            return;
        }

        for (Turno turno : turnos) {
            boolean mismoMedico =
                    turno.getMedico().equals(t.getMedico());
            boolean mismaFechaHora =
                    turno.getFechaHora().equals(t.getFechaHora());
            if (mismoMedico && mismaFechaHora) {
                System.out.println(
                        "Error: el médico ya tiene un turno en ese horario.");
                return;
            }
        }

        int maxId = 0;

        for (Turno turno : turnos) {
            if (turno.getId() > maxId) {
                maxId = turno.getId();
            }
        }

        t.setId(maxId + 1);
        turnos.add(t);
        System.out.println("Turno asignado exitosamente.");
    }

    public void cancelarTurno(int idTurno) {
        for (Turno turno : turnos) {
            if (turno.getId() == idTurno) {
                if (turno.getEstado() == EstadoTurno.ATENDIDO) {
                    System.out.println(
                            "No se puede cancelar un turno atendido.");
                    return;
                }

                if (turno.getEstado() == EstadoTurno.CANCELADO) {
                    System.out.println(
                            "El turno ya está cancelado.");
                    return;
                }

                turno.setEstado(EstadoTurno.CANCELADO);
                System.out.println(
                        "Turno cancelado correctamente.");
                return;
            }
        }

        System.out.println("Turno no encontrado.");
    }

    public void cambiarEstadoTurno(int idTurno, EstadoTurno nuevo) {
        for (Turno turno : turnos) {
            if (turno.getId() == idTurno) {
                turno.setEstado(nuevo);
                System.out.println("Estado actualizado a: " + nuevo);
                return;
            }
        }

        System.out.println("Turno no encontrado.");
    }

    public void listarTurnos() {
        if (turnos.isEmpty()) {
            System.out.println("No hay turnos registrados.");
            return;
        }

        List<Turno> copia = new ArrayList<>(turnos);
        copia.sort(Comparator.comparing(Turno::getFechaHora));

        for (Turno turno : copia) {
            System.out.println(turno);
        }
    }

    public Turno buscarTurnoPorId(int idTurno) {
        for (Turno turno : turnos) {
            if (turno.getId() == idTurno) {
                return turno;
            }
        }

        return null;
    }

    //consultable
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
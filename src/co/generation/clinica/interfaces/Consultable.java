package co.generation.clinica.interfaces;

import java.time.LocalDate;
import java.util.List;

import co.generation.clinica.model.Medico;
import co.generation.clinica.model.Paciente;
import co.generation.clinica.model.Turno;

public interface Consultable {
    List<Turno> listarTurnosDelDia(LocalDate fecha);
    List<Turno> buscarPorMedico(Medico medico);
    List<Turno> buscarPorPaciente(Paciente paciente);
}
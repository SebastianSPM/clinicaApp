package co.generation.clinica;

import co.generation.clinica.model.*;
import co.generation.clinica.service.ClinicaService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Scanner sc = new Scanner(System.in);
    private final ClinicaService servicio;

    public Menu(ClinicaService servicio) {
        this.servicio = servicio;
    }

    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            mostrarMenu();

            int opcion = leerEntero("Elige una opción: ");

            switch (opcion) {
                case 1 -> registrarPaciente();
                case 2 -> registrarMedico();
                case 3 -> asignarTurno();
                case 4 -> listarTurnosDelDia();
                case 5 -> cancelarTurno();
                case 6 -> verTurnosPorMedico();
                case 7 -> verTurnosPorPaciente();
                case 8 -> cambiarEstadoTurno();
                case 9 -> servicio.listarPacientes();
                case 10 -> servicio.listarMedicos();
                case 0 -> salir = true;
                default -> System.out.println("Opción inválida.");
            }

            System.out.println();
        }
    }

    private void mostrarMenu() {
        System.out.println("╔═══════════════════════════════════╗");
        System.out.println("║       CLINICAAPP — MENÚ           ║");
        System.out.println("╠═══════════════════════════════════╣");
        System.out.println("║ 1. Registrar paciente             ║");
        System.out.println("║ 2. Registrar médico               ║");
        System.out.println("║ 3. Asignar turno                  ║");
        System.out.println("║ 4. Listar turnos del día          ║");
        System.out.println("║ 5. Cancelar turno                 ║");
        System.out.println("║ 6. Ver turnos por médico          ║");
        System.out.println("║ 7. Ver turnos por paciente        ║");
        System.out.println("║ 8. Cambiar estado de turno        ║");
        System.out.println("║ 9. Listar pacientes               ║");
        System.out.println("║ 10. Listar médicos                ║");
        System.out.println("║ 0. Salir                          ║");
        System.out.println("╚═══════════════════════════════════╝");
    }

    private void registrarPaciente() {
        System.out.println("— Registrar paciente —");

        try {
            String cedula = leerTexto("Cédula: ");
            String nombre = leerTexto("Nombre: ");
            String apellido = leerTexto("Apellido: ");
            String telefono = leerTexto("Teléfono: ");

            Paciente paciente = new Paciente(cedula,nombre,apellido,telefono);

            servicio.registrarPaciente(paciente);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void registrarMedico() {
        System.out.println("— Registrar médico —");

        try {
            String nombre = leerTexto("Nombre: ");
            String apellido = leerTexto("Apellido: ");
            Especialidad especialidad = elegirEspecialidad();

            Medico medico = new Medico(nombre,apellido, especialidad);
            servicio.registrarMedico(medico);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void asignarTurno() {
        System.out.println("— Asignar turno —");

        try {
            String cedula = leerTexto("Cédula del paciente: ");
            Paciente paciente = servicio.buscarPorCedula(cedula);

            if (paciente == null) {
                System.out.println("Paciente no encontrado.");
                return;
            }

            String nombreMedico = leerTexto("Nombre del médico: ");
            String apellidoMedico = leerTexto("Apellido del médico: ");

            Medico medico = servicio.buscarPorNombreApellido(nombreMedico, apellidoMedico
            );

            if (medico == null) {
                System.out.println("Médico no encontrado.");
                return;
            }

            int anio = leerEntero("Año: ");
            int mes = leerEntero("Mes: ");
            int dia = leerEntero("Día: ");
            int hora = leerEntero("Hora: ");
            int minuto = leerEntero("Minuto: ");

            LocalDateTime fechaHora = LocalDateTime.of(anio, mes, dia, hora, minuto);

            Turno turno = new Turno(paciente,medico, fechaHora, EstadoTurno.PENDIENTE);

            servicio.asignarTurno(turno);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listarTurnosDelDia() {
        System.out.println("— Turnos del día —");

        int anio = leerEntero("Año: ");
        int mes = leerEntero("Mes: ");
        int dia = leerEntero("Día: ");

        LocalDate fecha = LocalDate.of(anio, mes, dia);

        List<Turno> turnos = servicio.listarTurnosDelDia(fecha);

        if (turnos.isEmpty()) {
            System.out.println("No hay turnos para esa fecha.");
            return;
        }

        turnos.forEach(System.out::println);
    }

    private void cancelarTurno() {
        System.out.println("— Cancelar turno —");

        int id = leerEntero("ID del turno: ");

        servicio.cancelarTurno(id);
    }

    private void verTurnosPorMedico() {
        System.out.println("— Turnos por médico —");

        String nombre = leerTexto("Nombre del médico: ");
        String apellido = leerTexto("Apellido del médico: ");

        Medico medico = servicio.buscarPorNombreApellido(nombre, apellido);

        if (medico == null) {
            System.out.println("Médico no encontrado.");
            return;
        }

        List<Turno> turnos = servicio.buscarPorMedico(medico);

        if (turnos.isEmpty()) {
            System.out.println("Este médico no tiene turnos.");
            return;
        }

        turnos.forEach(System.out::println);
    }

    private void verTurnosPorPaciente() {
        System.out.println("— Turnos por paciente —");

        String cedula = leerTexto("Cédula del paciente: ");

        Paciente paciente = servicio.buscarPorCedula(cedula);

        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }

        List<Turno> turnos = servicio.buscarPorPaciente(paciente);

        if (turnos.isEmpty()) {
            System.out.println("Este paciente no tiene turnos.");
            return;
        }

        turnos.forEach(System.out::println);
    }

    private void cambiarEstadoTurno() {
        System.out.println("— Cambiar estado de turno —");

        int id = leerEntero("ID del turno: ");

        EstadoTurno nuevoEstado = elegirEstado();

        servicio.cambiarEstadoTurno(id, nuevoEstado);
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine().trim();

            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un número válido.");
            }
        }
    }

    private Especialidad elegirEspecialidad() {
        System.out.println("Especialidades: " + Arrays.toString(Especialidad.values()));

        while (true) {
            String valor = leerTexto("Especialidad: ").toUpperCase();

            try {
                return Especialidad.valueOf(valor);
            } catch (IllegalArgumentException e) {
                System.out.println("Especialidad no válida. Intenta de nuevo.");
            }
        }
    }

    private EstadoTurno elegirEstado() {
        System.out.println("Estados: " + Arrays.toString(EstadoTurno.values()));

        while (true) {
            String valor = leerTexto("Nuevo estado: ").toUpperCase();

            try {
                return EstadoTurno.valueOf(valor);
            } catch (IllegalArgumentException e) {
                System.out.println("Estado no válido. Intenta de nuevo.");
            }
        }
    }
}
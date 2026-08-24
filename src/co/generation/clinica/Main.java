package co.generation.clinica;

import co.generation.clinica.datos.DatosCSV;
import co.generation.clinica.service.ClinicaService;

public class Main {
    public static void main(String[] args) {
        // Secuencia en main():
        ClinicaService servicio = new ClinicaService();
        // DatosCSV.cargar(servicio);
        // bucle while con Scanner para el menú...
        // Al salir (opción 0):
        // DatosCSV.guardar(servicio);
        System.out.println("Hasta pronto. Datos guardados.");
    }
}

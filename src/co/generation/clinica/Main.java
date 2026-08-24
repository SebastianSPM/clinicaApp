package co.generation.clinica;

import co.generation.clinica.datos.DatosCSV;
import co.generation.clinica.service.ClinicaService;

public class Main {
    public static void main(String[] args) {
        ClinicaService servicio = new ClinicaService();
        DatosCSV.cargar(servicio);

        Menu menu = new Menu(servicio);
        menu.iniciar();

        DatosCSV.guardar(servicio);

        System.out.println("Hasta pronto. Datos guardados.");
    }
}
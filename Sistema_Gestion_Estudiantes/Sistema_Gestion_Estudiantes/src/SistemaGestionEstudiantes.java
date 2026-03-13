import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SistemaGestionEstudiantes {

    public static void main(String[] args) {

        List<Estudiante> listaEstudiantes = new ArrayList<>();

        String rutaArchivo = "estudiantes.csv"; 
        String separador = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] partes = linea.split(separador);

                if (partes.length != 4) {
                    System.out.println("Línea mal formateada, se ignora: " + linea);
                    continue;
                }

                try {
                    String nombre = partes[0].trim();
                    int edad = Integer.parseInt(partes[1].trim());
                    String ciudad = partes[2].trim();
                    double calificacion = Double.parseDouble(partes[3].trim());

                    Estudiante e = new Estudiante(nombre, edad, ciudad, calificacion);
                    listaEstudiantes.add(e);
                } catch (NumberFormatException ex) {
                    System.out.println("Error de formato numérico en línea: " + linea);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return; 
        }

        System.out.println("=== Lista completa de estudiantes ===");
        for (Estudiante e : listaEstudiantes) {
            System.out.println(e);
        }

        Set<String> ciudadesUnicas = new HashSet<>();
        for (Estudiante e : listaEstudiantes) {
            ciudadesUnicas.add(e.getCiudad());
        }

        System.out.println("\n=== Ciudades únicas de los estudiantes ===");
        for (String ciudad : ciudadesUnicas) {
            System.out.println(ciudad);
        }

        Map<String, List<Estudiante>> estudiantesPorCiudad = new HashMap<>();

        for (Estudiante e : listaEstudiantes) {
            String ciudad = e.getCiudad();

            estudiantesPorCiudad.putIfAbsent(ciudad, new ArrayList<>());

            estudiantesPorCiudad.get(ciudad).add(e);
        }

        System.out.println("\n=== Estudiantes agrupados por ciudad ===");
        for (Map.Entry<String, List<Estudiante>> entrada : estudiantesPorCiudad.entrySet()) {
            String ciudad = entrada.getKey();
            List<Estudiante> estudiantesDeCiudad = entrada.getValue();

            System.out.println("Ciudad: " + ciudad);
            for (Estudiante e : estudiantesDeCiudad) {
                System.out.println("  " + e);
            }
        }

        Queue<Estudiante> colaAtencion = new LinkedList<>();

        colaAtencion.addAll(listaEstudiantes);

        System.out.println("\n=== Simulación de atención de estudiantes (cola FIFO) ===");
        while (!colaAtencion.isEmpty()) {
            Estudiante siguiente = colaAtencion.peek();
            System.out.println("Atendiendo a: " + siguiente);

            colaAtencion.poll();
        }

        System.out.println("\nNo quedan estudiantes en la cola.");
    }
}

import java.util.Arrays;
import java.util.Scanner;

public class ArraysUtilitiesTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // i. Saludo al usuario
        System.out.println("=================================================");
        System.out.println("   ¡Bienvenido al Probador de ArraysUtilities!  ");
        System.out.println("=================================================");

        // Arreglo de prueba inicial
        int[] data = {14, 5, 29, 8, 42, 3, 19, 7, 33};

        // iv. Impresión del estado inicial del arreglo usando Arrays.toString()
        System.out.println("\nArreglo cargado actualmente: " + Arrays.toString(data));

        boolean running = true;
        while (running) {
            // ii. Menú de opciones para el usuario
            System.out.println("\n---------------- MENÚ DE OPCIONES ----------------");
            System.out.println("1. Mostrar arreglo actual (Arrays.toString)");
            System.out.println("2. Obtener el valor máximo (findMax)");
            System.out.println("3. Obtener el valor mínimo (findMin)");
            System.out.println("4. Calcular la suma total (calculateSum)");
            System.out.println("5. Calcular el promedio aritmético (calculateAverage)");
            System.out.println("6. Invertir el arreglo (reverse)");
            System.out.println("7. Buscar un valor específico (linearSearch)");
            System.out.println("8. Restablecer arreglo a valores iniciales");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opción: ");

            int option;
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(">> Error: Ingresa un número válido del menú.");
                continue;
            }

            // iii. Llamada a cada método según la opción elegida
            switch (option) {
                case 1:
                    // iv. Uso explícito de Arrays.toString()
                    System.out.println(">> Contenido del arreglo: " + Arrays.toString(data));
                    break;

                case 2:
                    try {
                        int max = ArraysUtilities.findMax(data);
                        System.out.println(">> Arreglo: " + Arrays.toString(data));
                        System.out.println(">> El valor máximo es: " + max);
                    } catch (IllegalArgumentException e) {
                        System.out.println(">> Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        int min = ArraysUtilities.findMin(data);
                        System.out.println(">> Arreglo: " + Arrays.toString(data));
                        System.out.println(">> El valor mínimo es: " + min);
                    } catch (IllegalArgumentException e) {
                        System.out.println(">> Error: " + e.getMessage());
                    }
                    break;

                case 4:
                    int sum = ArraysUtilities.calculateSum(data);
                    System.out.println(">> Arreglo: " + Arrays.toString(data));
                    System.out.println(">> La sumatoria de los elementos es: " + sum);
                    break;

                case 5:
                    try {
                        double avg = ArraysUtilities.calculateAverage(data);
                        System.out.println(">> Arreglo: " + Arrays.toString(data));
                        System.out.printf(">> El promedio es: %.2f\n", avg);
                    } catch (IllegalArgumentException e) {
                        System.out.println(">> Error: " + e.getMessage());
                    }
                    break;

                case 6:
                    // iv. Impresión del antes y después usando Arrays.toString()
                    System.out.println(">> Arreglo antes de invertir: " + Arrays.toString(data));
                    ArraysUtilities.reverse(data);
                    System.out.println(">> Arreglo después de invertir: " + Arrays.toString(data));
                    break;

                case 7:
                    System.out.print("Ingresa el número entero a buscar: ");
                    try {
                        int target = Integer.parseInt(scanner.nextLine());
                        int index = ArraysUtilities.linearSearch(data, target);
                        System.out.println(">> Arreglo evaluado: " + Arrays.toString(data));
                        if (index != -1) {
                            System.out.println(">> Elemento " + target + " localizado en el índice: [" + index + "].");
                        } else {
                            System.out.println(">> El elemento " + target + " no existe en el arreglo.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(">> Error: Entrada numérica inválida.");
                    }
                    break;

                case 8:
                    data = new int[]{14, 5, 29, 8, 42, 3, 19, 7, 33};
                    System.out.println(">> Arreglo restablecido: " + Arrays.toString(data));
                    break;

                case 0:
                    System.out.println("\n>> ¡Gracias por usar ArraysUtilitiesTester! Saliendo del programa...");
                    running = false;
                    break;

                default:
                    System.out.println(">> Opción fuera de rango. Por favor elige entre 0 y 8.");
                    break;
            }
        }

        scanner.close();
    }
}
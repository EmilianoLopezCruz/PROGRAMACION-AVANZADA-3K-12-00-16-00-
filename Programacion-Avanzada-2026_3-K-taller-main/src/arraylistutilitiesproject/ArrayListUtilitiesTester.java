package arraylistutilitiesproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ArrayListUtilitiesTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=================================================");
        System.out.println(" ¡Bienvenido al Probador de ArrayListUtilities! ");
        System.out.println("=================================================");

        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(15, 4, 8, 23, 4, 42, 16, 8, 15, 5));

        System.out.println("\nLista cargada por defecto: " + numbers.toString());

        boolean running = true;
        while (running) {

            System.out.println("\n------------- MENÚ DE OPCIONES -------------");
            System.out.println("1. Mostrar estado de la lista actual (toString)");
            System.out.println("2. Agregar un número a la lista");
            System.out.println("3. Obtener el valor máximo (findMax)");
            System.out.println("4. Obtener el valor mínimo (findMin)");
            System.out.println("5. Calcular la suma total (calculateSum)");
            System.out.println("6. Calcular el promedio (calculateAverage)");
            System.out.println("7. Invertir la lista (reverseList)");
            System.out.println("8. Eliminar elementos duplicados (removeDuplicates)");
            System.out.println("9. Restablecer lista a valores iniciales");
            System.out.println("0. Salir de la aplicación");
            System.out.print("Selecciona una opción: ");

            int option;
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(">> Entrada no válida. Ingresa un número del menú.");
                continue;
            }

            switch (option) {
                case 1:

                    System.out.println(">> Contenido de la lista: " + numbers.toString());
                    break;

                case 2:
                    System.out.print("Ingresa el número entero a agregar: ");
                    try {
                        int newNum = Integer.parseInt(scanner.nextLine());
                        numbers.add(newNum);
                        System.out.println(">> Elemento agregado exitosamente.");
                        System.out.println(">> Lista resultante: " + numbers.toString());
                    } catch (NumberFormatException e) {
                        System.out.println(">> Error: Debes ingresar un número entero válido.");
                    }
                    break;

                case 3:
                    try {
                        int max = ArrayListUtilities.findMax(numbers);
                        System.out.println(">> El valor máximo en la lista es: " + max);
                        System.out.println(">> Evaluado sobre: " + numbers.toString());
                    } catch (IllegalArgumentException e) {
                        System.out.println(">> Error: " + e.getMessage());
                    }
                    break;

                case 4:
                    try {
                        int min = ArrayListUtilities.findMin(numbers);
                        System.out.println(">> El valor mínimo en la lista es: " + min);
                        System.out.println(">> Evaluado sobre: " + numbers.toString());
                    } catch (IllegalArgumentException e) {
                        System.out.println(">> Error: " + e.getMessage());
                    }
                    break;

                case 5:
                    int sum = ArrayListUtilities.calculateSum(numbers);
                    System.out.println(">> Suma de los elementos: " + sum);
                    System.out.println(">> Evaluado sobre: " + numbers.toString());
                    break;

                case 6:
                    try {
                        double avg = ArrayListUtilities.calculateAverage(numbers);
                        System.out.printf(">> Promedio de la lista: %.2f\n", avg);
                        System.out.println(">> Evaluado sobre: " + numbers.toString());
                    } catch (IllegalArgumentException e) {
                        System.out.println(">> Error: " + e.getMessage());
                    }
                    break;

                case 7:
                    System.out.println(">> Lista antes de invertir: " + numbers.toString());
                    ArrayListUtilities.reverseList(numbers);
                    System.out.println(">> Lista después de invertir: " + numbers.toString());
                    break;

                case 8:
                    System.out.println(">> Lista antes de depurar: " + numbers.toString());
                    ArrayListUtilities.removeDuplicates(numbers);
                    System.out.println(">> Lista sin duplicados: " + numbers.toString());
                    break;

                case 9:
                    numbers = new ArrayList<>(Arrays.asList(15, 4, 8, 23, 4, 42, 16, 8, 15, 5));
                    System.out.println(">> Lista restablecida a valores por defecto: " + numbers.toString());
                    break;

                case 0:
                    System.out.println("\n>> ¡Gracias por utilizar el evaluador de utilidades! Finalizando ejecución...");
                    running = false;
                    break;

                default:
                    System.out.println(">> Opción no reconocida. Por favor selecciona una opción del 0 al 9.");
                    break;
            }
        }

        scanner.close();
    }
}

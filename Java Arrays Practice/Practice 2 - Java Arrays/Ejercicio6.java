import java.util.Arrays;

public class Ejercicio6 {
    public static void main(String[] args) {
        // Arreglo de prueba (desordenado)
        int[] array = {12, 3, 5, 7, 19, 1, 8, 20};

        // 6. Encontrar y mostrar la mediana
        // Paso 1: Ordenar el arreglo
        Arrays.sort(array);
        System.out.println("Arreglo ordenado: " + Arrays.toString(array));

        double median;
        int n = array.length;

        // Paso 2: Evaluar si la cantidad de elementos es impar o par
        if (n % 2 == 1) {
            median = array[n / 2];
        } else {
            median = (double) (array[(n / 2) - 1] + array[n / 2]) / 2.0;
        }

        System.out.println("La mediana es: " + median);
    }
}

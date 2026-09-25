package javaarrayspractice.practice2javaarrays;

import java.util.Arrays;

public class Ejercicio6 {
    public static void main(String[] args) {

        int[] array = {12, 3, 5, 7, 19, 1, 8, 20};

        Arrays.sort(array);
        System.out.println("Arreglo ordenado: " + Arrays.toString(array));

        double median;
        int n = array.length;

        if (n % 2 == 1) {
            median = array[n / 2];
        } else {
            median = (double) (array[(n / 2) - 1] + array[n / 2]) / 2.0;
        }

        System.out.println("La mediana es: " + median);
    }
}


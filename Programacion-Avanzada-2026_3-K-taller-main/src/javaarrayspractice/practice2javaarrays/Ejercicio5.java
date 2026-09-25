package javaarrayspractice.practice2javaarrays;

public class Ejercicio5 {
    public static void main(String[] args) {
        int[] numbers = {10, 20, 30, 40, 50, 65};

        double sum = 0;
        for (int i = 0; i < numbers.length; i++) {
            sum += numbers[i];
        }
        double mean = sum / numbers.length;

        System.out.printf("La media (promedio) es: %.2f\n", mean);
    }
}


package javaarrayspractice.practice3javaarrays;

import java.util.Arrays;

public class Ejercicio3 {
    public static void main(String[] args) {
        int[] number = new int[30];

        for (int i = 0; i < number.length; i++) {
            number[i] = 5;
        }

        System.out.println("Arreglo number inicializado con 5:");
        System.out.println(Arrays.toString(number));
    }
}


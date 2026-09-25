package javaarrayspractice.practice4javaarrays;

import java.util.Arrays;

public class Ejercicio3 {
    public static void main(String[] args) {
        int[] num = new int[20];

        for (int i = 0; i < num.length; i++) {
            num[i] = i * 2;
        }

        System.out.println("Arreglo num (doble del índice):");
        System.out.println(Arrays.toString(num));
    }
}


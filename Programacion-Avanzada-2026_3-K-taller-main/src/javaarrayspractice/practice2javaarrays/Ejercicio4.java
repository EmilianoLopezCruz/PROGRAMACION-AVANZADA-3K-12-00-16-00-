package javaarrayspractice.practice2javaarrays;

public class Ejercicio4 {
    public static void main(String[] args) {

        int[] data = new int[30];
        for (int i = 0; i < data.length; i++) {
            data[i] = (i + 1) * 2;
        }

        System.out.println("Valores del arreglo de 30 elementos:");
        for (int i = 0; i < data.length; i++) {
            System.out.println("Índice [" + i + "] = " + data[i]);
        }
    }
}


package javaarrayspractice.practice3javaarrays;

public class Ejercicio4 {
    public static void main(String[] args) {

        int[] array = new int[16];
        for (int i = 0; i < array.length; i++) {
            array[i] = (i + 1) * 3;
        }

        System.out.println("Valores del arreglo de 16 elementos:");
        for (int i = 0; i < array.length; i++) {
            System.out.println("Elemento [" + i + "] = " + array[i]);
        }
    }
}


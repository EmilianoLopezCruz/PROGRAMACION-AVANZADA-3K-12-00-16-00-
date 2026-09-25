package javaarrayspractice.practice4javaarrays;

public class Ejercicio5 {
    public static void main(String[] args) {
        // Arreglo de prueba de 16x32
        int[][] array = new int[16][32];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 32; j++) {
                array[i][j] = (i + 1) * (j + 1);
            }
        }

        // 5. Bucle para imprimir cada valor en pantalla
        System.out.println("Imprimiendo arreglo de 16x32:");
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + "	");
            }
            System.out.println();
        }
    }
}

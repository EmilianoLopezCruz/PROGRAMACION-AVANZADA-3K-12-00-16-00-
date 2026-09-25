public class Ejercicio4 {
    public static void main(String[] args) {
        // Arreglo de prueba de 16 elementos
        int[] array = new int[16];
        for (int i = 0; i < array.length; i++) {
            array[i] = (i + 1) * 3;
        }

        // 4. Bucle para imprimir cada valor en pantalla
        System.out.println("Valores del arreglo de 16 elementos:");
        for (int i = 0; i < array.length; i++) {
            System.out.println("Elemento [" + i + "] = " + array[i]);
        }
    }
}

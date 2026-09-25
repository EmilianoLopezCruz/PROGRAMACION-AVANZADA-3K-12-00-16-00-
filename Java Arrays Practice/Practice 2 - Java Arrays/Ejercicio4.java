public class Ejercicio4 {
    public static void main(String[] args) {
        // Crear un arreglo de prueba de 30 elementos
        int[] data = new int[30];
        for (int i = 0; i < data.length; i++) {
            data[i] = (i + 1) * 2;
        }

        // 4. Bucle para imprimir cada valor en pantalla
        System.out.println("Valores del arreglo de 30 elementos:");
        for (int i = 0; i < data.length; i++) {
            System.out.println("Índice [" + i + "] = " + data[i]);
        }
    }
}

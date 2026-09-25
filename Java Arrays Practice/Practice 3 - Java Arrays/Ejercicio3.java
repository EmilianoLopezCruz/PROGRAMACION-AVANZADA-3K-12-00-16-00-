import java.util.Arrays;

public class Ejercicio3 {
    public static void main(String[] args) {
        int[] number = new int[30];

        // 3. Bucle para inicializar todos los elementos con el valor de 5
        for (int i = 0; i < number.length; i++) {
            number[i] = 5;
        }

        System.out.println("Arreglo number inicializado con 5:");
        System.out.println(Arrays.toString(number));
    }
}

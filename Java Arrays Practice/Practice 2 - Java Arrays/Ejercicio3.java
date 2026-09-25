import java.util.Arrays;

public class Ejercicio3 {
    public static void main(String[] args) {
        int[] number = new int[20];

        // 3. Bucle para recorrer el arreglo e inicializar cada elemento en 4
        for (int i = 0; i < number.length; i++) {
            number[i] = 4;
        }

        System.out.println("Arreglo number con todos sus elementos en 4:");
        System.out.println(Arrays.toString(number));
    }
}

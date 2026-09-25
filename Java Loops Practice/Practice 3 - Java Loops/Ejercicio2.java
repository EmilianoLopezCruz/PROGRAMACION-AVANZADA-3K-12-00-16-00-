public class Ejercicio2 {
    public static void main(String[] args) {
        // Ejercicio 2: Bucle corregido (se usa count en lugar de i y con llaves de bloque)
        int sum = 0;
        for (int count = 0; count <= 10; count++) {
            System.out.println(count + " ");
            sum += count;
        }
        System.out.println("The sum of 0 to 10 is: " + sum);
    }
}

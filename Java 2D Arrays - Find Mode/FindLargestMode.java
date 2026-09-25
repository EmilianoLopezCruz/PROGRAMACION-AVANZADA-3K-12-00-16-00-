import java.util.Arrays;

public class FindLargestMode {
    // Ejercicio: Encontrar la moda más grande (5)
    // Cambio clave: usar 'count >= maxCount' en vez de 'count > maxCount'
    static int mode(int a[], int n) {
        int maxValue = 0, maxCount = 0;
        for (int i = 0; i < n; ++i) {
            int count = 0;
            for (int j = 0; j < n; ++j) {
                if (a[j] == a[i])
                    ++count;
            }
            // Al usar >=, si otro número empata en frecuencia y está después (orden ascendente),
            // se convierte en la nueva moda, seleccionando así la moda más grande.
            if (count >= maxCount) {
                maxCount = count;
                maxValue = a[i];
            }
        }
        return maxValue;
    }

    public static void main(String args[]) {
        int a[] = {2, 3, 2, 3, 3, 4, 5, 2, 2, 5, 5, 5};
        int n = a.length;
        Arrays.sort(a);
        System.out.println("Largest Mode: " + mode(a, n));
    }
}

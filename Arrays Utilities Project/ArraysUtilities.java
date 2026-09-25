import java.util.Arrays;

/**
 * Clase de utilidades para operaciones comunes sobre arreglos primitivos de tipo int[].
 */
public class ArraysUtilities {

    /**
     * Encuentra y devuelve el valor numérico máximo dentro del arreglo.
     * 
     * Funcionamiento:
     * 1. Valida que el arreglo no sea nulo ni tenga una longitud igual a cero.
     * 2. Asume provisionalmente que el primer elemento (índice 0) es el valor más grande.
     * 3. Itera secuencialmente desde el índice 1 hasta el final del arreglo.
     * 4. En cada iteración, si el elemento actual supera el valor de 'max', se actualiza 'max'.
     * 5. Retorna el valor más grande localizado al concluir el recorrido.
     * 
     * @param array Arreglo de enteros a examinar.
     * @return El número entero más grande encontrado.
     * @throws IllegalArgumentException Si el arreglo es nulo o está vacío.
     */
    public static int findMax(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("El arreglo no puede ser nulo ni estar vacío.");
        }
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    /**
     * Encuentra y devuelve el valor numérico mínimo dentro del arreglo.
     * 
     * Funcionamiento:
     * 1. Verifica que la referencia sea válida y contenga al menos un elemento.
     * 2. Toma el elemento en la posición 0 como referencia base para la comparación.
     * 3. Recorre linealmente los elementos restantes evaluando si alguno es inferior a 'min'.
     * 4. Si se detecta un valor menor, se asigna como el nuevo mínimo.
     * 5. Devuelve el número menor tras examinar todas las posiciones.
     * 
     * @param array Arreglo de enteros a examinar.
     * @return El número entero más pequeño contenido en el arreglo.
     * @throws IllegalArgumentException Si el arreglo es nulo o carece de elementos.
     */
    public static int findMin(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("El arreglo no puede ser nulo ni estar vacío.");
        }
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }

    /**
     * Calcula la suma total acumulada de todos los elementos del arreglo.
     * 
     * Funcionamiento:
     * 1. Declara una variable acumuladora inicializada en 0.
     * 2. Emplea un ciclo for-each para visitar cada valor entero del arreglo.
     * 3. Añade el valor de cada celda a la variable sumatoria.
     * 4. Retorna el acumulado final.
     * 
     * @param array Arreglo de números enteros.
     * @return La suma de todos los enteros (retorna 0 si la referencia es nula).
     */
    public static int calculateSum(int[] array) {
        if (array == null) return 0;
        int sum = 0;
        for (int num : array) {
            sum += num;
        }
        return sum;
    }

    /**
     * Calcula la media aritmética (promedio) de los elementos del arreglo.
     * 
     * Funcionamiento:
     * 1. Valida que el arreglo tenga datos para evitar una división entre cero (ArithmeticException).
     * 2. Invoca el método calculateSum para obtener la suma aritmética de todos los elementos.
     * 3. Realiza un moldeo explícito (cast) a double para prevenir el truncamiento de la división entera.
     * 4. Divide la suma entre la cantidad de elementos (array.length) y devuelve el valor decimal resultante.
     * 
     * @param array Arreglo de enteros.
     * @return El promedio aritmético como número decimal de tipo double.
     * @throws IllegalArgumentException Si el arreglo es nulo o no contiene elementos.
     */
    public static double calculateAverage(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("No se puede calcular el promedio de un arreglo sin elementos.");
        }
        return (double) calculateSum(array) / array.length;
    }

    /**
     * Invierte el orden de los elementos del arreglo original en su propio espacio de memoria (in-place).
     * 
     * Funcionamiento:
     * 1. Emplea un algoritmo de dos punteros: 'left' al inicio (0) y 'right' al final (length - 1).
     * 2. En cada iteración guarda el valor de array[left] en una variable temporal.
     * 3. Copia el valor de array[right] en array[left] y restaura el temporal en array[right].
     * 4. Incrementa el puntero izquierdo y decrementa el derecho hasta que ambos se crucen en el centro.
     * 
     * @param array Arreglo cuyos elementos serán invertidos directamente.
     */
    public static void reverse(int[] array) {
        if (array == null || array.length <= 1) return;

        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            int temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            left++;
            right--;
        }
    }

    /**
     * Realiza una búsqueda secuencial (lineal) de un valor objetivo.
     * 
     * Funcionamiento:
     * 1. Itera desde el índice 0 hasta el final del arreglo.
     * 2. Compara cada posición con el valor buscado ('target').
     * 3. Si coincide, devuelve inmediatamente el índice de la primera coincidencia.
     * 4. Si concluye la iteración sin encontrarlo, devuelve -1 como convención de valor no hallado.
     * 
     * @param array Arreglo de enteros sobre el cual buscar.
     * @param target Valor entero buscado.
     * @return El índice en base cero de la primera aparición, o -1 si no se encuentra.
     */
    public static int linearSearch(int[] array, int target) {
        if (array == null) return -1;

        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }
}
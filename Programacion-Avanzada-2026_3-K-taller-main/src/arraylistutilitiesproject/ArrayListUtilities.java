package arraylistutilitiesproject;

import java.util.ArrayList;

/**
 * Clase utilitaria con operaciones comunes para listas dinámicas (ArrayList).
 */
public class ArrayListUtilities {

    /**
     * Determina el valor numérico más alto dentro del ArrayList.
     * 
     * Funcionamiento:
     * 1. Comprueba que la lista no sea nula ni se encuentre vacía.
     * 2. Asume provisionalmente que el primer elemento (índice 0) es el mayor.
     * 3. Recorre linealmente la colección comparando cada número con el máximo actual.
     * 4. Si un valor supera al máximo registrado, se actualiza la variable de control.
     * 5. Retorna el valor más alto encontrado tras completar la iteración.
     * 
     * @param list ArrayList de números enteros a evaluar.
     * @return El número de mayor valor presente en la lista.
     * @throws IllegalArgumentException si la lista es nula o no contiene elementos.
     */
    public static int findMax(ArrayList<Integer> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("La lista no puede ser nula ni estar vacía.");
        }
        int max = list.get(0);
        for (int num : list) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    /**
     * Determina el valor numérico más bajo dentro del ArrayList.
     * 
     * Funcionamiento:
     * 1. Verifica la validez y el tamaño de la lista de entrada.
     * 2. Inicializa la variable de control con el primer elemento de la lista.
     * 3. Examina cada elemento secuencialmente. Si encuentra un número menor,
     *    actualiza la referencia al nuevo mínimo.
     * 4. Retorna el valor mínimo tras recorrer la totalidad de la lista.
     * 
     * @param list ArrayList de enteros.
     * @return El número entero más pequeño de la lista.
     * @throws IllegalArgumentException si la lista es nula o vacía.
     */
    public static int findMin(ArrayList<Integer> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("La lista no puede ser nula ni estar vacía.");
        }
        int min = list.get(0);
        for (int num : list) {
            if (num < min) {
                min = num;
            }
        }
        return min;
    }

    /**
     * Calcula la suma acumulada de todos los enteros almacenados en el ArrayList.
     * 
     * Funcionamiento:
     * 1. Inicializa un acumulador numérico en cero.
     * 2. Itera a través de todos los nodos de la lista añadiendo el valor de cada
     *    iteración al acumulador.
     * 3. Retorna la sumatoria total obtenida.
     * 
     * @param list ArrayList de enteros.
     * @return La suma de todos los elementos (0 si la lista está vacía).
     */
    public static int calculateSum(ArrayList<Integer> list) {
        if (list == null) return 0;
        int sum = 0;
        for (int num : list) {
            sum += num;
        }
        return sum;
    }

    /**
     * Calcula la media aritmética de los números presentes en el ArrayList.
     * 
     * Funcionamiento:
     * 1. Reutiliza el método calculateSum para obtener la suma total.
     * 2. Realiza un casting explícito a tipo double para evitar la división entera truncada.
     * 3. Divide la suma entre la cantidad total de elementos (size) y devuelve el resultado.
     * 
     * @param list ArrayList de enteros.
     * @return Promedio aritmético como número decimal de doble precisión (double).
     * @throws IllegalArgumentException si la lista es nula o carece de elementos.
     */
    public static double calculateAverage(ArrayList<Integer> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("No se puede calcular el promedio de una lista vacía.");
        }
        return (double) calculateSum(list) / list.size();
    }

    /**
     * Invierte el orden de los elementos directamente sobre la lista original (in-place).
     * 
     * Funcionamiento:
     * 1. Utiliza la técnica de dos punteros: 'left' iniciando en el índice 0 y 'right'
     *    en el último índice (size - 1).
     * 2. En cada paso intercambia temporalmente el elemento de la izquierda con el de la
     *    derecha usando una variable auxiliar.
     * 3. Desplaza 'left' hacia adelante y 'right' hacia atrás hasta encontrarse en el centro.
     * 
     * @param list ArrayList cuyos elementos serán invertidos.
     */
    public static void reverseList(ArrayList<Integer> list) {
        if (list == null || list.size() <= 1) return;

        int left = 0;
        int right = list.size() - 1;
        while (left < right) {
            int temp = list.get(left);
            list.set(left, list.get(right));
            list.set(right, temp);
            left++;
            right--;
        }
    }

    /**
     * Remueve valores repetidos conservando la primera aparición de cada elemento.
     * 
     * Funcionamiento:
     * 1. Crea un ArrayList temporal auxiliar para registrar valores únicos.
     * 2. Itera sobre la lista original; si el elemento actual no existe aún en el
     *    contenedor auxiliar (!uniqueList.contains(num)), lo agrega.
     * 3. Limpia la lista original mediante el método clear().
     * 4. Copia todos los elementos únicos filtrados de vuelta a la lista original con addAll().
     * 
     * @param list ArrayList al que se le depurarán los elementos duplicados.
     */
    public static void removeDuplicates(ArrayList<Integer> list) {
        if (list == null || list.size() <= 1) return;

        ArrayList<Integer> uniqueList = new ArrayList<>();
        for (int num : list) {
            if (!uniqueList.contains(num)) {
                uniqueList.add(num);
            }
        }
        list.clear();
        list.addAll(uniqueList);
    }
}
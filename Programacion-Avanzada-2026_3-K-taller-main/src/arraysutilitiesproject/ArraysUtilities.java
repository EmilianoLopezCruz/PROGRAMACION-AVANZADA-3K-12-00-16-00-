package arraysutilitiesproject;

import java.util.Arrays;

public class ArraysUtilities {

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

    public static int calculateSum(int[] array) {
        if (array == null) return 0;
        int sum = 0;
        for (int num : array) {
            sum += num;
        }
        return sum;
    }

    public static double calculateAverage(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("No se puede calcular el promedio de un arreglo sin elementos.");
        }
        return (double) calculateSum(array) / array.length;
    }

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

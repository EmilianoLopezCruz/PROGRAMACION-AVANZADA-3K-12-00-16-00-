package arraylistutilitiesproject;

import java.util.ArrayList;

public class ArrayListUtilities {

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

    public static int calculateSum(ArrayList<Integer> list) {
        if (list == null) return 0;
        int sum = 0;
        for (int num : list) {
            sum += num;
        }
        return sum;
    }

    public static double calculateAverage(ArrayList<Integer> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("No se puede calcular el promedio de una lista vacía.");
        }
        return (double) calculateSum(list) / list.size();
    }

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

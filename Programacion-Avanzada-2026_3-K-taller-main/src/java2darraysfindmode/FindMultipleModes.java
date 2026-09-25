package java2darraysfindmode;

import java.util.ArrayList;
import java.util.Arrays;

public class FindMultipleModes {

    static ArrayList<Integer> findModes(int a[], int n) {
        ArrayList<Integer> modes = new ArrayList<>();
        int maxCount = 0;

        for (int i = 0; i < n; ++i) {
            int count = 0;
            for (int j = 0; j < n; ++j) {
                if (a[j] == a[i])
                    ++count;
            }
            if (count > maxCount) {
                maxCount = count;
                modes.clear();
                modes.add(a[i]);
            } else if (count == maxCount) {
                if (!modes.contains(a[i])) {
                    modes.add(a[i]);
                }
            }
        }
        return modes;
    }

    public static void main(String args[]) {
        int a[] = {2, 3, 2, 3, 3, 4, 5, 2, 2, 5, 5, 5};
        int n = a.length;
        Arrays.sort(a);

        ArrayList<Integer> modes = findModes(a, n);
        System.out.println("Array ordenado: " + Arrays.toString(a));
        System.out.println("Modes: " + modes);
    }
}


import java.util.Arrays;

public class FindSmallestMode {
    static int mode(int a[], int n) {
        int maxValue = 0, maxCount = 0;
        for (int i = 0; i < n; ++i) {
            int count = 0;
            for (int j = 0; j < n; ++j) {
                if (a[j] == a[i])
                    ++count;
            }
            if (count > maxCount) {
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
        System.out.println("Smallest Mode: " + mode(a, n));
    }
}

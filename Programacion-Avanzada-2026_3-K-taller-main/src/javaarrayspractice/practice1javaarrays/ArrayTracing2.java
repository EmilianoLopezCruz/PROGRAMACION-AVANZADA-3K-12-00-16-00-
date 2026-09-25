package javaarrayspractice.practice1javaarrays;

public class ArrayTracing2 {
    public static void main(String[] args) {
        System.out.println("Array Tracing #2");
        int array[][] = {{2, 3, 1, 2}, {2, 1, 0, 2}, {3, 1, 0, 4}};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++)
                System.out.print(array[i][j] + " ");
            System.out.println();
        }
    }
}


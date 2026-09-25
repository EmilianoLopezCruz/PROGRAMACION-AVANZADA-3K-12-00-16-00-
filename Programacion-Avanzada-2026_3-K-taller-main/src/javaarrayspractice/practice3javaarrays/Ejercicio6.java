package javaarrayspractice.practice3javaarrays;

public class Ejercicio6 {
    public static void main(String[] args) {
        // 6. Rastreo de código
        int array6[][] = {{2, 3, 1}, {2, 2, 1}, {0, 2, 3}, {1, 0, 4}};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++)
                System.out.print(array6[i][j]);
            System.out.println();
        }
    }
}

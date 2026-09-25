package javaarrayspractice.practice4javaarrays;

public class Ejercicio7 {
    public static void main(String[] args) {

        int array7[][] = {{2, 2, 2}, {2, 2, 0}};
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 3; j++)
                if (array7[i][j] == 0 && j == 2)
                    System.out.print(array7[i][j]);
        System.out.println();
    }
}


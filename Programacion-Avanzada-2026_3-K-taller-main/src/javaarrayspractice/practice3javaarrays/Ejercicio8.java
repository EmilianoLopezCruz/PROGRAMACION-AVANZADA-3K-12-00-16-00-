package javaarrayspractice.practice3javaarrays;

public class Ejercicio8 {
    public static void main(String[] args) {

        int array8[][] = {{6, 4, 3}, {4, 3, 2}};
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 3; j++)
                System.out.print(2 * array8[i][j]);
        System.out.println();
    }
}


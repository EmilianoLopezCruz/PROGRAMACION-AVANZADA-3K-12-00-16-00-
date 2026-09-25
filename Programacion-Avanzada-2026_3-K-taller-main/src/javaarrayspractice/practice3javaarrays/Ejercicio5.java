package javaarrayspractice.practice3javaarrays;

public class Ejercicio5 {
    public static void main(String[] args) {

        int num[] = {7, 7, 6, 6, 5, 5, 4, 4, 3, 3, 2, 2, 1};
        for (int i = 0; i < 13; i = i + 4)
            System.out.print(num[i]);
        System.out.println();
    }
}


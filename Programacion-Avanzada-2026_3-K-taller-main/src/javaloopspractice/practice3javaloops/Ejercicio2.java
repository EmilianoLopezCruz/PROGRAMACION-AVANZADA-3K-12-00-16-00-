package javaloopspractice.practice3javaloops;

public class Ejercicio2 {
    public static void main(String[] args) {

        int sum = 0;
        for (int count = 0; count <= 10; count++) {
            System.out.println(count + " ");
            sum += count;
        }
        System.out.println("The sum of 0 to 10 is: " + sum);
    }
}


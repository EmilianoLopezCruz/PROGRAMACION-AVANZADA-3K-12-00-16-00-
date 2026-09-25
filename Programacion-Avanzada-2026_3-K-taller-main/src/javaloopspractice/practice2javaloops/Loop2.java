package javaloopspractice.practice2javaloops;

public class Loop2 {
    public static void main(String[] args) {
        System.out.println("\nLoop 2:"); 
        int a = 25; 
        while (a < 50) { 
            a++; 
            if (a % 3 == 0) 
                continue; 
            System.out.println("Oh my!"); 
        } 
        System.out.println(a);
    }
}

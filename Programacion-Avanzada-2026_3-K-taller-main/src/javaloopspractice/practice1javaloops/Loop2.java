package javaloopspractice.practice1javaloops;

public class Loop2 {
    public static void main(String[] args) {
        System.out.println("Loop 2:"); 
        int x = 15; 
        while (x < 22) { 
            x++; 
            if (x % 2 == 0) 
                continue; 
            System.out.println("Bye!"); 
        } 
        System.out.println(x);
    }
}

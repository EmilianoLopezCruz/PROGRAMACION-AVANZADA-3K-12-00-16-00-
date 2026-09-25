public class Loop1 {
    public static void main(String[] args) {
        System.out.println("Loop 1:"); 
        int c = 4; 
        int d = 5; 
        for (int p = c + d; p > 1; p--) { 
            System.out.println(p); 
            if (p == 5) 
                break; 
            d = d + p; 
        } 
        System.out.println(d);
    }
}

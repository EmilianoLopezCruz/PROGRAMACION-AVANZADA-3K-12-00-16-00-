public class Loop1 {
    public static void main(String[] args) {
        System.out.println("Loop 1:"); 
        int x = 9; 
        int y = 7; 
        for (int z = x + y; z > 1; z--) { 
            System.out.println(z); 
            if (z == 5) 
                break; 
            y = y + z; 
        } 
        System.out.println(y);
    }
}

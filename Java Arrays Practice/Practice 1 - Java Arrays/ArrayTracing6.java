public class ArrayTracing6 {
    public static void main(String[] args) {
        System.out.println("Array Tracing #6");
        int array7[][] = {{3, 3, 3}, {2, 2, 2}}; 
        for (int i = 0; i < 3; i++) 
            array7[1][i] = 4; 
        for (int i = 0; i < 2; i++) { 
            for (int j = 0; j < 3; j++)      
                System.out.print(array7[i][j]);
            System.out.println(); 
        }
    }
}

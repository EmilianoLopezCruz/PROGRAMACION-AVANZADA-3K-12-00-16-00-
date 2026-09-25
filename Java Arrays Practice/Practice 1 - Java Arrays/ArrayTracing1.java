public class ArrayTracing1 {
    public static void main(String[] args) {
        System.out.println("Array Tracing #1"); 
        int array1[] = {7, 7, 6, 6, 5, 5, 4, 4}; 
        for (int i = 0; i < 8; i = i + 2) 
            System.out.print(array1[i]); 
        System.out.println();
    }
}

public class ArrayTracing3 {
    public static void main(String[] args) {
        System.out.println("Array Tracing #3"); 
        int array3[][] = {{2, 3, 1}, {2, 1, 2}}; 
        for (int i = 0; i < 2; i++) 
            for (int j = 0; j < 3; j++) 
                if (array3[i][j] == 2 && j == 2)
                    System.out.print(array3[i][j] + " ");
        System.out.println();
    }
}

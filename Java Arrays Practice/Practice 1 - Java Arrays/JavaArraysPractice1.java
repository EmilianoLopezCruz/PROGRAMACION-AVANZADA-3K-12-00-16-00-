public class JavaArraysPractice1 {
    public static void main(String[] args) {
        // Ejercicio 1
        System.out.println("Array Tracing #1"); 
        int array1[] = {7, 7, 6, 6, 5, 5, 4, 4}; 
        for (int i = 0; i < 8; i = i + 2) 
            System.out.print(array1[i]); 
        System.out.println();
        System.out.println();

        // Ejercicio 2
        System.out.println("Array Tracing #2"); 
        int array[][] = {{2, 3, 1, 2}, {2, 1, 0, 2}, {3, 1, 0, 4}};
        for (int i = 0; i < 3; i++) { 
            for (int j = 0; j < 4; j++) 
                System.out.print(array[i][j] + " "); 
            System.out.println(); 
        }
        System.out.println();

        // Ejercicio 3
        System.out.println("Array Tracing #3"); 
        int array3[][] = {{2, 3, 1}, {2, 1, 2}}; 
        for (int i = 0; i < 2; i++) 
            for (int j = 0; j < 3; j++) 
                if (array3[i][j] == 2 && j == 2)
                    System.out.print(array3[i][j] + " ");
        System.out.println();
        System.out.println();

        // Ejercicio 4
        System.out.println("Array Tracing #4"); 
        int array4[][] = new int[2][3]; 
        for (int i = 0; i < 2; i++) 
            for (int j = 0; j < 3; j++) { 
                array4[i][j] = i + 3 * j; 
                System.out.print(array4[i][j] + " "); 
            } 
        System.out.println();
        System.out.println();

        // Ejercicio 5
        System.out.println("Array Tracing #5");
        int array5[] = {6, 5, 5, 4, 4}; 
        int array6[] = {1, 9, 6, 7, 1}; 
        for (int i = 0; i < 5; i++) 
            System.out.println(array5[i] + array6[i]);
        System.out.println();

        // Ejercicio 6
        System.out.println("Array Tracing #6");
        int array7[][] = {{3, 3, 3}, {2, 2, 2}}; 
        for (int i = 0; i < 3; i++) 
            array7[1][i] = 4; 
        for (int i = 0; i < 2; i++) { 
            for (int j = 0; j < 3; j++)      
                System.out.print(array7[i][j]);
            System.out.println(); 
        }
        System.out.println();

        // Ejercicio 7
        System.out.println("Array Tracing #7");
        int array8[][] = {{3, 3, 3}, {2, 2, 2}}; 
        int array9[] = {4, 3, 2}; 
        for (int i = 0; i < 3; i++) 
            array8[1][i] = array8[0][i] + array9[i]; 
        for (int i = 0; i < 2; i++) { 
            for (int j = 0; j < 3; j++) 
                System.out.print(array8[i][j]); 
            System.out.println(); 
        }
    }
}

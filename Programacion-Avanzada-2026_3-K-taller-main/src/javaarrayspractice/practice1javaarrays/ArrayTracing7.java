package javaarrayspractice.practice1javaarrays;

public class ArrayTracing7 {
    public static void main(String[] args) {
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

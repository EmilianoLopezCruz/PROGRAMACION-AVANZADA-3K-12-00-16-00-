public class ArraysUtilities_Start {
    
    private int[] ary = new int[20];
    private int count = 0;
    
    public void buildList() {
        for (int i = 0; i < ary.length; i++) {
            ary[i] = (int)(Math.random() * 20 + 1);
            count++;
        }
    }
    
    public void removeEvens() {
        for (int i = 0; i < count; i++) {
            if (ary[i] % 2 == 0) {
                removeElement(i);
                i--;
            }
        }
    }
    
    public void removeElement(int index) {
        System.out.println("remove: " + ary[index]);
        for (int i = index; i < count - 1; i++) {
            ary[i] = ary[i + 1];
        }
        count--;
    }
    
    public int getSum() {
        int sum = 0;
        for(int el: ary) {
            sum += el;
        }
        
        return sum;
    }
    
    public double getAverage() {
        
        return (double)getSum() / count;
    }
    
    public void getMedian() {
        int[] evenCountList = {60, 65, 70, 75, 80, 85, 90, 95};
        int[] oddCountList = {60, 65, 70, 75, 80, 85, 90, 95, 100};
        
        int oddIndex = oddCountList.length / 2;
        int oddMedian = oddCountList[oddIndex];
        
        int evenIndex = evenCountList.length / 2;
        int evenMedian = (evenCountList[evenIndex] + evenCountList[evenIndex - 1]) / 2;
        
        System.out.println("EvenCountList Median: " + evenMedian);
        System.out.println("OddCountList Median: " + oddMedian);
    }
    
    public int valuesBetween(int low, int high) {
        int num = 0;
        for (int el: ary) {
            if (el > low && el < high) {
                num++;
            }
        }
        
        return num;
    }
     
    public boolean allNumbersBetween(int low, int high) {
        
        for (int el: ary) {
            if (el <= low || el >= high) {
                return false;
            }
        }
        
        return true;
    }
    
    public void displayConsecutivePairs() {
        
        System.out.println("Display Consecutive Pairs");
        int value = 0;
        for (int i = 0; i < count - 1; i++) {
            if (ary[i] == ary[i+1]) {
                System.out.println("\t" + ary[i] + ", " + ary[i+1]);
                value++;
            }
        }
        if (value == 0) {
            System.out.println("NO CONSECUTIVE PAIRS");
        }
    }
    
    public void removeDuplicates() {
        
        for (int i = 0; i < count - 1; i++) {
            for (int j = i + 1; j < count; j++) {
                if (ary[i] == ary[j]) {
                    removeElement(j);
                    j--;
                }
            }
        }
    }

    public void moveToEnd(int index) {
        
        int valueToBeMoved = ary[index];
        for (int i = index; i < count - 1; i++) {
            ary[i] = ary[i + 1];
        }
        ary[count - 1] = valueToBeMoved;
    }

    public void moveToBeginning(int index) {
        
        int valueToBeMoved = ary[index];
        for (int i = index; i > 0; i--) {
            ary[i] = ary[i-1];
        }
        ary[0] = valueToBeMoved;
    }

    public void reverseList() {
        int[] temp = new int[ary.length];
        int current = 0;
        for (int i = count - 1; i >= 0; i--) {
            temp[current] = ary[i];
            current++;
        }
        ary = temp;
        
    }
    
    public String toString() {
        String str = "";
        for (int i = 0; i < count; i++) {
            str += (ary[i] + "  ");
        }
        return str;
    }
}

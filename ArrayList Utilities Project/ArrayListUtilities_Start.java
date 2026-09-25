
import java.util.ArrayList;

public class ArrayListUtilities_Start{
    
    private ArrayList<Integer> list = new ArrayList<Integer>();
    
    public void buildList(int size) {
        for (int i = 0; i < size; i++) {
            list.add((int)(Math.random() * 20 + 1));
        }
    }
   
    public void removeEvens() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) % 2 == 0) {
                System.out.println("Remove: " + list.get(i));
                list.remove(i);
                i--;
            }
        }
    }
    
    public Integer getSum() {
        int sum = 0;
        for(Integer el: list) {
            sum += el;
        }
        
        return sum;
    }
    
    public double getAverage() {
        
        return (double)getSum() / list.size();
    }
    
    public void getMedian() {
        System.out.println("\ngetMedian method");
        
	ArrayList<Integer> evenCountList = new ArrayList<Integer>();
        System.out.print("\t");
        for (int i = 60; i <= 100; i+= 5) {
            System.out.print(i + " ");
            evenCountList.add(i);
        }
        System.out.println("");

        ArrayList<Integer> oddCountList = new ArrayList<Integer>();
        System.out.print("\t");
        for (int i = 60; i < 100; i+= 5) {
            System.out.print(i + " ");
            oddCountList.add(i);
        }
        System.out.println("");
        
        int evenListIndex = evenCountList.size() / 2;
        int oddListIndex = oddCountList.size() / 2;
        
        int evenMedian = (evenCountList.get(evenListIndex) + evenCountList.get(evenListIndex - 1)) / 2;
        int oddMedian = (oddCountList.get(oddListIndex));
        
        System.out.println("\tEvenCountList Median: " + evenMedian);
        System.out.println("\tOddCountList Median: " + oddMedian);
    }
    
    public int valuesBetween(int low, int high) {
        int num = 0;
        for (Integer el: list) {
            if (el > low && el < high) {
                num++;
            }
        }  
        return num;
    }
    
    public boolean allNumbersBetween(int low, int high) {
        
        for (Integer el: list) {
            if (el <= low || el >= high) {
                return false;
            }
        }  
        return true;
    }
    
    public void displayConsecutivePairs() {
        
        System.out.println("Display Consecutive Pairs");
        int value = 0;
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).equals(list.get(i+1))) {
                System.out.println("\t" + list.get(i) + ", " + list.get(i+1));
                value++;
            }
        }
        if (value == 0) {
            System.out.println("NO CONSECUTIVE PAIRS");
        }
    }
    
    public void removeDuplicates() {
        
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i) == list.get(j)) {
                    System.out.println("\tRemove: " + list.get(j));
                    list.remove(j);
                    j--;
                }
            }
        }
    }
    
    public void removeElement_Bad(Integer value) {
        
        for (Integer obj: list) {
            if (obj.equals(value)) {
                list.remove(obj);
            }
        }
    }
   
    public void moveToEnd(int index) {
        
        Integer valueToBeMoved = list.remove(index);
        list.add(valueToBeMoved);
    }

    public void moveToBegining(int index) {
        
        Integer valueToBeMoved = list.remove(index);
        list.add(0, valueToBeMoved);
    }

    public void reverseList() {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        
        for (int i = list.size() - 1; i >= 0; i--) {
            temp.add(list.remove(i));
        }
        list = temp;
    }
   
    public String toString() {
        String str = "";
        for (Integer num: list) {
            str += (num + "  ");
        }
        return str;
    }
}

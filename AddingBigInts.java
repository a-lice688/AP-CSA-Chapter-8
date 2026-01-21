import java.util.*;

public class AddingBigInts {

    public static void main(String[] args) {
        
        List<Integer> l1 = Arrays.asList(9, 9, 9, 9);   
        List<Integer> l2 = Arrays.asList(1);      

        List<Integer> result = add(l1, l2);

        System.out.println("Sum: " + result);
    }

    public static List<Integer> add(List<Integer> l1, List<Integer> l2) {
        
        List<Integer> result = new ArrayList<>();

        int i = l1.size() - 1;
        int j = l2.size() - 1;
        int carryOver = 0;

        while (i >= 0 || j >= 0 || carryOver > 0) {
            int digit1 = 0;
            int digit2 = 0;

            if (i >= 0) digit1 = l1.get(i);
            
            if (j >= 0) digit2 = l2.get(j);
            
            int sum = digit1 + digit2 + carryOver;
            int digit = sum % 10;
            carryOver = sum / 10;

            result.add(0, digit);

            i--;
            j--;
        }

        return result;
    }
}
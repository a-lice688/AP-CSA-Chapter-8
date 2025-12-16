import java.util.*;

public class Eratosthenes {
    
    public static void main(String[] args) {
        
        Scanner s = new Scanner(System.in);
        System.out.print("Input n: ");
        
        int n = s.nextInt();
        ArrayList<Integer> primes = sieve(n);
        System.out.println(primes);
        
    }

    public static ArrayList<Integer> sieve(int n) {
        
        ArrayList<Integer> list = new ArrayList<>();
        
        if (n <= 2) return list;

        for (int i = 2; i < n; i++) list.add(i);

        int indexOfP = 0;

        while (indexOfP < list.size() && list.get(indexOfP) * list.get(indexOfP) < n) {
            
            int p = list.get(indexOfP);
            
            for (int i = list.size() - 1; i > indexOfP; i--) {
                if (list.get(i) % p == 0)  list.remove(i);
                
            }
            
            indexOfP++;
        }

        return list;
    }
}

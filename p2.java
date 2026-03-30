import java.util.*;

public class p2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input symbols
        System.out.print("Number of input symbols: ");
        int nSymbols = sc.nextInt();
        sc.nextLine();

        System.out.print("Input symbols: ");
        String[] symbols = sc.nextLine().split(" ");

        // Map symbol to index
        Map<Character, Integer> symbolIndex = new HashMap<>();
        for (int i = 0; i < nSymbols; i++) {
            symbolIndex.put(symbols[i].charAt(0), i);
        }

        // States
        System.out.print("Enter number of states: ");
        int nStates = sc.nextInt();

        System.out.print("Initial state: ");
        int startState = sc.nextInt();

        // Accepting states
        System.out.print("Number of accepting states: ");
        int nAccept = sc.nextInt();

        System.out.print("Accepting states: ");
        Set<Integer> acceptStates = new HashSet<>();
        for (int i = 0; i < nAccept; i++) {
            acceptStates.add(sc.nextInt());
        }
        // Transition table
        int[][] transition = new int[nStates + 1][nSymbols];

        System.out.println("Enter transition table (state symbol nextState):");
        for (int i = 0; i < nStates * nSymbols; i++) {
            int state = sc.nextInt();
            char symbol = sc.next().charAt(0);
            int nextState = sc.nextInt();

            int index = symbolIndex.get(symbol);
            transition[state][index] = nextState;
        }
        // Input string
        System.out.print("Input string: ");
        String input = sc.next();

        // Validate string
        int currentState = startState;

        for (char ch : input.toCharArray()) {
            if (!symbolIndex.containsKey(ch)) {
                System.out.println("Invalid String");
                return;
            }
            int index = symbolIndex.get(ch);
            currentState = transition[currentState][index];
        }
        // Final check
        if (acceptStates.contains(currentState)) {
            System.out.println("Valid String");
        } else {
            System.out.println("Invalid String");
        }
        sc.close();
    }
}
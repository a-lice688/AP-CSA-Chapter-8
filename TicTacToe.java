/*
Features:
1) Game loop & replay
   - Runs up to 9 valid moves or until someone wins
   - Skips to the end of the game if input "000" 
   - After a game ends, player can enter "1" to start a new game (board reset) or "999" to quit

2) Turn tracking
   - round counter; Player X on even rounds, Player O on odd rounds (Sorry! the round is 0-based)

3) Input handling 
   - Special commands:
     • "000" → skip current game and go to replay prompt
     • "999" → exit program immediately
     • "r" or "R" → undo the last move (redo feature)
   - Cleans input of any non-number characters

4) Input validation with re-prompting
   - If parsing fails or not exactly two digits → message + re-prompt same player
   - If row/col out of range (not 1–3) → message + re-prompt
   - If cell occupied → message + re-prompt

5) Move application
   - Converts 1–3 input to 0–2 indices (and vice-versa for human-friendliness)
   - Records last move (lastRow/lastCol) for redo

6) Redo
   - If "r"/"R":
     • If no move yet → "There is nothing to redo." + re-prompts
     • Else clears last mark, decrements round (same player tries again)

8) Win detection / Draw detection

9) Reset game
    - Clears board to spaces, resets round to 0, clears last move markers

Controls summary:
- Enter "row, column" to place a mark
- Enter "r" or "R" to undo last move
- Enter "1" to start another game
- Enter "000" to skip the current game
- Enter "999" to quit at any time

Core methods: prompt(), display(), breakInput(), updateBoard(), redoLast(), hasWon(), resetGame()

*/


import java.util.*;

public class TicTacToe {
    static int round = 0;
    static boolean gameOver = false;

    static int lastRow = -1;
    static int lastCol = -1;
    
    static char[][] board = {{' ', ' ', ' '}, 
                            {' ', ' ', ' '},
                            {' ', ' ', ' '}}; 
                                
    public static void main(String[] args) {
        
        Scanner s = new Scanner(System.in);
        
        System.out.println("Welcome to Tic-Tac-Toe!");
        display();
        
        while (true) {
            
            gameOver = false;
            round = 0;
            lastRow = -1;
            lastCol = -1;

            while (!gameOver && round < 9) {
                prompt();
                String input = s.nextLine();
                
                if (input.equals("000")) {
                    gameOver = true;
                    System.out.println("Game skipped!");
                    break;
                }
                
                if (input.equals("999")) {
                    gameOver = true;
                    System.out.println("Game over!");
                    return;
                }
                
                if (input.equalsIgnoreCase("r")) {
                    if (lastRow == -1) {
                        System.out.println("There is nothing to redo. Try again.");
                        continue;
                    }
                    
                    redoLast();
                    display();
                    if (round > 0) round--;
                    continue;
                    
                }
            
                int[] pos = breakInput(input);
                if (pos == null) {
                    System.out.println("Invalid input. Use format like \"row, column\"!");
                    continue;
                }
                
                int r = pos[0] - 1;
                int c = pos[1] - 1;
                
                if (r < 0 || r > 2 || c < 0 || c > 2) {
                    System.out.println("Out of range. Try again.!");
                    continue;
                }
                
                if (board[r][c] != ' ') {
                    System.out.println("That spot is taken. Try again! ");
                    continue;
                }
                
                char player = getXO();
                updateBoard(new int[]{pos[0], pos[1]});
                
                lastRow = r;
                lastCol = c;
                
                display();
                
                if (hasWon(player)) {
                    System.out.println("Player " + player + " wins!\n");
                    gameOver = true;
                    break;
                }
                
                if (round == 8) {
                    System.out.println("It's a draw!\n");
                    gameOver = true;
                    break;
                }
                
                round++;
                
            }
            
            while (true) {
                System.out.print("Do you want to play again? \nEnter 1 to play again, 999 to quit: ");
                String choice = s.nextLine();
                    
                if (choice.equals("999")) return;
                        
                else if (choice.equals("1")) {
                        
                    resetGame();
                    break;
                        
                } else System.out.println("Please enter 1 or 999.");
                
            }
            
        }
    }
    
    public static int getRound() {
        return round;
    }
    
    public static char getXO() {
        if (getRound() % 2 == 0) return 'X';
        else return 'O';
    }
    
    public static void prompt() {        
        System.out.print("\n *** Round " + (round + 1) + " ***\nPlayer " + getXO() + ", please make your move: ");
    }

    
    public static void display() {
        
        for (int i = 0; i < 3; i++) {
            
            for (int j = 0; j < 3; j++) System.out.print(" [" + board[i][j] + "] ");
            
            System.out.println();
        }
    }
    
    public static int[] breakInput(String input) {
        
        if (input == null) return null; 
        
        String cleaned = "";
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) cleaned += c;
        }
        
        if (cleaned.length() != 2) return null;
        
        int[] result = new int[2];
        result[0] = cleaned.charAt(0) - '0';
        result[1] = cleaned.charAt(1) - '0';
        
        return result;
    }
    
    public static void updateBoard(int[] input) {
        int row = input[0] - 1;
        int column = input[1] - 1; 
        
        board[row][column] = getXO();
        
    }
    
    public static void redoLast() {
        
        board[lastRow][lastCol] = ' ';
        
        lastRow = -1;
        lastCol = -1;
    }
    
        
    public static boolean hasWon(char p) {
        
        for (int i = 0; i < 3; i++) {
            
            if (board[i][0] == p && board[i][1] == p && board[i][2] == p) return true;
            
        }
        
        for (int j = 0; j < 3; j++) {
            
            if (board[0][j] == p && board[1][j] == p && board[2][j] == p) return true;
            
        }
        
        if (board[0][0] == p && board[1][1] == p && board[2][2] == p) return true;
        
        if (board[0][2] == p && board[1][1] == p && board[2][0] == p) return true;
        
        return false;
    }
    
    public static void resetGame() {
        
        System.out.print("\f");

        for (int i = 0; i < 3; i++) {
            
            for (int j = 0; j < 3; j++) board[i][j] = ' ';
            
        }
        
        round = 0;
        lastRow = -1;
        lastCol = -1;
        gameOver = false;
        
        System.out.println("New game!\n");
        display();
    }
    
}

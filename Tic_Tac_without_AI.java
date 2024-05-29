import java.util.Scanner; 
 
public class NONAI_tictactoe 
 { 
    private static final int SIZE = 3; 
 
    private static void printBoard(char[][] board) { 
        System.out.println("-------------"); 
        for (int i = 0; i < SIZE; i++) { 
            System.out.print("| "); 
            for (int j = 0; j < SIZE; j++) { 
                System.out.print(board[i][j] + " | "); 
            } 
            System.out.println("\n-------------"); 
        } 
    } 
 
   
    private static int isGameOver(char[][] board, char player) { 
         
        for (int i = 0; i < SIZE; i++) { 
            if ((board[i][0] == player && board[i][1] == player && board[i][2] 
== player) || 
                    (board[0][i] == player && board[1][i] == player && 
board[2][i] == player)) { 
                return 1; // Player wins 
            } 
        } 
 
         
        if ((board[0][0] == player && board[1][1] == player && board[2][2] == 
player) || 
                (board[0][2] == player && board[1][1] == player && board[2][0] 
== player)) { 
            return 1; // Player wins 
        } 
 
        
        for (int i = 0; i < SIZE; i++) { 
            for (int j = 0; j < SIZE; j++) { 
                if (board[i][j] == ' ') 
                    return 0; // The game is not over yet 
            } 
        } 
 
        return -1; // It's a draw 
    } 
 
 
 
     
    private static int isValidMove(char[][] board, int row, int col) { 
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) 
            return 0;  
        if (board[row][col] != ' ') 
            return 0;  
        return 1;  
    } 
 
   
    private static void computerMove(char[][] board, char player) { 
        
        for (int i = 0; i < SIZE; i++) { 
            for (int j = 0; j < SIZE; j++) { 
                if (board[i][j] == ' ') { 
                    board[i][j] = player; 
                    if (isGameOver(board, player)==1) { 
                        return;  
                    } 
                    board[i][j] = ' '; 
                } 
            } 
        } 
 
        
        char opponent = (player == 'X') ? 'O' : 'X'; 
        for (int i = 0; i < SIZE; i++) { 
            for (int j = 0; j < SIZE; j++) { 
                if (board[i][j] == ' ') { 
                    board[i][j] = opponent; 
                    if (isGameOver(board, opponent)==1) { 
                        board[i][j] = player;  
                        return; 
                    } 
                    board[i][j] = ' ';  
                } 
            } 
        } 
 
        // Play in the center if available 
        if (board[1][1] == ' ') { 
            board[1][1] = player; 
            return; 
        } 
 
        // Play in a corner if available 
        int[][] corners = {{0, 0}, {0, 2}, {2, 0}, {2, 2}}; 
        for (int i = 0; i < 4; i++) { 
            int row = corners[i][0]; 
 
 
            int col = corners[i][1]; 
            if (board[row][col] == ' ') { 
                board[row][col] = player; 
                return; 
            } 
        } 
 
        // Play in any available edge 
        int[][] edges = {{0, 1}, {1, 0}, {1, 2}, {2, 1}}; 
        for (int i = 0; i < 4; i++) { 
            int row = edges[i][0]; 
            int col = edges[i][1]; 
            if (board[row][col] == ' ') { 
                board[row][col] = player; 
                return; 
            } 
        } 
    } 
 
    public static void main(String[] args) { 
        char[][] board = { 
                {' ', ' ', ' '}, 
                {' ', ' ', ' '}, 
                {' ', ' ', ' '} 
        }; 
 
        System.out.println("TIC-TAC-TOE"); 
 
        Scanner scanner = new Scanner(System.in); 
 
        char currentPlayer; 
        System.out.print("Who plays first? (Human(h)/Computer(c): "); 
        currentPlayer = scanner.next().charAt(0); 
 
        int row, col, moveCount = 0; 
        int gameOver = 0; 
 
        while (gameOver == 0) { 
            System.out.println(); 
 
            if (currentPlayer == 'h') { 
                System.out.println("Your turn (X):"); 
                printBoard(board); 
 
                 
                System.out.print("Enter row (0, 1, or 2) and column (0, 1, or 2): "); 
                row = scanner.nextInt(); 
                col = scanner.nextInt(); 
 
 
 
                if (isValidMove(board, row, col) == 1) { 
                    board[row][col] = 'X'; 
                    moveCount++; 
                    gameOver = isGameOver(board, 'X'); 
                    currentPlayer = 'c';  
                } else { 
                    System.out.println("Invalid move. Try again."); 
                } 
            } else { 
                System.out.println("Computer's turn (O):"); 
                computerMove(board, 'O');  
                moveCount++; 
                printBoard(board); 
                gameOver = isGameOver(board, 'O'); 
                currentPlayer = 'h';  
            } 
        } 
 
        if (isGameOver(board, 'X')==1) { 
            printBoard(board); 
            System.out.println("Player X wins!"); 
        }  
        else if (isGameOver(board, 'O')==1) { 
            printBoard(board); 
            System.out.println("Player O wins!"); 
        }else { 
            printBoard(board); 
            System.out.println("It's a draw!"); 
        } 
 
        scanner.close(); 
    } 
} 
  
  
  
  
 

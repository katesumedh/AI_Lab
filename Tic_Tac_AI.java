import java.util.Scanner; 
 
public class TICTACTOE_AI { 
    private static final char PLAYER_X = 'X'; 
    private static final char PLAYER_O = 'O'; 
    private static final char EMPTY = ' '; 
 
    public static void main(String[] args) { 
        char[][] board = { 
            {EMPTY, EMPTY, EMPTY}, 
            {EMPTY, EMPTY, EMPTY}, 
            {EMPTY, EMPTY, EMPTY} 
        }; 
 
        char currentPlayer = PLAYER_X; 
        char winner=EMPTY; 
 
        Scanner scanner = new Scanner(System.in); 
    
        do { 
            displayBoard(board); 
 
            if (currentPlayer == PLAYER_X) { 
                int position; 
                System.out.print("Player X, enter your move (position [1-9]): 
"); 
                position = scanner.nextInt(); 
 
                // Convert position to row and column 
                int row = (position - 1) / 3; 
                int col = (position - 1) % 3; 
 
                if (isValidMove(position, board) ) { 
                    board[row][col] = currentPlayer; 
                } else { 
                    System.out.println("Invalid move! Try again."); 
                    continue; 
                } 
            } else { 
                findBestMove(board); 
 
 
            } 
 
            winner = checkWinner(board); 
 
            // Switch player for the next turn 
            currentPlayer = (currentPlayer == PLAYER_X) ? PLAYER_O : PLAYER_X; 
        } while (winner == EMPTY && !isBoardFull(board)); 
 
        displayBoard(board); 
 
        if (winner != EMPTY) { 
            System.out.println("Player " + winner + " wins!"); 
        } else { 
            System.out.println("It's a tie!"); 
        } 
 
        scanner.close(); 
    } 
 
    private static void displayBoard(char[][] board) { 
        System.out.println("-------------"); 
        for (int i = 0; i < 3; i++) { 
            System.out.print("| "); 
            for (int j = 0; j < 3; j++) { 
                System.out.print(board[i][j] + " | "); 
            } 
            System.out.println("\n-------------"); 
        } 
    } 
 
    private static boolean isValidMove(int position, char[][] board) { 
        return position >= 1 && position <= 9 && board[(position - 1) / 
3][(position - 1) % 3] == EMPTY; 
    } 
 
    private static char checkWinner(char[][] board) { 
        for (int i = 0; i < 3; i++) { 
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && 
board[i][0] != EMPTY) { 
                return board[i][0]; 
            } 
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && 
board[0][i] != EMPTY) { 
                return board[0][i]; 
            } 
        } 
 
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && 
board[0][0] != EMPTY) { 
 
 
            return board[0][0]; 
        } 
 
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && 
board[0][2] != EMPTY) { 
            return board[0][2]; 
        } 
 
        return EMPTY; 
    } 
 
    private static boolean isBoardFull(char[][] board) { 
        for (int i = 0; i < 3; i++) { 
            for (int j = 0; j < 3; j++) { 
                if (board[i][j] == EMPTY) { 
                    return false; 
                } 
            } 
        } 
        return true; 
    } 
 
    private static void findBestMove(char[][] board) { 
        int bestVal = -1000; 
        int bestMove = -1; 
 
        for (int i = 0; i < 3; i++) { 
            for (int j = 0; j < 3; j++) { 
                if (board[i][j] == EMPTY) { 
                    int move = i * 3 + j + 1; 
                    board[i][j] = PLAYER_O; 
                    int moveVal = minimax(board, 0, false); 
                    board[i][j] = EMPTY; 
 
                    if (moveVal > bestVal) { 
                        bestMove = move; 
                        bestVal = moveVal; 
                    } 
                } 
            } 
        } 
 
        // Make the best move 
        int row = (bestMove - 1) / 3; 
        int col = (bestMove - 1) % 3; 
        board[row][col] = PLAYER_O; 
    } 
 
 
 
    private static int minimax(char[][] board, int depth, boolean isMaximizer) 
{ 
        int score = evaluate(board); 
 
        if (score != 0) { 
            return score; 
        } 
 
        if (isBoardFull(board)) { 
            return 0; 
        } 
 
        if (isMaximizer) { 
            int maxEval = Integer.MIN_VALUE; 
 
            for (int i = 0; i < 3; i++) { 
                for (int j = 0; j < 3; j++) { 
                    if (board[i][j] == EMPTY) { 
                        board[i][j] = PLAYER_O; 
                        int eval = minimax(board, depth + 1, false); 
                        board[i][j] = EMPTY; 
                        maxEval = Math.max(maxEval, eval); 
                    } 
                } 
            } 
 
            return maxEval; 
        } else { 
            int minEval = Integer.MAX_VALUE; 
 
            for (int i = 0; i < 3; i++) { 
                for (int j = 0; j < 3; j++) { 
                    if (board[i][j] == EMPTY) { 
                        board[i][j] = PLAYER_X; 
                        int eval = minimax(board, depth + 1, true); 
                        board[i][j] = EMPTY; 
                        minEval = Math.min(minEval, eval); 
                    } 
                } 
            } 
 
            return minEval; 
        } 
    } 
 
    private static int evaluate(char[][] board) { 
        char winner = checkWinner(board); 
 
        if (winner == PLAYER_X) { 
return -1; 
} else if (winner == PLAYER_O) { 
return 1; 
} 
return 0; 
} 
}

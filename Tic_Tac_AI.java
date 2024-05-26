import java.util.Scanner;

public class ai {
    private static final char HUMAN_PLAYER = 'X';
    private static final char AI_PLAYER = 'O';

    public static void main(String[] args) {
        char[][] board = {
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };

        Scanner scanner = new Scanner(System.in);

        while (true) {
            printBoard(board);

            // Human move
            makeMove(board, HUMAN_PLAYER);

            // Check for a win or a draw
            if (checkWin(board, HUMAN_PLAYER)) {
                printBoard(board);
                System.out.println("You win!");
                break;
            } else if (isBoardFull(board)) {
                printBoard(board);
                System.out.println("It's a draw!");
                break;
            }

            // AI move
            makeAIMove(board);

            // Check for a win or a draw
            if (checkWin(board, AI_PLAYER)) {
                printBoard(board);
                System.out.println("AI wins!");
                break;
            } else if (isBoardFull(board)) {
                printBoard(board);
                System.out.println("It's a draw!");
                break;
            }
        }

        scanner.close();
    }

    private static void printBoard(char[][] board) {
        System.out.println("  0 1 2");
        for (int i = 0; i < 3; i++) {
            System.out.print(i + "|");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + "|");
            }
            System.out.println("\n  -----");
        }
    }

    private static void makeMove(char[][] board, char player) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter your move (row and column): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ') {
                board[row][col] = player;
                break;
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
    }

    private static void makeAIMove(char[][] board) {
        int[] bestMove = findBestMove(board);
        board[bestMove[0]][bestMove[1]] = AI_PLAYER;
    }

    private static int[] findBestMove(char[][] board) {
        int[] bestMove = {-1, -1};
        int bestScore = Integer.MIN_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = AI_PLAYER;
                    int score = minimax(board, 0, false);
                    board[i][j] = ' '; // Undo the move

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }

        return bestMove;
    }

    private static int minimax(char[][] board, int depth, boolean isMaximizing) {
        if (checkWin(board, HUMAN_PLAYER)) {
            return -1;
        } else if (checkWin(board, AI_PLAYER)) {
            return 1;
        } else if (isBoardFull(board)) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = AI_PLAYER;
                        int score = minimax(board, depth + 1, false);
                        board[i][j] = ' '; // Undo the move
                        bestScore = Math.max(bestScore, score);
                    }
                }
            }

            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = HUMAN_PLAYER;
                        int score = minimax(board, depth + 1, true);
                        board[i][j] = ' '; // Undo the move
                        bestScore = Math.min(bestScore, score);
                    }
                }
            }

            return bestScore;
        }
    }

    private static boolean checkWin(char[][] board, char player) {
        
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true; 
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true; 
            }
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true; 
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true; 
        }

        return false;
    }

    private static boolean isBoardFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}

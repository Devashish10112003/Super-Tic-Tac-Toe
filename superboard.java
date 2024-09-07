import java.util.Scanner;

public class superboard {
    private board[][] superBoard;
    public char[][] winnersOfCell;
    private String winner;
    public int currentSuperCellRow;
    public int currentSuperCellColumn;
    public int currentSubCellRow;
    public int currentSubCellColumn;
    private boolean gameOver = false;
    private char currentPlayer;

    public superboard() 
    {
        superBoard = new board[3][3];
        for (int i = 0; i < 3; i++) 
        {
            for (int j = 0; j < 3; j++) 
            {
                superBoard[i][j] = new board();
            }
        }

        winnersOfCell = new char[3][3];
        for (int i = 0; i < 3; i++) 
        {
            for (int j = 0; j < 3; j++) 
            {
                winnersOfCell[i][j] = '-';
            }
        }
        winner = "-";
        currentPlayer = 'X'; // X starts the game
    }

    public void displaySuperBoard() {
        for (int i = 0; i < 3; i++) { // For each super row
            for (int subRow = 0; subRow < 3; subRow++) { // For each row within the sub-board
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < 3; j++) { // For each super column
                    for (int subCol = 0; subCol < 3; subCol++) { // For each column within the sub-board
                        line.append(superBoard[i][j].board[subRow][subCol]);
                        if (subCol < 2) line.append(' ');
                    }
                    if (j < 2) line.append(" | ");
                }
                System.out.println(line.toString());
            }
            if (i < 2) {
                System.out.println("---------+---------+---------");
            }
        }
    }

    public void firstMove() 
    {
        Scanner sc = new Scanner(System.in);
        displaySuperBoard();
        System.out.println("\n\n\tPlayer " + currentPlayer + ", specify the initial super-cell to play (row and column from 0 to 2): ");
        int x, y;
        int a, b;

        x = sc.nextInt();
        y = sc.nextInt();

        while (x > 2 || x < 0 || y > 2 || y < 0) {
            System.out.println("\n\n\tInvalid input. Please enter numbers from 0 to 2.");
            x = sc.nextInt();
            y = sc.nextInt();
        }

        currentSuperCellRow = x;
        currentSuperCellColumn = y;

        System.out.println("\n\n\tSpecify the initial sub-cell to play (row and column from 0 to 2): ");

        a = sc.nextInt();
        b = sc.nextInt();

        while (a > 2 || a < 0 || b > 2 || b < 0 || superBoard[x][y].board[a][b] != '-') {
            System.out.println("\n\n\tInvalid input. Please enter numbers from 0 to 2 and ensure the cell is not occupied.");
            a = sc.nextInt();
            b = sc.nextInt();
        }

        currentSubCellRow = a;
        currentSubCellColumn = b;

        superBoard[x][y].mark(a, b, currentPlayer, winnersOfCell);
        displaySuperBoard();

        if (winnersOfCell[x][y] != '-') {
            System.out.println("Sub-board already won by Player " + winnersOfCell[x][y] + ".");
        }

        togglePlayer();
        nextMoves(currentSubCellRow, currentSubCellColumn, sc);

        sc.close();
    }

    public void nextMoves(int x, int y, Scanner sc) 
    {
        while (!gameOver) 
        {
            //displaySuperBoard();
            System.out.println("\n\n\tPlayer " + currentPlayer + ", you need to play in super cell (" + x + ", " + y + ").");
            System.out.println("\tSpecify the mini board cell (row and column from 0 to 2): ");
            int a = sc.nextInt();
            int b = sc.nextInt();

            while (a > 2 || a < 0 || b > 2 || b < 0 || superBoard[x][y].board[a][b] != '-') 
            {
                System.out.println("\n\n\tInvalid input. Please enter numbers from 0 to 2 and ensure the cell is not occupied.");
                a = sc.nextInt();
                b = sc.nextInt();
            }

            currentSubCellRow = a;
            currentSubCellColumn = b;

            superBoard[x][y].mark(a, b, currentPlayer, winnersOfCell);
            displaySuperBoard();

            if (winnerCheck()) {
                break;
            }

            // Determine the next super cell based on the current sub cell
            x = currentSubCellRow;
            y = currentSubCellColumn;

            // If the targeted super cell is already won or full, allow the player to choose any super cell
            if (winnersOfCell[x][y] != '-' || superBoard[x][y].isFull()) {
                System.out.println("\n\n\tThe directed super cell (" + x + ", " + y + ") is already won or full.");
                System.out.println("\tChoose any super cell to play in (row and column from 0 to 2): ");
                x = sc.nextInt();
                y = sc.nextInt();

                while (x > 2 || x < 0 || y > 2 || y < 0 || winnersOfCell[x][y] != '-' || superBoard[x][y].isFull()) {
                    System.out.println("\n\n\tInvalid input. Please enter numbers from 0 to 2 and choose a super cell that is not won or full.");
                    x = sc.nextInt();
                    y = sc.nextInt();
                }
            }

            togglePlayer();
        }
    }

    private void togglePlayer() {
        if (currentPlayer == 'X') {
            currentPlayer = 'O';
        } else {
            currentPlayer = 'X';
        }
    }

    private boolean winnerCheck() 
    {
        // Check rows for a winner
        for (int i = 0; i < 3; i++) 
        {
            if (winnersOfCell[i][0] != '-' && winnersOfCell[i][0] == winnersOfCell[i][1] && winnersOfCell[i][1] == winnersOfCell[i][2]) 
            {
                winner = "Player " + winnersOfCell[i][0] + " wins the game!";
                gameOver = true;
                System.out.println(winner);
                return true;
            }
        }

        // Check columns for a winner
        for (int i = 0; i < 3; i++) 
        {
            if (winnersOfCell[0][i] != '-' && winnersOfCell[0][i] == winnersOfCell[1][i] && winnersOfCell[1][i] == winnersOfCell[2][i]) 
            {
                winner = "Player " + winnersOfCell[0][i] + " wins the game!";
                gameOver = true;
                System.out.println(winner);
                return true;
            }
        }

        // Check diagonals for a winner
        if (winnersOfCell[0][0] != '-' && winnersOfCell[0][0] == winnersOfCell[1][1] && winnersOfCell[1][1] == winnersOfCell[2][2]) 
        {
            winner = "Player " + winnersOfCell[0][0] + " wins the game!";
            gameOver = true;
            System.out.println(winner);
            return true;
        }

        if (winnersOfCell[0][2] != '-' && winnersOfCell[0][2] == winnersOfCell[1][1] && winnersOfCell[1][1] == winnersOfCell[2][0]) 
        {
            winner = "Player " + winnersOfCell[0][2] + " wins the game!";
            gameOver = true;
            System.out.println(winner);
            return true;
        }

        // Check for a draw (all super cells are won or full)
        boolean draw = true;
        for (int i = 0; i < 3 && draw; i++) {
            for (int j = 0; j < 3 && draw; j++) {
                if (winnersOfCell[i][j] == '-' && !superBoard[i][j].isFull()) {
                    draw = false;
                }
            }
        }

        if (draw) {
            winner = "The game is a draw!";
            gameOver = true;
            System.out.println(winner);
            return true;
        }

        return false;
    }
}

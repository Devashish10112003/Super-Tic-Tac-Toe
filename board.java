public class board 
{
    public char[][] board;
    private char cellWinner = '-';

    public board() 
    {
        board = new char[3][3];
        for (int i = 0; i < 3; i++) 
        {
            for (int j = 0; j < 3; j++) 
            {
                board[i][j] = '-';
            }
        }
    }

    private char winnerCheck(int a, int b, char[][] winnersOfCell) 
    {
        // Check rows
        for (int i = 0; i < 3; i++) 
        {
            if (board[i][0] != '-' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) 
            {
                cellWinner = board[i][0];
                winnersOfCell[a][b] = cellWinner;
                return cellWinner;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) 
        {
            if (board[0][i] != '-' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) 
            {
                cellWinner = board[0][i];
                winnersOfCell[a][b] = cellWinner;
                return cellWinner;
            }
        }

        // Check diagonals
        if (board[0][0] != '-' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) 
        {
            cellWinner = board[0][0];
            winnersOfCell[a][b] = cellWinner;
            return cellWinner;
        }

        if (board[0][2] != '-' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) 
        {
            cellWinner = board[0][2];
            winnersOfCell[a][b] = cellWinner;
            return cellWinner;
        }

        return cellWinner;
    }

    public void mark(int a, int b, char c, char[][] winnersOfCell) 
    {
        if (board[a][b] == '-') {
            this.board[a][b] = c;
            if (cellWinner == '-') 
            {
                cellWinner = winnerCheck(a, b, winnersOfCell);
            }
        } else {
            System.out.println("Cell already occupied!");
        }
    }

    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }
}

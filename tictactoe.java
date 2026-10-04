import java.util.Random;
import java.util.Scanner;

public class tictactoe
{
    static char[][] board; 

    //constructor 
    tictactoe()
    {
        board = new char[3][3];
        initialiseBoard();
    }

    //initialise the board with space first
    public void initialiseBoard()
    {
        for(int i = 0; i <= 2; i++)
        {
            for(int j = 0; j <= 2; j++)
            {
                board[i][j] = ' ';
            }
        }
    }

    //display the board
    public void displayBoard()
    {
        System.out.println("---------------");

        for(int i = 0; i <= 2; i++)
        {
            System.out.print(" | ");

            for(int j = 0; j <= 2; j++)
            {
                System.out.print(board[i][j] + " | ");
            }

            System.out.println();
            System.out.println("---------------");
        }
    }

    //place mark
    public static void placeMark(int row, int col, char mark)
    {
        if(row >= 0 && row <= 2 && col >= 0 && col <= 2)
        {
            board[row][col] = mark;
        }
        else
        {
            System.out.println("invalid position");
        }
    }

    //check row win
    public boolean checkRowWin()
    {
        for(int i = 0; i <= 2; i++)
        {
            if(board[i][0] != ' ' &&
               board[i][0] == board[i][1] &&
               board[i][1] == board[i][2])
            {
                return true;
            }
        }

        return false;
    }

    //check column win
    public boolean checkColWin()
    {
        for(int j = 0; j <= 2; j++)
        {
            if(board[0][j] != ' ' &&
               board[0][j] == board[1][j] &&
               board[1][j] == board[2][j])
            {
                return true;
            }
        }

        return false;
    }

    //check diagonal win
    public boolean diagonalWin()
    {
        if((board[0][0] != ' ' &&
            board[0][0] == board[1][1] &&
            board[1][1] == board[2][2]) ||

           (board[0][2] != ' ' &&
            board[0][2] == board[1][1] &&
            board[1][1] == board[2][0]))
        {
            return true;
        }

        return false;
    }

    //check draw
    public boolean checkDraw()
    {
        for(int i = 0; i <= 2; i++)
        {
            for(int j = 0; j <= 2; j++)
            {
                if(board[i][j] == ' ')
                {
                    return false;
                }
            }
        }

        return true;
    }
}

abstract class player
{
    String name; 
    char mark;

    //make a move
    public abstract void makeMove();

    //valid move
    public boolean isValidMove(int row, int col)
    {
        if(row >= 0 && row <= 2 &&
           col >= 0 && col <= 2 &&
           tictactoe.board[row][col] == ' ')
        {
            return true;
        }

        return false;
    }
}

class human extends player
{
    human(String name, char mark)
    {
        this.name = name;
        this.mark = mark;
    }
    
    @Override
    public void makeMove()
    {
        Scanner sc = new Scanner(System.in);

        int row;
        int col;

        do
        {
            System.out.println("enter the row (0-2): ");
            row = sc.nextInt();

            System.out.println("enter the column (0-2): ");
            col = sc.nextInt();

            if(!isValidMove(row, col))
            {
                System.out.println("invalid position");
            }

        }while(!isValidMove(row, col));

        tictactoe.placeMark(row, col, mark);
    }
}

class AI extends player
{
    public AI(String name, char mark)
    {
        this.name = name;
        this.mark = mark;
    }

    @Override
    public void makeMove()
    {
        int row;
        int col;

        Random ref = new Random();

        do
        {
            row = ref.nextInt(3);
            col = ref.nextInt(3);

        }while(!isValidMove(row, col));

        tictactoe.placeMark(row, col, mark);
    }
}

class main
{
    public static void main(String[]args)
    {
        tictactoe game = new tictactoe();

        human p1 = new human("john", 'X');
        AI p2 = new AI("chitti", 'O');

        player con = p1;

        game.displayBoard();

        while(true)
        {
            System.out.println(con.name + " turn");

            con.makeMove();

            game.displayBoard();

            if(game.checkRowWin() ||
               game.checkColWin() ||
               game.diagonalWin())
            {
                System.out.println(con.name + " wins");
                break;
            }
            else if(game.checkDraw())
            {
                System.out.println("match is draw");
                break;
            }
            else
            {
                if(con == p1)
                {
                    con = p2;
                }
                else
                {
                    con = p1;
                }
            }
        }
    }
}
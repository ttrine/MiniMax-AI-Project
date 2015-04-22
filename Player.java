import java.util.Scanner;
import java.util.ArrayList;

public class Player {
    char color;
    char oppColor;
    int depthLimit;
    int timeLimit1;
    int timeLimit2;
    char[][] board;
        
    public Player(char color, int dLim, int tLim1, int tLim2) {
        this.color = color;
        this.depthLimit = dLim;
        this.timeLimit1 = tLim1;
        this.timeLimit2 = tLim2;
        
        if (this.color == 'B')
            oppColor = 'W';
        else
            oppColor = 'B';
               
        this.board = new char[8][8];
        this.resetBoard();
    }
    
    public void resetBoard() {
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
                this.board[r][c] = ' ';
                
        this.board[3][3] = 'W';
        this.board[3][4] = 'B';
        this.board[4][3] = 'B';
        this.board[4][4] = 'W';
    }
    
    /* Should use minimax + heuristics to choose a move and return as a string. */
    public String makeMove() {
        //Scanner s = new Scanner(System.in);
        int x = 4;
        int y = 5;
        //x = s.nextInt();
        //y = s.nextInt();
        update(true, x, y);
        
        return x + " " + y;
    }
    
    /* Should return a list of all legal moves */
    public ArrayList<int[][]> getLegal() {
        return null;
    }
    
    /* Checks all 8 directions looking for another piece of the same color as the 
       one just placed. If one is found then flip is called to change the color
       of all the pieces inbetween them. */
    public void update(boolean isMe, int x, int y) {
        int r = y; // - 1;
        int c = x; // - 1;
        char setColor = this.color;
        char oppColor = this.oppColor;
        
        if (!isMe) {
            if (this.color == 'W') {
                setColor = 'B';
                oppColor = 'W';
            }
            else {
                setColor = 'W';
                oppColor = 'B';
            }
        }
        
        this.board[r][c] = setColor;
        
        int row;
        int col;
        
        /* Check down */
        for (row = r + 1; row < 8 && this.board[row][c] == oppColor; row++) {}
        if (row < 8 && this.board[row][c] == setColor) {
            flip("S", setColor, r, c, row, c);
        }
        
        /* Check up */
        for (row = r - 1; row >= 0 && this.board[row][c] == oppColor; row--) {}
        if (row >= 0 && this.board[row][c] == setColor) {
            flip("N", setColor, r, c, row, c);
        }
        
        /* Check right */
        for (col = c + 1; col < 8 && this.board[r][col] == oppColor; col++) {}
        if (col < 8 && this.board[r][col] == setColor) {
            flip("E", setColor, r, c, r, col);
        }
        
        /* Check left */
        for (col = c - 1; col >= 0 && this.board[r][col] == oppColor; col--) {}
        if (col >= 0 && this.board[r][col] == setColor) {
            flip("W", setColor, r, c, r, col);
        }
        
        /* Check down-right */
        for (row = r + 1, col = c + 1; 
             row < 8 && col < 8 && this.board[row][col] == oppColor; 
             row++, col++) {}
        if (row < 8 && col < 8 && this.board[row][col] == setColor) {
            flip("SE", setColor, r, c, row, col);
        }
        
        /* Check down-left */
        for (row = r + 1, col = c - 1; 
             row < 8 && col >= 0 && this.board[row][col] == oppColor; 
             row++, col--) {}
        if (row < 8 && col >= 0 && this.board[row][col] == setColor) {
            flip("SW", setColor, r, c, row, col);
        }
        
        /* Check up-right */
        for (row = r - 1, col = c + 1; 
             row >= 0 && col < 8 && this.board[row][col] == oppColor; 
             row--, col++) {}
        if (row >= 0 && col < 8 && this.board[row][col] == setColor) {
            flip("NE", setColor, r, c, row, col);
        }
        
        /* Check up-right */
        for (row = r - 1, col = c - 1; 
             row >= 0 && col >= 0 && this.board[row][col] == oppColor; 
             row--, col--) {}
        if (row >= 0 && col >= 0 && this.board[row][col] == setColor) {
            flip("NW", setColor, r, c, row, col);
        }
    }
    
    /* Changes all the pieces inbetween the given coordinates to the indicated color. */
    public int flip(String dir, char setColor, int r1, int c1, int r2, int c2) {
        int flipped = 0;
        switch (dir) {
            case "S":
                for (int row = r1 + 1; row < r2; row++) {
                    this.board[row][c1] = setColor;
                    flipped++;
                }
                break;
            case "N":
                for (int row = r2 + 1; row < r1; row++) {
                    this.board[row][c1] = setColor;
                    flipped++;
                }
                break;
            case "E":
                for (int col = c1 + 1; col < c2; col++) {
                    this.board[r1][col] = setColor;
                    flipped++;
                }
                break;
            case "W":
                for (int col = c2 + 1; col < c1; col++) {
                    this.board[r1][col] = setColor;
                    flipped++;
                }
                break;
            case "SE":
                for (int row = r1 + 1, col = c1 + 1; row < r2 && col < c2; row++, col++) {
                    this.board[row][col] = setColor;
                    flipped++;
                }
                break;
            case "SW":
                for (int row = r1 + 1, col = c2 + 1; row < r2 && col < c1; row++, col++) {
                    this.board[row][col] = setColor;
                    flipped++;
                }
                break;
            case "NE":
                for (int row = r2 + 1, col = c1 + 1; row < r1 && col < c2; row++, col++) {
                    this.board[row][col] = setColor;
                    flipped++;
                }
                break;
            case "NW":
                for (int row = r2 + 1, col = c2 + 1; row < r1 && col < c1; row++, col++) {
                    this.board[row][col] = setColor;
                    flipped++;
                }
                break;
        }
        
        return flipped;
    }
    
    /* For debugging only */
    public void printBoard() {
        System.out.println("-----------------");
        
        for (char[] rows : this.board) {
            System.out.print("|");
            
            for (char color : rows) {
                System.out.print(color);
                System.out.print("|");
            }
            
            System.out.println("\n-----------------");
        }
    }
}

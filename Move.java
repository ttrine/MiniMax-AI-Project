public class Move {
	int score;
	char[][] parentBoard;
	char[][] board;
	char color, oppColor;
	int x, y;

	static final private int[][] referenceboard = {
		{1000,-100,150,100,100,150,-100,1000},
		{-100,-200,20,20,20,20,-200,-100},
		{150,20,15,15,15,15,20,150},
		{100,20,15,10,10,15,20,100},
		{100,20,15,10,10,15,20,100},
		{150,20,15,15,15,15,20,150},
		{-100,-200,20,20,20,20,-200,-100},
		{1000,-100,150,100,100,150,-100,1000}};

	public Move(char[][] parentBoard, int x, int y, char color, boolean isMe){
		this.parentBoard = parentBoard;
		this.board = new char[8][8];
		resetBoard();
		this.x=x; this.y=y;
		this.color = color;
		
		if(color == 'W')
			oppColor = 'B';
		else oppColor = 'W';

		if (x >= 0 && y >= 0) //basically, if not root node
			update(isMe, x, y);

		this.score = score();

		//score = score(parentBoard, board);
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

    public int update(boolean isMe, int x, int y) {
        int r = y;
        int c = x;
        int flipped = 0;
        
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
            flipped += flip("S", setColor, r, c, row, c);
        }
        
        /* Check up */
        for (row = r - 1; row >= 0 && this.board[row][c] == oppColor; row--) {}
        if (row >= 0 && this.board[row][c] == setColor) {
            flipped += flip("N", setColor, r, c, row, c);
        }
        
        /* Check right */
        for (col = c + 1; col < 8 && this.board[r][col] == oppColor; col++) {}
        if (col < 8 && this.board[r][col] == setColor) {
            flipped += flip("E", setColor, r, c, r, col);
        }
        
        /* Check left */
        for (col = c - 1; col >= 0 && this.board[r][col] == oppColor; col--) {}
        if (col >= 0 && this.board[r][col] == setColor) {
            flipped += flip("W", setColor, r, c, r, col);
        }
        
        /* Check down-right */
        for (row = r + 1, col = c + 1; 
             row < 8 && col < 8 && this.board[row][col] == oppColor; 
             row++, col++) {}
        if (row < 8 && col < 8 && this.board[row][col] == setColor) {
            flipped += flip("SE", setColor, r, c, row, col);
        }
        
        /* Check down-left */
        for (row = r + 1, col = c - 1; 
             row < 8 && col >= 0 && this.board[row][col] == oppColor; 
             row++, col--) {}
        if (row < 8 && col >= 0 && this.board[row][col] == setColor) {
            flipped += flip("SW", setColor, r, c, row, col);
        }
        
        /* Check up-right */
        for (row = r - 1, col = c + 1; 
             row >= 0 && col < 8 && this.board[row][col] == oppColor; 
             row--, col++) {}
        if (row >= 0 && col < 8 && this.board[row][col] == setColor) {
            flipped += flip("NE", setColor, r, c, row, col);
        }
        
        /* Check up-left */
        for (row = r - 1, col = c - 1; 
             row >= 0 && col >= 0 && this.board[row][col] == oppColor; 
             row--, col--) {}
        if (row >= 0 && col >= 0 && this.board[row][col] == setColor) {
            flipped += flip("NW", setColor, r, c, row, col);
        }

        return flipped;
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
                for (int row = r1 + 1, col = c1 - 1; row < r2 && col > c2; row++, col--) {
                    this.board[row][col] = setColor;
                    flipped++;
                }
                break;
            case "NE":
                for (int row = r1 - 1, col = c1 + 1; row > r2 && col < c2; row--, col++) {
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


	public int score() { //char[][] parentBoard, char[][] nodeBoard){
		int score = 0;
		//this.printBoard();
		//System.out.println(score);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == color) {
					score += Move.referenceboard[i][j];
					//System.out.println(i + " " + j + " " + Move.referenceboard[i][j]);
				}
			}
		}

		//System.out.println(score);
		return score;
	}

	public String toString() {
		return (x >= 0 && y >= 0) ? x + " " + y : "pass";
	}

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
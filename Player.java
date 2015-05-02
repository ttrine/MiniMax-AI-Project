/*
 * Authors: Wolf Honore, Tyler Trine, Anthony Margetic
 * CSC 242 Othello Project Spring 2015
 */

import java.util.ArrayList;

/*
 * class Player
 * Represents the AI player. Stores the color, the opponents color, the board
 * state, and the depth and time limits. Contains methods to choose a new move
 * (alphaBeta) and keep the board updated (update, flip).
 */
public class Player {
    char color;
    char oppColor;
    int depthLimit;
    int timeLimit1;
    int timeLimit2;
    char[][] board;

    public Player(char color, int dLim, int tLim1, int tLim2) {
        this.color = color;
        this.depthLimit = (dLim == 0) ? 8 : dLim; /* Use depth 8 by default */
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

    /*
     * String makeMove()
     * Creates a root Move to start the minimax search. Updates the board if
     * a move was made and returns it to the main class.
     */
    public String makeMove() {
        Move root = new Move(this.board, -1, -1, color, true);
        Move nextMove = alphaBeta(root, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, true);

        if (nextMove.x >= 0 && nextMove.y >= 0) {
            update(true, nextMove.x, nextMove.y);
        }
        return nextMove.toString();
    }

    /*
     * Move alphaBeta(Move, int, int, int, boolean)
     * An implementation of minimax with alpha-beta pruning. Returns the next
     * legal move for Player to make.
     */
    public Move alphaBeta(Move move, int depth, int alp, int bet, boolean isMax) {
        ArrayList<Move> children = getLegal(move.parentBoard, move.color);

        /* Stop the search when the limit is reached or at a leaf node */
        if (depth == depthLimit || children.size() == 0) {
            return move;
        }

        if (isMax) {
            int v = Integer.MIN_VALUE;
            Move maxMove = null;
            for (Move child : children) {
                Move nextMove = alphaBeta(child, depth + 1, alp, bet, false);

                if (nextMove.score > v) {
                    maxMove = child;
                    v = nextMove.score;
                }

                alp = Math.max(alp, v);
                if (bet <= alp) /* Prunes the rest of this subtree */
                    break;
            }
            return maxMove;
        }
        else {
            int v = Integer.MAX_VALUE;
            Move minMove = null;
            for (Move child : children) {
                Move nextMove = alphaBeta(child, depth + 1, alp, bet, true);

                if (nextMove.score < v) {
                    minMove = child;
                    v = nextMove.score;
                }

                bet = Math.min(bet, v);
                if (bet <= alp) /* Prunes the rest of this subtree */
                    break;
            }
            return minMove;
        }
    }

    /*
     * ArrayList<Move> getLegal(char[][], char)
     * Returns a list of legal moves for the given color and board state.
     * Simply checks every empty square on the board and sees if placing a token
     * there would flip any pieces.
     */
    public ArrayList<Move> getLegal(char[][] board, char setColor) {
        ArrayList<Move> moves = new ArrayList<Move>();
        boolean isMe;

        if (setColor == color) isMe = true;
        else isMe = false;

        char oppColor = (setColor == 'B') ? 'W' : 'B';

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (board[r][c] != ' ') /* Ignore non-empty squares */
                    continue;

                int row;
                int col;

                /* Check down */
                for (row = r + 1; row < 8 && board[row][c] == oppColor; row++) {}
                if (row < 8 && board[row][c] == setColor && row != r + 1) {
                    moves.add(new Move(board, c, r, color, isMe));
                    continue;
                }

                /* Check up */
                for (row = r - 1; row >= 0 && board[row][c] == oppColor; row--) {}
                if (row >= 0 && board[row][c] == setColor && row != r - 1) {
                    moves.add(new Move(board, c, r, color, isMe));
                    continue;
                }

                /* Check right */
                for (col = c + 1; col < 8 && board[r][col] == oppColor; col++) {}
                if (col < 8 && board[r][col] == setColor && col != c + 1) {
                    moves.add(new Move(board, c, r, color, isMe));
                    continue;
                }

                /* Check left */
                for (col = c - 1; col >= 0 && board[r][col] == oppColor; col--) {}
                if (col >= 0 && board[r][col] == setColor && col != c - 1) {
                    moves.add(new Move(board, c, r, color, isMe));
                    continue;
               }

                /* Check down-right */
                for (row = r + 1, col = c + 1;
                     row < 8 && col < 8 && board[row][col] == oppColor;
                     row++, col++) {}
                if (row < 8 && col < 8 && board[row][col] == setColor
                        && !(row == r + 1 && col == c + 1)) {
                    moves.add(new Move(board, c, r, color, isMe));
                    continue;
                }

                /* Check down-left */
                for (row = r + 1, col = c - 1;
                     row < 8 && col >= 0 && board[row][col] == oppColor;
                     row++, col--) {}
                if (row < 8 && col >= 0 && board[row][col] == setColor
                        && !(row == r + 1 && col == c - 1)) {
                    moves.add(new Move(board, c, r, color, isMe));
                    continue;
                }

                /* Check up-right */
                for (row = r - 1, col = c + 1;
                     row >= 0 && col < 8 && board[row][col] == oppColor;
                     row--, col++) {}
                if (row >= 0 && col < 8 && board[row][col] == setColor
                        && !(row == r - 1 && col == c + 1)) {
                    moves.add(new Move(board, c, r, color, isMe));
                    continue;
                }

                /* Check up-left */
                for (row = r - 1, col = c - 1;
                     row >= 0 && col >= 0 && board[row][col] == oppColor;
                     row--, col--) {}
                if (row >= 0 && col >= 0 && board[row][col] == setColor
                        && !(row == r - 1 && col == c - 1)) {
                    moves.add(new Move(board, c, r, color, isMe));
                    continue;
                }
            }
        }

        return moves;
    }

    /*
     * void update(boolean, int, int)
     * Checks all 8 directions looking for another piece of the same color as the
     * one just placed. If one is found then flip is called to change the color
     * of all the pieces between them.
     */
    public void update(boolean isMe, int x, int y) {
        int r = y;
        int c = x;

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

        /* Check up-left */
        for (row = r - 1, col = c - 1;
             row >= 0 && col >= 0 && this.board[row][col] == oppColor;
             row--, col--) {}
        if (row >= 0 && col >= 0 && this.board[row][col] == setColor) {
            flip("NW", setColor, r, c, row, col);
        }
    }

    /*
     * int flip(String, char, int, int, int, int)
     * Flips all the pieces between the given coordinates to the indicated
     * color. Returns the total number flipped.
     */
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

    /*
     * void printBoard
     * For debugging only. Prints the board state.
     */
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

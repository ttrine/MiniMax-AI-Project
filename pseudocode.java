

public class Move {

	int score;

	int[][] prevBoard;

	String move; 

}


public Move alphaBeta(Move move, int depth, int alp, int bet, boolean isMax) {

	if (depth == MAXDEPTH || noChildren(move)) {

		setScore(move);

		return move;

	}

	

	if (isMax) {

		int v = -inf;

		for (Move child : getChildren(move)) {

			v = max(v, alphaBeta(child, depth + 1, alp, bet, false);

			alp = max(alp, v);

			if (bet <= alp)

				break;

		}

		return v;

	}

	else {

		int v = inf;

		for (Move child : getChildren(board)) {

			v = min(v, alphaBeta(child, depth + 1, alp, bet, true);

			bet = min(bet, v);

			if (bet <= alp)

				break;

		}

		return v;

	}

}

Move root = new Move(0, this.board, “”);

Move nextMove = alphaBeta(root, 0, -inf, inf, true);
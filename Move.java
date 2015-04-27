public class Move {
	int score;
	char[][] parentBoard;
	char[][] nodeBoard;
	int x, y;

	public Move(char[][] parentBoard, int x, int y){
		this.parentBoard = parentBoard;
		this.x=x; this.y=y;
		if (x >= 0 && y >= 0) //basically, if not root node
			nodeBoard = update();
		score = score(parentBoard, nodeBoard);
	}

	public char[][] update() {
		//update board using parentboard and x, y coords
		return null;
	}

	public int score(char[][] parentBoard, char[][] nodeBoard){
		//calculate score
		return 0;
	}
}
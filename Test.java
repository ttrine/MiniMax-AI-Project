import java.util.*;

public class Test {
	public static void main(String[] args) {
		Player p = new Player('B', 4, 0, 0);
		Scanner in = new Scanner(System.in);

		p.printBoard();
		ArrayList<Move> cs = p.getLegal(p.board, 'B');
		//Move m = new Move(p.board, 3, 2, 'B', true);
		//System.out.println("here " + m + " " + m.score);
		for (Move c : cs) {
			System.out.println(c.x + " " + c.y + " " + c.score);
		}
		String m = p.makeMove();

		p.printBoard();
		cs = p.getLegal(p.board, 'W');
		//Move m = new Move(p.board, 3, 2, 'B', true);
		//System.out.println("here " + m + " " + m.score);
		for (Move c : cs) {
			System.out.println(c.x + " " + c.y + " " + c.score);
		}
		m = p.makeMove();
		//System.out.println(m);

		/*char[][] board =
			{
				{'W', 'W', 'W', 'W', 'W', 'W', 'W', 'B'},
			 	{'W', 'W', 'W', 'W', 'W', 'W', 'B', 'B'},
			 	{'W', 'W', 'W', 'W', 'W', 'B', 'W', 'B'},
			 	{'W', 'W', 'W', 'W', 'B', 'W', 'W', 'B'},
			 	{'W', 'W', 'W', 'W', 'B', 'W', 'B', 'B'},
			 	{'W', 'W', 'W', 'B', 'W', 'B', 'B', 'B'},
			 	{'W', 'W', 'W', 'W', 'B', 'W', ' ', 'B'},
			 	{'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'}
		    };

		p.board = board;    
		p.printBoard();

		ArrayList<Move> cs = p.getLegal(p.board, 'W');
		for (Move c : cs) {
			System.out.println(c.x + " " + c.y);
		}

		String m = p.makeMove();
		System.out.println(m);
		//p.update(true, cs.get(0).x, cs.get(0).y);
		*/

		/*char player = 'B';
		while (true) {
			p.printBoard();

			String line = in.nextLine();
			int x;
			int y;

			if (!line.equals("pass")) {
				x = Integer.parseInt(line.split(" ")[0]);
				y = Integer.parseInt(line.split(" ")[1]);
				p.update(player == 'B', x, y);
			}

			player = (player == 'W') ? 'B' : 'W';
		}*/
	}
}
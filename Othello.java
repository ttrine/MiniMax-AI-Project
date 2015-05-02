/*
 * Authors: Wolf Honore, Tyler Trine, Anthony Margetic
 * CSC 242 Othello Project Spring 2015
 */

import java.util.Scanner;

/*
 * class Othello
 * Gets the initial conditions from the interface then enters the game loop
 * which consists of alternating between updating the board from the opponents
 * turn and making a move.
 */
public class Othello {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        Player player;
        String move;
        char playerString;
        int depthLimit;
        int timeLimit1;
        int timeLimit2;

        scan.next();
        playerString = scan.next().charAt(0);
        depthLimit = scan.nextInt();
        timeLimit1 = scan.nextInt();
        timeLimit2 = scan.nextInt();

        player = new Player(playerString, depthLimit, timeLimit1, timeLimit2);

        if (player.color == 'B') { /* Black moves first */
            move = player.makeMove();
            System.out.println(move);
        }

        scan.nextLine();

        while (scan.hasNextLine()) {
            move = scan.nextLine();

            if (!move.equals("pass")) {
                String[] xy = move.split(" ");
                player.update(false, Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
            }

            move = player.makeMove();
            System.out.println(move);
        }
    }
}

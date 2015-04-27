import java.util.Scanner;

public class Othello {
    public static boolean DEBUG = false;

    /* Gets the initial conditions from the interface then enters the game loop
       which consists of alternating between updating the board from the opponents
       turn and making a move. */
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("-d")) DEBUG = true;
        
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
        
        if (DEBUG)
            player.printBoard();
        
        if (player.color == 'B') {
            move = player.makeMove();
            
            if (DEBUG)
                player.printBoard();
                
            System.out.println(move);
        }
        
        scan.nextLine();
        
        while (scan.hasNextLine()) {
            move = scan.nextLine();

            if (!move.equals("pass")) {
                String[] xy = move.split(" ");
                player.update(false, Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
                
                if (DEBUG)
                    player.printBoard();
            }
            
            move = player.makeMove();
            
            if (DEBUG)
                player.printBoard();
            
            System.out.println(move);
        }
    }
}
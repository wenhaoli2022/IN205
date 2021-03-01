import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Game {

    /*
     * *** Constante
     */
    public static final File SAVE_FILE = new File("savegame.dat");

    /*
     * *** Attributs
     */
    private Player player1;
    private AIPlayer player2;
    private Scanner sin;

    /*
     * *** Constructeurs
     */
    public Game() {
    }

    public Game init() {
        if (!loadSave()) {
            // init attributes
            System.out.println("entre ton nom:");

            // TODO use a scanner to read player name
            sin = new Scanner(System.in);
            String name = sin.nextLine();
            
            // TODO init boards
            Board b1, b2;
            b1 = new Board("test1",10);
    		b2 = new Board("test2",10);
            
            // TODO init this.player1 & this.player2
    		Destroyer destroyer = new Destroyer();
    		Submarine submarine1 = new Submarine();
    		Submarine submarine2 = new Submarine();
    		Battleship battleship = new Battleship();
    		Carrier carrier = new Carrier();
    		List<AbstractShip> list = new ArrayList<>();
    		list.add(destroyer);
    		list.add(submarine1);
    		list.add(submarine2);
    		list.add(battleship);
    		list.add(carrier);
    		this.player1 = new Player(b1, b2, list);
    		this.player2 = new AIPlayer(b2,b1,list);

            b1.print();
            // place player ships
            player1.putShips();
            player2.putShips(list);
        }
        return this;
    }

    /*
     * *** Méthodes
     */
    public void run() {
        int[] coords = new int[2];
        Board b1 = player1.board;
        Hit hit;

        // main loop
        b1.print();
        boolean done;
        do {
    		int[] coords2 = new int[2];
        	hit = player1.sendHit(coords2); // TODO player1 send a hit
        	Boolean bool = null;
        	if(hit != Hit.MISS) bool = true;
        	else if(hit==Hit.MISS) bool = false;
        	player1.setHit(bool, coords2[0], coords2[1]);
            boolean strike = hit != Hit.MISS; // TODO set this hit on his board (b1)

            done = updateScore();
            b1.print();
            System.out.println(makeHitMessage(false /* outgoing hit */, coords2, hit));

            save();

            if (!done && !strike) {
                do {
                	Random rnd = new Random();
                	coords[0]=rnd.nextInt(b1.getSize())+1;
        			coords[1]=rnd.nextInt(b1.getSize())+1;
                	hit = player2.sendHit(coords);// TODO player2 send a hit.
                	
                    strike = hit != Hit.MISS;
                    if (strike) {
                        b1.print();
                    }
                    System.out.println(makeHitMessage(true /* incoming hit */, coords, hit));
                    done = updateScore();

                    if (!done) {
                        save();
                    }
                } while (strike && !done);
            }

        } while (!done);

        SAVE_FILE.delete();
        System.out.println(String.format("joueur %d gagne", player1.lose ? 2 : 1));
        sin.close();
    }

    private void save() {
        try {
            // TODO bonus 2 : uncomment
            // if (!SAVE_FILE.exists()) {
            // SAVE_FILE.getAbsoluteFile().getParentFile().mkdirs();
            // }

            // TODO bonus 2 : serialize players

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean loadSave() {
        if (SAVE_FILE.exists()) {
            try {
                // TODO bonus 2 : deserialize players

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean updateScore() {
        for (Player player : new Player[] { player1, player2 }) {
            int destroyed = 0;
            for (AbstractShip ship : player.getShips()) {
                if (ship.isSunk()) {
                    destroyed++;
                }
            }

            player.destroyedCount = destroyed;
            player.lose = destroyed == player.getShips().length;
            if (player.lose) {
                return true;
            }
        }
        return false;
    }

    private String makeHitMessage(boolean incoming, int[] coords, Hit hit) {
        String msg;
        ColorUtil.Color color = ColorUtil.Color.RESET;
        switch (hit) {
        case MISS:
            msg = hit.toString();
            break;
        case STIKE:
            msg = hit.toString();
            color = ColorUtil.Color.RED;
            break;
        default:
            msg = hit.toString() + " coulé";
            color = ColorUtil.Color.RED;
        }
        msg = String.format("%s Frappe en %c%d : %s", incoming ? "<=" : "=>", ((char) ('A' + coords[0]-1)),
                (coords[1]), msg);
        return ColorUtil.colorize(msg, color);
    }

    private static List<AbstractShip> createDefaultShips() {
        return Arrays.asList(new AbstractShip[] { new Destroyer(), new Submarine(), new Submarine(), new Battleship(),
                new Carrier() });
    }
}

import java.io.Serializable;
import java.util.*;
import java.util.Random;

public class BattleShipsAI implements Serializable {

    /*
     * ** Attributs
     */

    /**
     * grid size.
     */
    private final int size;

    /**
     * My board. My ships have to be put on this one.
     */
    private final IBoard board;

    /**
     * My opponent's board. My hits go on this one to strike his ships.
     */
    private final IBoard opponent;

    /**
     * Coords of last known strike. Would be a good idea to target next hits around
     * this point.
     */
    private int lastStrike[];

    /**
     * If last known strike lead me to think the underlying ship has vertical
     * placement.
     */
    private Boolean lastVertical;

    /*
     * ** Constructeur
     */

    /**
     *
     * @param myBoard       board where ships will be put.
     * @param opponentBoard Opponent's board, where hits will be sent.
     */
    public BattleShipsAI(IBoard myBoard, IBoard opponentBoard) {
        this.board = myBoard;
        this.opponent = opponentBoard;
        size = board.getSize();
    }
    
    public int getSize() {
    	return size;
    }

    /*
     * ** Methodes publiques
     */

    /**
     * Put the ships on owned board.
     * 
     * @param ships the ships to put
     */
    public void putShips(List<AbstractShip> ships) {
        int x, y;
        Random rnd = new Random();

        for (AbstractShip s : ships) {
            do {
                // TODO use Random to pick a random x, y & orientation
            	x = rnd.nextInt(size)+1; 
            	y = rnd.nextInt(size)+1;
            	//System.out.println(x);
            	//System.out.println(y);
            	int o = rnd.nextInt(3);
            	if(o==0) s.setOrientation('n');
            	else if(o==1) s.setOrientation('s');
            	else if(o==2) s.setOrientation('w');
            	else if(o==3) s.setOrientation('e');
            } while (!canPutShip(s, x, y));
            //System.out.println(x);
            //System.out.println(y);
            board.putShip(s, x, y);
        }
    }

    /**
     *
     * @param coords array must be of size 2. Will hold the coord of the send hit.
     * @return the status of the hit.
     */
    public Hit sendHit(int x, int y) {
    	if(!board.hasShip(x, y)) {
    		
    		board.setHit(false, x, y);
    		return Hit.fromInt(-1);
    	}
    	
    	else if(board.hasShip(x, y) && board.isSunk(x,y)==false) {
    		board.setHit(true, x, y);
    		board.addStrike(x,y);
    		return Hit.fromInt(-2);
    	} 
    	else if(board.hasShip(x, y) && board.isSunk(x,y)==true) {
    		board.setHit(true, x, y);
    		return Hit.fromInt(board.getTaille(x,y));
    	} 
    	return Hit.fromInt(-2);
    }
    /*
    public Hit sendHit(int[] coords) {
        int res[] = null;
        if (coords == null || coords.length < 2) {
            throw new IllegalArgumentException("must provide an initialized array of size 2");
        }

        // already found strike & orientation?
        if (lastVertical != null) {
            if (lastVertical) {
                res = pickVCoord();
            } else {
                res = pickHCoord();
            }

            if (res == null) {
                // no suitable coord found... forget last strike.
                lastStrike = null;
                lastVertical = null;
            }
        } else if (lastStrike != null) {
            // if already found a strike, without orientation
            // try to guess orientation
            res = pickVCoord();
            if (res == null) {
                res = pickHCoord();
            }
            if (res == null) {
                // no suitable coord found... forget last strike.
                lastStrike = null;
            }
        }

        if (lastStrike == null) {
            res = pickRandomCoord();
        }

        Hit hit = opponent.sendHit(res[0], res[1]);
        board.setHit(hit != Hit.MISS, res[0], res[1]);

        if (hit != Hit.MISS) {
            if (lastStrike != null) {
                lastVertical = guessOrientation(lastStrike, res);
            }
            lastStrike = res;
        }

        coords[0] = res[0];
        coords[1] = res[1];
        return hit;
    }*/

    /*
     * *** Méthodes privées
     */

    private boolean canPutShip(AbstractShip ship, int x, int y) {
        char o = ship.getOrientation();
        int dx = 0, dy = 0;
        if (o == 'e') {
            if (this.size+1-x < ship.getTailleNavire()) {
                return false;
            }
            dx = 1;
        } else if (o == 's') {
            if (this.size+1-y < ship.getTailleNavire()) {
                return false;
            }
            dy = 1;
        } else if (o == 'n') {
            if (y<ship.getTailleNavire()) {
                return false;
            }
            dy = -1;
        } else if (o == 'w') {
            if (x<ship.getTailleNavire()) {
                return false;
            }
            dx = -1;
        }

        int ix = x;
        int iy = y;

        for (int i = 0; i < ship.getTailleNavire(); ++i) {
            if (board.hasShip(ix, iy)) {
                return false;
            }
            ix += dx;
            iy += dy;
        }

        return true;
    }

    private boolean guessOrientation(int[] c1, int[] c2) {
        return c1[0] == c2[0];
    }

    private boolean isUndiscovered(int x, int y) {
        return x >= 1 && x <= size && y >= 1 && y <= size && board.getHit(x, y) == null;
    }

    private int[] pickRandomCoord() {
        Random rnd = new Random();
        int x;
        int y;

        do {
            x = rnd.nextInt(size)+1;
            y = rnd.nextInt(size)+1;
        } while (!isUndiscovered(x, y));

        return new int[] { x, y };
    }

    /**
     * pick a coord verically around last known strike
     * 
     * @return suitable coord, or null if none is suitable
     */
    private int[] pickVCoord() {
        int x = lastStrike[0];
        int y = lastStrike[1];

        for (int iy : new int[] { y - 1, y + 1 }) {
            if (isUndiscovered(x, iy)) {
                return new int[] { x, iy };
            }
        }
        return null;
    }

    /**
     * pick a coord horizontally around last known strike
     * 
     * @return suitable coord, or null if none is suitable
     */
    private int[] pickHCoord() {
        int x = lastStrike[0];
        int y = lastStrike[1];

        for (int ix : new int[] { x - 1, x + 1 }) {
            if (isUndiscovered(ix, y)) {
                return new int[] { ix, y };
            }
        }
        return null;
    }
}

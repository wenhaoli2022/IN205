import java.io.Serializable;
import java.util.List;

public class Player {
    /* **
     * Attributs
     */
    protected Board board;
    protected Board opponentBoard;
    protected int destroyedCount;
    protected AbstractShip[] ships;
    protected boolean lose;

    /* **
     * Constructeur
     */
    public Player(Board board, Board opponentBoard, List<AbstractShip> ships) {
        this.board = board;
        this.ships = ships.toArray(new AbstractShip[0]);
        this.opponentBoard = opponentBoard;
    }

    /* **
     * Méthodes
     */

    /**
     * Read keyboard input to get ships coordinates. Place ships on given coodrinates.
     */
    public void putShips() {
        boolean done = false;
        int i = 0; 

        do {
            AbstractShip s = ships[i];
            String msg = String.format("placer %d : %s(%d)", i + 1, s.getNomNavire(), s.getTailleNavire());
            System.out.println(msg);
            InputHelper.ShipInput res = InputHelper.readShipInput();
            // TODO set ship orientation
			s.setOrientation(res.orientation.charAt(0));
			
            // TODO put ship at given position
            // TODO when ship placement successful
			if(board.putShip(s,res.x,res.y)){
				++i;
				board.print();
			}
            done = i == 5;   
        } while (!done);
    }

    public Hit sendHit(int[] coords) {
        boolean done = false;
        Hit hit = null;

        do {
            System.out.println("ou frapper?");
            InputHelper.CoordInput hitInput = InputHelper.readCoordInput();
            // TODO call sendHit on this.opponentBoard
            try {
            	hit = this.opponentBoard.sendHit(hitInput.x, hitInput.y);
            } catch(Exception e) {
            	//nop
            }
            // TODO : Game expects sendHit to return BOTH hit result & hit coords.
            // return hit is obvious. But how to return coords at the same time ?
            System.out.println("coord: " + hitInput.x + ", " + hitInput.y + " " + hit);
            coords[0] = hitInput.x;
            coords[1] = hitInput.y;
            done = true;
        } while (!done);

        return hit;
    }

    public AbstractShip[] getShips() {
        return ships;
    }

    public void setShips(AbstractShip[] ships) {
        this.ships = ships;
    }
    
    public void printOpponentBoard() {
        this.opponentBoard.print();
    }
    
    public void setHit(Boolean hit, int x, int y){
		board.setHit(hit,x,y);
	}
}

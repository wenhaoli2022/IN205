public class Battleship extends AbstractShip {
	public Battleship(char orientation) {
		super("Battleship", 'B', 4, orientation);
	}
	
	public Battleship() {
		super("Battleship", 'B', 4, 'E');
	}
}
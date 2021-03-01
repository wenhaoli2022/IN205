public class Submarine extends AbstractShip {
	public Submarine(char orientation) {
		super("Submarine", 'S', 3, orientation);
	}
	
	public Submarine() {
		super("Submarine", 'S', 3, 'E');
	}
}
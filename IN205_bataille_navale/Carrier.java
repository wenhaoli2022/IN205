public class Carrier extends AbstractShip {
	public Carrier(char orientation) {
		super("Carrier", 'C', 5, orientation);
	}
	
	public Carrier() {
		super("Carrier", 'C', 5, 'E');
	}
}
public class Destroyer extends AbstractShip {
	public Destroyer(char orientation) {
		super("Destroyer", 'D', 2, orientation);
	}
	
	public Destroyer() {
		super("Destroyer", 'D', 2, 'E');
	}
}
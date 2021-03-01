public class ShipState {
	/* Attributs */
	protected AbstractShip ship;
	protected boolean struck;
	
	/* Constructors */
	public ShipState() {
		this.ship = null;
		this.struck = false;
	}
	
	public ShipState(AbstractShip ship) {
		this.ship = ship;
		this.struck = false;
	}
	
	/* Methodes */
	void addStrike() {
		this.struck = true;
		ship.addStrike();
	}
	
	boolean isStruck() {
		return struck;
	}
	
	char getOrientation() {
		if(this.struck==true) return ColorUtil.colorize(ship.getLabel(), ColorUtil.Color.RED).charAt(0);
		else return ship.getLabel();
	}
	
	boolean isSunk() {
		if(ship.getStrikeCount()>=ship.getTailleNavire()) return true;
		else return false;
	}
	
	AbstractShip getShip() {
		if(ship==null) return null;
		return ship;
	}
	
	void setShip(AbstractShip ship) {
		this.ship = ship;
	}
}
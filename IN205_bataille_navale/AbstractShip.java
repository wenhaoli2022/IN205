public abstract class AbstractShip {
	
	/* Attributs */
	protected char label; /*d pour destroyer, s pour submarine, b pour battleship, c pour carrier*/
	protected String nom_navire;
	protected int taille_navire;
	protected char orientation; /*n pour North, s pour South, w pour West, e pour East*/
	protected int strikeCount;
	
	/* Constructeur */
	public AbstractShip(String nom, char label, int taille, char orientation) {
		this.nom_navire = nom;
		this.label = label;
		this.taille_navire = taille;
		this.orientation = orientation;
		this.strikeCount = 1;
	}

	
	/* Accesseurs */
	public char getLabel() {
		return label;
	}
	
	public String getNomNavire() {
		return nom_navire;
	}

	public int getTailleNavire() {
		return taille_navire;
	}

	public char getOrientation() {
		return orientation;
	}
	
	public int getStrikeCount() {
		return strikeCount;
	}
	
	/* Mutateurs */
	public void setOrientation(char orientation) {
		this.orientation = orientation;
	}
	
	/* Methodes */
	public void addStrike() {
		this.strikeCount++;
	}
	
	public boolean isSunk() {
		if(strikeCount>=taille_navire) return true;
		else return false;
	}
}
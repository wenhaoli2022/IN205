public class Board implements IBoard {
	
	/* Attributs */
    private String nom;
	private int taille;
    private ShipState[][] tableau_navires;
	private Boolean[][] tableau_frappes;

    /* Constructeur */
    public Board(String nom, int taille) {
        this.nom = nom;
        this.taille = taille;
		this.tableau_navires = new ShipState[taille][taille];
		this.tableau_frappes = new Boolean[taille][taille];
		for(int i=0;i<this.taille;i++) {
			for(int j=0;j<this.taille;j++) {
				this.tableau_navires[i][j] = new ShipState();
			} 
		}
    }
	public Board(String nom) {
        this.nom = nom;
        this.taille = 10;
		this.tableau_navires = new ShipState[10][10];
		this.tableau_frappes = new Boolean[10][10];
		for(int i=0;i<this.taille;i++) {
			for(int j=0;j<this.taille;j++) {
				this.tableau_navires[i][j] = new ShipState();
			} 
		}
    }
	
	/* Accesseurs */
	public String getNom(){
		return nom;
	}
	public int getTaille(){
		return taille;
	}
	
	/* Methodes */
	/** Get the size of the grids contained in the Board
     * @return the size of the grids contained in the Board */
	public int getSize(){
		return taille;
	}
	
	/** Put the given ship at the given position */
    public boolean putShip(AbstractShip ship, int x, int y){
		try{
			if(ship.getOrientation()=='n'){
				if(y<ship.getTailleNavire()) throw new ArrayIndexOutOfBoundsException("Fail to put the ship "+ship.getNomNavire()+": out of board");
				for(int i=0;i<ship.getTailleNavire();i++){
					if(this.tableau_navires[x-1][y-1-i].getShip()!=null){
						throw new Superposition("Fail to put the ship "+ship.getNomNavire()+": superposition");
					}
				}
				for(int i=0;i<ship.getTailleNavire();i++){
					this.tableau_navires[x-1][y-1-i].setShip(ship);
				}
			}
			if(ship.getOrientation()=='s'){
				if(this.taille+1-y < ship.getTailleNavire()) throw new ArrayIndexOutOfBoundsException("Fail to put the ship "+ship.getNomNavire()+": out of board");
				for(int i=0;i<ship.getTailleNavire();i++){
					if(this.tableau_navires[x-1][y-1+i].getShip()!=null){
						throw new Superposition("Fail to put the ship "+ship.getNomNavire()+": superposition");
					}
				}
				for(int i=0;i<ship.getTailleNavire();i++){
					this.tableau_navires[x-1][y-1+i].setShip(ship);
				}
			}
			if(ship.getOrientation()=='w'){
				if(x<ship.getTailleNavire()) throw new ArrayIndexOutOfBoundsException("Fail to put the ship "+ship.getNomNavire()+": out of board");
				for(int i=0;i<ship.getTailleNavire();i++){
					if(this.tableau_navires[x-1-i][y-1].getShip()!=null){
						throw new Superposition("Fail to put the ship "+ship.getNomNavire()+": superposition");
					}
				}
				for(int i=0;i<ship.getTailleNavire();i++){
					this.tableau_navires[x-1-i][y-1].setShip(ship);
				}
			}
			if(ship.getOrientation()=='e'){
				if(this.taille+1-x < ship.getTailleNavire()) throw new ArrayIndexOutOfBoundsException("Fail to put the ship "+ship.getNomNavire()+": out of board");
				for(int i=0;i<ship.getTailleNavire();i++){
					if(this.tableau_navires[x-1+i][y-1].getShip()!=null){
						throw new Superposition("Fail to put the ship "+ship.getNomNavire()+": superposition");
					}
				}
				for(int i=0;i<ship.getTailleNavire();i++){
					this.tableau_navires[x-1+i][y-1].setShip(ship);
				}
			}
			return true;
		} catch(ArrayIndexOutOfBoundsException e){
			System.out.println(e);
			return false;
		} catch(Superposition e){
			System.out.println(e);
			return false;
		}
	}
	
	/** Get if a ship is placed at the given position */
    public boolean hasShip(int x, int y){
		if(this.tableau_navires[x-1][y-1].getShip()!=null) return true;
		else return false;
	}
	
	/** Set the state of the hit at a given position
     * @param hit true if the hit must be set to successful */
    public void setHit(Boolean hit, int x, int y){
		this.tableau_frappes[x-1][y-1] = hit;
	}

    /** Get the state of a hit at the given position
	@return true if the hit is successful */
    public Boolean getHit(int x, int y){
    	if(tableau_frappes[x-1][y-1]==null) return null;
    	else if(tableau_frappes[x-1][y-1]==true) return true;
		else if(tableau_frappes[x-1][y-1]==false) return false; 	
		return true;	
	}
    
    public Boolean isSunk(int x, int y){
    	if(this.tableau_navires[x-1][y-1].isSunk()==false) return false;	
		return true;	
	}
    
    public void addStrike(int x, int y) {
    	tableau_navires[x-1][y-1].addStrike();
	}
    
    public char getLabel(int x, int y) {
    	return tableau_navires[x-1][y-1].getShip().getLabel();
    }
	
    public int getTaille(int x, int y) {
    	return tableau_navires[x-1][y-1].getShip().getTailleNavire();
    }
    
    public Hit sendHit(int x, int y) {
    	if(tableau_navires[x-1][y-1].getShip()==null) {
    		
    		this.tableau_frappes[x-1][y-1] = false;
    		return Hit.fromInt(-1);
    	}
    	
    	else if(this.tableau_navires[x-1][y-1].getShip()!=null && this.tableau_navires[x-1][y-1].isSunk()==false) {
    		this.tableau_frappes[x-1][y-1] = true;
    		this.tableau_navires[x-1][y-1].addStrike();
    		return Hit.fromInt(-2);
    	} 
    	else if(this.tableau_navires[x-1][y-1].getShip()!=null && this.tableau_navires[x-1][y-1].isSunk()==true) {
    		this.tableau_frappes[x-1][y-1] = true;
    		return Hit.fromInt(tableau_navires[x-1][y-1].getShip().getTailleNavire());
    	} 
    	return Hit.fromInt(-2);
    }
    
	/** dessine les deux grilles de jeu dans leur etat respectif **/
    public void print() {
        System.out.println("Navires :");
		System.out.print("  ");
		for(int i=0;i<this.taille;i++) System.out.print(" " + (char)(i+65));
		System.out.println(" ");
		for(int i=0;i<this.taille;i++){
			System.out.print(i+1);
			if(i<9) System.out.print(" ");
		for(int j=0;j<this.taille;j++){
			if(tableau_navires[j][i].getShip()!=null) System.out.print(" " + this.tableau_navires[j][i].getShip().getLabel());
			else System.out.print(" " + ".");
		} 	
			System.out.println(" ");
		} 
		System.out.println(" ");
		System.out.println("Frappes :");
		System.out.print("  ");
		for(int i=0;i<this.taille;i++) System.out.print(" " + (char)(i+65));
		System.out.println(" ");
		for(int i=0;i<this.taille;i++){
			System.out.print(i+1);
			if(i<9) System.out.print(" ");
			for(int j=0;j<this.taille;j++){
				if(tableau_frappes[j][i]==null) System.out.print(" " + ".");
				else if(tableau_frappes[j][i]==true) System.out.print(ColorUtil.colorize(" " + "x", ColorUtil.Color.RED));
				else if(tableau_frappes[j][i]==false) System.out.print(" " + "x");
			} 	
			System.out.println(" ");	
		} 
    }
}
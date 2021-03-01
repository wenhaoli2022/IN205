public interface IBoard { 

    /**
     * Get the size of the grids contained in the Board
     * @return the size of the grids contained in the Board
     */
    public int getSize();

    /**
    * Put the given ship at the given position
    * @param ship The ship to place on the board
    * @param x
    * @param y
    */
    public boolean putShip(AbstractShip ship, int x, int y);

    /**
     * Get if a ship is placed at the given position
     * @param x
     * @param y
     * @return true if a ship is located at the given position
     */
    public boolean hasShip(int x, int y);

    /**
     * Set the state of the hit at a given position
     * @param hit true if the hit must be set to successful
     * @param x
     * @param y
     */
    public void setHit(Boolean hit, int x, int y);

    /**
     * Get the state of a hit at the given position
     * @param x
     * @param y
     * @return true if the hit is successful
     */
    public Boolean getHit(int x, int y);
    
    /**
    * Sends a hit at the given position
    * @param x
    * @param y
    * @return status for the hit (eg : strike or miss)
    */
    public Hit sendHit(int x, int y);
    
    public Boolean isSunk(int x, int y);
    
    public void addStrike(int x, int y);
    
    public char getLabel(int x, int y);
	
    public int getTaille(int x, int y);
}

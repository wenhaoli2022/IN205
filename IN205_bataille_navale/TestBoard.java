import java.util.ArrayList;
import java.util.List;

public class TestBoard {
	public static void main(String[] args) {
		Board board1 = new Board("test1");
		Board board2 = new Board("test2");
		Destroyer destroyer = new Destroyer();
		Submarine submarine1 = new Submarine();
		Submarine submarine2 = new Submarine();
		Battleship battleship = new Battleship();
		Carrier carrier = new Carrier();
		List<AbstractShip> list = new ArrayList<>();
		list.add(destroyer);
		list.add(submarine1);
		list.add(submarine2);
		list.add(battleship);
		list.add(carrier);
		Player player = new Player(board1,board2,list);
		//player.putShips();
		int[] coords = new int[2];
		player.sendHit(coords);
	} 
}
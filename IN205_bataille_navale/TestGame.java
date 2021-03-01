import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestGame {
	public static void main(String[] args) {
		Game game = new Game();
		game = game.init();
		
		game.run();
		
		/*Board board1 = new Board("test1",8);
		Board board2 = new Board("test2",8);
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
		BattleShipsAI ai = new BattleShipsAI(board1,board2);
		int count = 0;
		ai.putShips(list);
		int[] coords = new int[2];
		Random rnd = new Random();
		while(count < 5) {
			coords[0]=rnd.nextInt(board1.getSize())+1;
			coords[1]=rnd.nextInt(board1.getSize())+1;
			if(board1.getHit(coords[0],coords[1])==null) {
				Hit hit = ai.sendHit(coords[0],coords[1]);
				if(hit.getValue()==-1) {
					System.out.println("manque");
				}
				else if(hit.getValue()==-2) {
					System.out.println("touche");
				}
				else if(hit.getValue()==2) {
					count++;
					System.out.println("Fregate coule");
				}
				else if(hit.getValue()==3) {
					count++;
					System.out.println("Sous-marin coule");
				}
				else if(hit.getValue()==4) {
					count++;
					System.out.println("Croiseur coule");
				}
				else if(hit.getValue()==5) {
					count++;
					System.out.println("Porte-avion coule");
				}
				board1.print();
			}
			
			sleep(30);
		}*/
	}
	
	private static void sleep(int ms) {
		try {
		Thread.sleep(ms);
		} catch (InterruptedException e) {
		e.printStackTrace();
		} }
}

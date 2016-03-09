package base;

public class Main {//Board Dims: 7 wide 6 tall
	
	public static Pieces pieces;
	public static Window win;
	public static AIPlayer ai;
	public static boolean gameRunning = true;
	public static boolean playerWonGame;

	public static void main(String[] args) {
		pieces = new Pieces();
		win = new Window();
		ai = new AIPlayer(3);//Difficulties: 0 = easy(soly random moves), 1 = medium(easy until vertical or horizontal moves appear), 
		run();				//2 = Hard(best medium move, contains knowledge of diagonal moves, else easy move),
	}						//3 = UltraHard(Hard but checks Y values before placing a piece so it doesn't unintentionally help you)
	
	private static void run() {
		long lastTime = System.nanoTime();
		long nsPT = 10000000L / 60;
		double delta = 0;
		long curTime = System.nanoTime();
		while(true) {
			delta = (curTime - lastTime) / nsPT;
			lastTime = curTime;
			while(delta >= 1) {
				delta -= 1;
				win.repaint();
				//System.out.println("LOOPED");
			}
			curTime = System.nanoTime();
		}
	}

}

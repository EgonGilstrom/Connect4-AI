package base;

import java.awt.Color;
import java.awt.Graphics;

public class Pieces {

	public int[][][] pieces;//IDs(index 2): AI == 1, Player == 2, Empty == 0
	public int[] curSelect = new int[2];
	
	public Pieces() {//Y coordinate is inversed
		pieces = new int[7][6][3];
		for(int i = 0; i < pieces.length; i++)
			for(int j = 0; j < pieces[i].length; j++) {
				pieces[i][j][0] = i * 30 + 50;
				pieces[i][j][1] = j * 30 + 50;
				pieces[i][j][2] = 0;
			}
		curSelect[0] = 0;
		curSelect[1] = 5;
	}
	
	public void updatePiece(int pX, int pY, int pID) {
		pieces[pX][pY][2] = pID;
	}
	
	public int findOpenY(int x) {//BooBoo mabey here(also below) cant move over enemy piece
		int i = pieces[x].length - 1;
		while(pieces[x][i][2] != 0 && i > 0) {
			i--;
			//System.out.println(i);
		}
		if(i == 0 && pieces[x][0][2] != 0) return -1;
		return i;
	}
	
	public void draw(Graphics g) {
		for(int i = 0; i < pieces.length; i++) 
			for(int j = 0; j < pieces[i].length; j++) {
				if(pieces[i][j][2] == 0) g.setColor(Color.WHITE);
				else if(pieces[i][j][2] == 1) g.setColor(Color.BLUE);
				else g.setColor(Color.RED);
				g.fillOval(pieces[i][j][0], pieces[i][j][1], 20, 20);
			}
		g.setColor(Color.YELLOW);
		if(curSelect[1] >= 0 && Main.gameRunning)
			g.fillOval(pieces[curSelect[0]][curSelect[1]][0], pieces[curSelect[0]][curSelect[1]][1], 20, 20);
		else if(!Main.gameRunning) {
			g.setColor(Color.BLACK);
			g.drawString("GAME OVER!", 100, 90);
			if(Main.playerWonGame) g.drawString("Congrats You Won!", 100, 100);
			else g.drawString("Sorry, But YOU LOST!", 100, 80);
		}
	}

	public void moveCurSelect(boolean right) {
		if(right) {
			if(curSelect[0] < pieces.length - 1) {//++
				int x = curSelect[0];
				int flagY;
				do { 
					x++;
					flagY = findOpenY(x);
				} while(x < (pieces.length - 1) && flagY < 0);
				curSelect[0] = x;
				curSelect[1] = flagY;
			}
		} else {
			if(curSelect[0] > 0) {//--
				int x = curSelect[0];
				int flagY;
				do {
					x--;
					flagY = findOpenY(x);
				} while(x > 0 && flagY < 0);
				curSelect[0] = x;
				curSelect[1] = flagY;
			}
		}
	}

	public void placePlayerPiece() {
		if(curSelect[1] >= 0 && pieces[curSelect[0]][curSelect[1]][2] == 0) {
			pieces[curSelect[0]][curSelect[1]][2] = 2;
			if(checkGameOver()) {
				Main.gameRunning = false;
				System.out.println("You Win!!! Congrats");
			}
			else {
				int m = Main.ai.makeMove();
				if(m < 0) System.out.println("Game Over: Tie... Good job that is hard to do.");
				else placeAIpiece(m);
				int newY = findOpenY(curSelect[0]);
				if(newY >= 0) curSelect[1] = newY;
				else {
					moveCurSelect(true);
					moveCurSelect(false);
				}
			}
		} else {
			System.out.println("ERROR: SELECTION OUT OF BOUNDS OF BOARD!");
			System.out.print(curSelect[0]);
			System.out.print(", ");
			System.out.println(curSelect[1]);
		}
	}
	
	public void placeAIpiece(int x) {
		if(x > 6 || x < 0) {
			System.out.println("ERROR In AI: Ai piece Out Of Bounds!!!!");
			System.out.println(x);
		} else {
			int y = findOpenY(x);
			if(y < 0) {
				System.out.println("ERROR: AI Piece Placed In Full Row");
				System.out.println(x);
			}
			else pieces[x][y][2] = 1;
		} if(checkGameOver()) System.out.println("You Loose!!! Get Better Now!!");
	}
	
	public boolean checkGameOver() {
		boolean flag = false;
		for(int x = 0; x < pieces.length; x++) {
			if(flag) break;
			for(int y = 0; y < pieces[x].length; y++) {
				if(pieces[x][y][2] != 0)
					if(checkVert(x, y) || checkHor(x, y) || checkDR(x, y) || checkDL(x, y)) {
						Main.gameRunning = false;
						return true;
					}
			}
		}
		return false;
	}
	
	private boolean checkVert(int x, int y) {
		if(y > 2) return false;
		for(int i = 0; i < 4; i++) if(pieces[x][y + i][2] != pieces[x][y][2]) return false;
		if(pieces[x][y][2] == 1) Main.playerWonGame = false;
		else Main.playerWonGame = true;
		return true;
	}
	
	private boolean checkHor(int x, int y) {
		if(x > 3) return false;
		for(int i = 0; i < 4; i++) if(pieces[x + i][y][2] != pieces[x][y][2]) return false;
		if(pieces[x][y][2] == 1) Main.playerWonGame = false;
		else Main.playerWonGame = true;
		return true;
	}
	
	private boolean checkDR(int x, int y) {
		if(y > 2 || x > 3) return false;
		for(int i = 0; i < 4; i++) if(pieces[x + i][y + i][2] != pieces[x][y][2]) return false;
		if(pieces[x][y][2] == 1) Main.playerWonGame = false;
		else Main.playerWonGame = true;
		return true;
	}
	
	private boolean checkDL(int x, int y) {
		if(y > 2 || x < 3) return false;
		for(int i = 0; i < 4; i++) if(pieces[x - i][y + i][2] != pieces[x][y][2]) return false;
		if(pieces[x][y][2] == 1) Main.playerWonGame = false;
		else Main.playerWonGame = true;
		return true;
	}
}

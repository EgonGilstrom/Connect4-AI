package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class AIPlayer {

	private int difficulty;
	private Random rand = new Random();
	private int[] transVar = new int[2];//anytime data needs to be transfered between 2 classes, this is what is used ( if it is an int)
	private comparator comp = new comparator();
	
	public AIPlayer(int Difficulty) {
		difficulty = Difficulty;
	}
	
	public int makeMove() {
		if(difficulty == 0) return easyMove();
		else if(difficulty == 1) return mediumMove();
		else if(difficulty == 2) return hardMove();
		else return ultraHardMove();
	}
	
	private int ultraHardMove() {
		ArrayList<int[]> moves = new ArrayList<int[]>();
		int temp;
		int[] temp2;
		System.out.println("<=====Start Turn=====>");
		for(int x = 0; x < Main.pieces.pieces.length; x++)
			for(int y = 0; y < Main.pieces.pieces[x].length; y++) {
				if(Main.pieces.pieces[x][y][2] != 0) {
					temp2 = searchDiagnalR(x, y, Main.pieces.pieces[x][y][2]);
					temp = searchVert(x, y, Main.pieces.pieces[x][y][2]);
					if(temp2[1] > 1) {
						if((y == 5 && x - temp2[0] == 0) || Main.pieces.pieces[temp2[0]][y - ( temp2[0] - x ) + 1][2] != 0) {
							int[] temp3 = {temp2[0], temp2[1], Main.pieces.pieces[x][y][2]};
							System.out.print("Adding DiagnalRight: X: ");
							System.out.print(temp2[0]);
							System.out.print(", Importance: ");
							System.out.print(temp2[1]);
							System.out.print(", PieceID: ");
							System.out.println(Main.pieces.pieces[x][y][2]);
							moves.add(temp3);
						} else System.out.println("Stopped Detrimental Move!");
					}
					temp2 = searchDiagnalL(x, y, Main.pieces.pieces[x][y][2]);
					if(temp2[1] > 1) {
						if((y == 5 && x - temp2[0] == 0) || Main.pieces.pieces[temp2[0]][y - ( x - temp2[0] ) + 1][2] != 0) {
							int[] temp3 = {temp2[0], temp2[1], Main.pieces.pieces[x][y][2]};
							System.out.print("Adding DiagnalLeft: X: ");
							System.out.print(temp2[0]);
							System.out.print(", Importance: ");
							System.out.print(temp2[1]);
							System.out.print(", PieceID: ");
							System.out.println(Main.pieces.pieces[x][y][2]);
							moves.add(temp3);
						} else System.out.println("Stopped Detrimental Move!");
					}
					if(temp > 1) {//no if needed as y - 1 is unarguably going to be a piece
						int[] temp3 = {x, temp, Main.pieces.pieces[x][y][2]};
						System.out.print("Adding Horizontal: X: ");
						System.out.print(x);
						System.out.print(", Importance: ");
						System.out.print(temp);
						System.out.print(", PieceID: ");
						System.out.println(Main.pieces.pieces[x][y][2]);
						moves.add(temp3);
					}
					temp2 = searchHor(x, y, Main.pieces.pieces[x][y][2]);
					if(temp2[1] > 1) {
						if((y < 5 && Main.pieces.pieces[temp2[0]][y + 1][2] != 0) || y == 5) {
							int[] temp3 = {temp2[0], temp2[1], Main.pieces.pieces[x][y][2]};
							System.out.print("Adding Horizontal: X: ");
							System.out.print(temp2[0]);
							System.out.print(", Y: ");
							System.out.print(y);
							System.out.print(", Importance: ");
							System.out.print(temp2[1]);
							System.out.print(", PieceID: ");
							System.out.println(Main.pieces.pieces[x][y][2]);
							moves.add(temp3);
						} else System.out.println("Stopped Detrimental Move!");
					}
				}
			}
		System.out.println("<=====Search End=====>");
		if(moves.size() < 1) return easyMove();
		Collections.sort(moves, comp);
		for(int[] i : moves) {
			System.out.print("X: ");
			System.out.print(i[0]);
			System.out.print(", Importance: ");
			System.out.print(i[1]);
			System.out.print(", PieceID: ");
			System.out.println(i[2]);
		}
		System.out.println("<======End Turn======>");
		return moves.get(0)[0];
	}

	private int hardMove() {
		ArrayList<int[]> moves = new ArrayList<int[]>();
		int temp;
		int[] temp2;
		for(int x = 0; x < Main.pieces.pieces.length; x++)
			for(int y = 0; y < Main.pieces.pieces[x].length; y++) {
				if(Main.pieces.pieces[x][y][2] != 0) {
					temp2 = searchDiagnalR(x, y, Main.pieces.pieces[x][y][2]);
					temp = searchVert(x, y, Main.pieces.pieces[x][y][2]);
					if(temp2[1] > 1) {
						int[] temp3 = {temp2[0], temp2[1], Main.pieces.pieces[x][y][2]};
						System.out.print("Adding DiagnalRight: X: ");
						System.out.print(temp2[0]);
						System.out.print(", Importance: ");
						System.out.print(temp2[1]);
						System.out.print(", PieceID: ");
						System.out.println(Main.pieces.pieces[x][y][2]);
						moves.add(temp3);
					}
					temp2 = searchDiagnalL(x, y, Main.pieces.pieces[x][y][2]);
					if(temp2[1] > 1) {
						int[] temp3 = {temp2[0], temp2[1], Main.pieces.pieces[x][y][2]};
						System.out.print("Adding DiagnalLeft: X: ");
						System.out.print(temp2[0]);
						System.out.print(", Importance: ");
						System.out.print(temp2[1]);
						System.out.print(", PieceID: ");
						System.out.println(Main.pieces.pieces[x][y][2]);
						moves.add(temp3);
					}
					if(temp > 1) {
						int[] temp3 = {x, temp, Main.pieces.pieces[x][y][2]};
						System.out.print("Adding Horizontal: X: ");
						System.out.print(x);
						System.out.print(", Importance: ");
						System.out.print(temp);
						System.out.print(", PieceID: ");
						System.out.println(Main.pieces.pieces[x][y][2]);
						moves.add(temp3);
					}
					temp2 = searchHor(x, y, Main.pieces.pieces[x][y][2]);
					if(temp2[1] > 1) {
						int[] temp3 = {temp2[0], temp2[1], Main.pieces.pieces[x][y][2]};
						System.out.print("Adding Horizontal: X: ");
						System.out.print(temp2[0]);
						System.out.print(", Importance: ");
						System.out.print(temp2[1]);
						System.out.print(", PieceID: ");
						System.out.println(Main.pieces.pieces[x][y][2]);
						moves.add(temp3);
					}
				}
			}
		System.out.println("<======End Search======>");
		if(moves.size() < 1) {
			System.out.println("Moved Via Random");
			return easyMove();
		}
		Collections.sort(moves, comp);
		for(int[] i : moves) {
			System.out.print("X: ");
			System.out.print(i[0]);
			System.out.print(", Importance: ");
			System.out.print(i[1]);
			System.out.print(", PieceID: ");
			System.out.println(i[2]);
		}
		System.out.println("<======End Turn======>");
		return moves.get(0)[0];
	}
	
	private int[] searchDiagnalR(int x, int y, int pieceID) {
		int[] retval = new int[2];//x, importance
		retval[1] = -1;
		if(x > 3) return retval;
		if(y < 3) return retval;
		int imp = 0;
		for(int i = 0; i < 4; i++) {
			if(Main.pieces.pieces[x + i][y - i][2] == pieceID) {imp++; retval[0] = x + i;}
			else if(Main.pieces.pieces[x + i][y - i][2] != 0) return retval;
		}
		for(int i = 0; i < 4; i++) if(Main.pieces.pieces[x + i][y - i][2] == 0) retval[0] = x + i;
		retval[1] = imp;
		return retval;
	}
	
	private int[] searchDiagnalL(int x, int y, int pieceID) {
		int[] retval = new int[2];
		retval[1] = -1;
		if(x < 3) return retval;
		if(y < 3) return retval;
		int imp = 0;
		for(int i = 0; i < 4; i++) {
			if(Main.pieces.pieces[x - i][y - i][2] == pieceID) {imp++; retval[0] = x - i;}
			else if(Main.pieces.pieces[x - i][y - i][2] != 0) return retval;
		}
		for(int i = 0; i < 4; i++) if(Main.pieces.pieces[x - i][y - i][2] == 0) retval[0] = x - i;
		retval[1] = imp;
		return retval;
	}

	private int mediumMove() {
		ArrayList<int[]> moves = new ArrayList<int[]>();//compiles all possible moves to make that are somewhat reasonable
		int[] temp;
		int temp2;
		for(int x = 0; x < Main.pieces.pieces.length; x++) 
			for(int y = 0; y < Main.pieces.pieces[x].length; y++) {
				if(Main.pieces.pieces[x][y][2] != 0) {
					temp2 = searchVert(x, y, Main.pieces.pieces[x][y][2]);
					temp = searchHor(x, y, Main.pieces.pieces[x][y][2]);
					//System.out.print("mediumMove, searchVert retval: ");
					//System.out.println(temp2);
					if(temp2 > 2) {
						int[] temp3 = {x, temp2, Main.pieces.pieces[x][y][2]};//x, importance level, piece type
						moves.add(temp3);
					}
					if(temp[1] > 2) {
						int[] temp3 = {temp[0], temp[1], Main.pieces.pieces[x][y][2]};//same as above
						moves.add(temp3);
					}
				}
			}
		if(moves.size() < 1) {
			transVar[0] = -1;
			return easyMove();
		}
		Collections.sort(moves, comp);
		//if(difficulty > 1) {transVar[0] = moves.get(0)[1]; transVar[1] = moves.get(0)[2];}
		return moves.get(0)[0];
	}
	
	private int[] searchHor(int x, int y, int pieceID) {//r = {X, Importance}
		if(x > 3) {//returns r = {-1} for any instance where no move should be made
			int[] r = new int[2];
			r[1] = -1;
			return r;
		}
		int[] r = new int[2];
		r[0] = -1;
		for(int i = 0; i < 4; i++) {
			if(Main.pieces.pieces[x + i][y][2] == pieceID) r[1]++;
			else if(r[1] < 3 && Main.pieces.pieces[x + i][y][2] != 0) { r[1] = -1; return r; }
		}
		for(int i = 0; i < 4; i++) if(Main.pieces.pieces[x + i][y][2] == 0) r[0] = x + i;
		if(r[0] < 0 && x > 0) if(Main.pieces.pieces[x - 1][y][2] == 0) r[0] = x - 1;
		if(r[0] < 0) {
			r = new int[2];
			r[1] = -1;
			return r;
		}
		return r;
	}
	
	private int searchVert(int x, int y, int pieceID) {//returns importance of making a move here, the grater the number the more important
		if(y < 3) {
			//System.out.println(y);
			return -1;
		}
		int aboveC = 0;
		for(int i = 0; i < 4; i++) {
			if(Main.pieces.pieces[x][y - i][2] == pieceID) aboveC++;
			else if(Main.pieces.pieces[x][y - i][2] != 0) return -1;
		}
		//System.out.print("searchVert retVal: ");
		//System.out.println(aboveC);
		return aboveC;
	}

	private int easyMove() {
		int move = 0;
		int cap = 150;
		do { move = rand.nextInt(7); cap--; } while(Main.pieces.findOpenY(move) < 0 ^ cap <= 0);
		return cap <= 0 ? -1 : move;
	}
	
	private class comparator implements Comparator<int[]> {

		@Override
		public int compare(int[] ints, int[] ints2) {
			if(ints[2] != ints2[2] && ints[0] == ints2[0]) return Integer.compare(ints[2], ints2[2]);
			return Integer.compare(ints2[1], ints[1]);
		}
		
	}
}

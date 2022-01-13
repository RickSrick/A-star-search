import static java.util.Arrays.binarySearch;
public class Board implements Comparable<Board> {

	String pater;
	String me;
	private final int[] board;
	int move;
	int manhattan;
	private int conflict;
	private byte lastMove; //0 up, 1 down, 2 left, 3 right; 
	private int zero;

	public static int rowLC(int[] board, int num) {
		final int[][] C = new int[Solver.size][Solver.size];
		final int[] row = new int[Solver.size];

		System.arraycopy(board, num*Solver.size, row, 0, row.length);
		int min = Solver.size*num + 1;
		int max = (Solver.size*num + Solver.size<=Solver.size*Solver.size) ? Solver.size*num + Solver.size : Solver.size*num + Solver.size-1;

		for (int i = 0; i < Solver.size; i++)
			if (row[i] != 0 && row[i] >= min && row[i] <= max)
				for (int j = i; j < Solver.size; j++)
					if (row[j] != 0 && row[j] >= min && row[j] <= max && (row[i] < row[j]) != (i < j)) {	
							C[0][i]++;
							C[j][i] = j+1;
						}
		return getInversions(C);
	}
	public static int colLC (int[] board, int num) {
		final int[][] C = new int[Solver.size][Solver.size];
		final int[] col = new int[Solver.size];
		final int[] des = new int[Solver.size];

		for (int i = 0, j = num+1; i < Solver.size; i++, j+= Solver.size) {
			des[i] = (j < Solver.size*Solver.size) ? j : 0;
			col[i] = (j < Solver.size*Solver.size) ? board[j-1] : 0;
		}

		for (int i = 0; i < Solver.size; i++) {
			int rowIndex = binarySearch(des, col[i]);
			if (col[i] != 0 && 0 <= rowIndex) {
				for (int j = i; j < Solver.size; j++) {
					int colIndex = binarySearch(des, col[j]);
					if (col[j] != 0 && 0 <= colIndex && (col[i] < col[j]) != (i < j)) {
							C[0][i]++;
							C[j][i] = j+1;
					}
				}
			}
		}

		return getInversions(C);
	}
	private static int getInversions (int[][] C) {
		int inversions = 0;
		int k = findMax(C[0]);
		while(C[0][k] > 0) {
			C[0][k] = 0;
			for (int i = 1; i < Solver.size; i++) { 
				int toChangeIndex = C[i][k]-1;
				if(toChangeIndex >= 0)C[0][toChangeIndex]-=1; 
			}
			
			inversions++;
			k = findMax(C[0]);
		}
		return inversions;
	}
	private static int findMax(int[] c) {
		int out = 0;
		int max = c[0];
		for (int i = 1; i < c.length; i++) {
			if(c[i] >= max) {
				max = c[i];
				out = i;
			}
		}
		return out;
	}

	public Board(int [][] tiles) {
		this(matrixToArray(tiles));
	}
	private static int[] matrixToArray(int[][] tiles) {
		final int[] tmp = new int[Solver.size*Solver.size];
		
		for (int i = 0; i < Solver.size; i++) {
			for (int j = 0; j < Solver.size; j++) {
				tmp[i*Solver.size+j] = tiles[i][j];
			}
		}
		return tmp;
	}

	public Board(int[] tiles) {
		board = tiles;
		me = toString(tiles);
		pater = " ";
		move = 0; //0 move initial state
		manhattan = manhattan();
		lastMove = -1;
	}
	public Board(int[] tiles, String _pater, int _move, byte _lastMove, int _manhattan, int _conflict, int _zero) {
		board = tiles;
		me = toString(tiles);
		pater = _pater;
		move = _move;
		lastMove = _lastMove;
		manhattan = _manhattan;
		conflict = _conflict;
		zero = _zero;
	}

	public int manhattan() {
		int tmp = 0;
		int l = board.length;
		for (int i = 0; i < l; i++) {
			if(board[i] == 0) { zero = i; }
			else {
				tmp += mh(board[i], i);
			}
		}

		int _conflict = 0;
		for (int j = 0; j < Solver.size; j++) { 
			_conflict += rowLC(board, j);
			_conflict += colLC(board, j);
		}
		conflict = _conflict;
		return tmp;
	}

	private static int mh(int tile, int index) { return Math.abs(index/Solver.size - (tile-1)/Solver.size) + Math.abs(index%Solver.size - (tile-1)%Solver.size); };
	private int getPriority() { return move + manhattan + 2*(conflict); }
	
	public int compareTo(Board o) {	 return this.getPriority() - o.getPriority(); }
	public String toString() { return me; }	
	private static String toString(int[] tiles) {
		StringBuilder sb = new StringBuilder();
		
		for (int i : tiles) {
			sb.append(i);
			sb.append(" ");
		}
		return sb.toString();
	}

	private Board move(int xIndex, int yIndex) {
		final int[] zArray = {(zero/Solver.size),(zero%Solver.size)};
		final int tile = (zArray[0]+xIndex)*Solver.size+(zArray[1]+yIndex);

		if(((zArray[0]+xIndex)<0 || (zArray[0]+xIndex)>=Solver.size) || ((zArray[1]+yIndex)<0 || (zArray[1]+yIndex)>=Solver.size)) return null;

		int[] tmpTiles = new int[board.length];
		System.arraycopy(board, 0, tmpTiles, 0, board.length);
		int newManhattan = manhattan;

		newManhattan -= mh(tmpTiles[tile], tile);

		tmpTiles[zero] = tmpTiles[tile];
		tmpTiles[tile] = 0;

		if(Solver.boardDatabase.containsKey(toString(tmpTiles))) return null;
		
		newManhattan += mh(tmpTiles[zero], zero);
		int newConflict = 0;
		for (int i = 0; i < Solver.size; i++) {
			newConflict += rowLC(tmpTiles, i);
			newConflict += colLC(tmpTiles, i);
		}

		byte newLastMove;
		if(xIndex == 0 && yIndex != 0){
			newLastMove = (yIndex > 0) ? (byte)3 : (byte)2;
		}
		else newLastMove = (xIndex > 0) ? (byte)0 : (byte)1;

		return new Board(tmpTiles, this.toString(), move+1, newLastMove, newManhattan, newConflict, tile);
	}
	public Board[] getChildren(){
		final Board[] children = new Board[4];
		
		switch(lastMove) {
			case 0:
				children[0] = move(1, 0);
				children[1] = move(0, -1);
				children[2] = move(0, 1);
				break;
			case 1:
				children[0] = move(-1, 0);
				children[1] = move(0,-1);
				children[2] = move(0, 1);
				break;
			case 2:
				children[0] = move(-1,0);
				children[1] = move(1, 0);
				children[2] = move(0, -1);
				break;
			case 3:
				children[0] = move(-1,0);
				children[1] = move(1,0);
				children[2] = move(0,1);
				break;
			default:
				children[0] = move(-1,0);
				children[1] = move(1,0);
				children[2] = move(0,1);
				children[3] = move(0,-1);
			}
		return children;
	}
}
public class BoardV2 implements Comparable<BoardV2> {
	
	BoardV2 pater;
	final int [] board;
	static int size;
	final int[] data = new int[2]; //0 move , 1 manhattan
	byte lastmove; //0 up ,  1 down , 2 left, 3 right 
	int z;
	
	public BoardV2(int[][] tiles){
		this(copymatrix(tiles));
	}

	private static int[] copymatrix (int[][] pater) {
		size = pater.length;
		final int[] tmp = new  int[size*size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				tmp[i*size+j] = pater[i][j];
			}
		}
		return tmp; 
	}

	public BoardV2(int[] tiles) {
		board = tiles;
		size = (int) (board.length/Math.sqrt(board.length));
		pater = this;
		data[0] = 0;
		data[1] = manhattan();
		lastmove = -1;
	}
	
	public BoardV2(int[] tiles, BoardV2 p, byte lm, int mh, int zerox, int zeroy) {
		board = tiles;
		pater = p;
		data[0] = pater.data[0] + 1;
		data[1] = mh;
		z = zerox*size+zeroy;
		lastmove = lm;
	}

	public int getPriority() { return data[0] + data[1]; }
	private static boolean linearConflict(int[] board, int x, int y) { return  (board[x*size+y] != 0) && (x == (board[x*size+y]-1)/size || y == (board[x*size+y]-1)%size ) && ( x*size+y+1 != board[x*size+y] ) && ( x*size+y+1 == board[((board[x*size+y]-1)/size)*size+((board[x*size+y]-1)%size)]); }
	private static int mh(int[] board, int x, int y) { return Math.abs(x - (board[x*size+y]-1)/size) + Math.abs(y - (board[x*size+y]-1)%size); }

	public BoardV2[] getChildren() {
		final BoardV2[] children = new BoardV2[4];
		byte k = 0;
		
		switch(lastmove) {
			case 0:
				children[k++] = Normal(1, 0);
				children[k++] = Normal(0, -1);
				children[k++] = Normal(0, 1);
				break;
			case 1:
				children[k++] = Normal(-1, 0);
				children[k++] = Normal(0,-1);
				children[k++] = Normal(0, 1);
				break;
			case 2:
				children[k++] = Normal(-1,0);
				children[k++] = Normal(1, 0);
				children[k++] = Normal(0, 1);
				break;
			case 3:
				children[k++] = Normal(-1,0);
				children[k++] = Normal(1,0);
				children[k++] = Normal(0,-1);
				break;
			default:
				children[k++] = Normal(-1,0);
				children[k++] = Normal(1,0);
				children[k++] = Normal(0,1);
				children[k++] = Normal(0,-1);
			}
		return children;
	}

	public BoardV2 Normal (int xindex, int yindex) {
		int[] zero = {(z/size), (z%size)};
		int xpos = zero[0] + xindex;
		int ypos = zero[1] + yindex;
		if((xpos < 0 || xpos >= size) || (ypos < 0 || ypos >= size)) return null;
		int[] tmp = new int[board.length];
		System.arraycopy(board, 0, tmp, 0, board.length);
		int mh = data[1];

		mh -= mh(tmp, xpos, ypos);
        if (linearConflict(tmp, xpos, ypos)) mh -= 2;

		tmp[zero[0]*size+zero[1]] = tmp[xpos*size+ypos];
		tmp[xpos*size+ypos] = 0;

		mh += mh(tmp, zero[0], zero[1]);
		if (linearConflict(tmp, zero[0], zero[1])) mh += 2;
		
		byte lastMove;

		if(xindex == 0 && yindex != 0){
			lastMove = (yindex > 0) ? (byte)2 : (byte)3;
		}
		else lastMove = (xindex > 0) ? (byte)0 : (byte)1;
		
		
		return new BoardV2(tmp, this, lastMove, mh, xpos, ypos);
	}

	public int manhattan() {
		int tmp = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i*size+j] == 0) { z = i*size+j; }
				else {
					tmp += mh(board, i, j);
					if (linearConflict(board, i, j)) tmp++; 
				}
			}
		}
		return tmp;
	}
	
	public String toString() { 
		StringBuilder sb = new StringBuilder();
		
		for (int x : board) {
			sb.append(x);
			sb.append(" ");
		}
		return sb.toString();
	}

	public int compareTo(BoardV2 b) { return this.getPriority() - b.getPriority(); }
	public boolean equals(Object obj) { return ((BoardV2) obj).toString().equals(this.toString()); }

}

import java.util.Arrays;
public final class Board implements Comparable<Board> {

    Board pater;
    int [][] board;
    static int size;
    int [] data = new int[2]; //0 move , 1 manhattan
    final boolean[] lastmove = new boolean[4]; //0 up ,  1 down , 2 left, 3 right 
    final int [] zero = new int[2];

    public Board(int[][] tiles) { 
        board = tiles;
        pater = null;
        size = tiles.length;
        data[0] = 0;
        data[1] = manhattan();        
    }
    
    public Board(int[][] tiles, Board p, int m, int lm, int mh, int zerox, int zeroy) {
        board = tiles;
        pater = p;
        data[0] = m;
        data[1] = mh; 
        zero[0] = zerox;
        zero[1] = zeroy;
        lastmove[lm] = true;
    }

    public int getPriority() { return data[0] + data[1]; }

    public Board[] getChildren() {
        final Board[] children = new Board[4];
        int k = 0;
        if(!lastmove[0]) {
            children[k++] = Vertical(-1);
        } 
        if(!lastmove[1]) {
            children[k++] = Vertical(1);
        }
        if(!lastmove[2]) {
            children[k++] = Horizontal(-1);
        }
        if(!lastmove[3]) {
            children[k++] = Horizontal(1);
        }
        return children;
    }

    private static int[][] copymatrix (int[][] pater) {
        final int[][] tmp = new  int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tmp[i][j] = pater[i][j];
            }
        }
        return tmp; 
    }

    public Board Vertical (int index) {
        int pos = zero[0] + index;
        if(pos < 0 || pos >= size) return null;
        int[][] tmp = copymatrix(board);
        int mh = data[1];

        mh -=  Math.abs(pos - (tmp[pos][zero[1]]-1)/size) + Math.abs(zero[1] - (tmp[pos][zero[1]]-1)%size);

        tmp[zero[0]][zero[1]] ^= tmp[pos][zero[1]];
        tmp[pos][zero[1]] ^= tmp[zero[0]][zero[1]];
        tmp[zero[0]][zero[1]] ^= tmp[pos][zero[1]];

        mh += Math.abs(zero[0] - (tmp[zero[0]][zero[1]]-1)/size) + Math.abs(zero[1] - (tmp[zero[0]][zero[1]]-1)%size);

        byte lastMove = (index > 0) ? (byte)0 : 1;
        return new Board(tmp, this, data[0]+1, lastMove, mh, pos, zero[1]);
    }

    public Board Horizontal (int index) {
        int pos = zero[1] + index;
        if(pos < 0 || pos >= size) return null;
        int[][] tmp = copymatrix(board);
        int mh = data[1];

        mh -=  Math.abs(zero[0] - (tmp[zero[0]][pos]-1)/size) + Math.abs(pos - (tmp[zero[0]][pos]-1)%size);
        
        tmp[zero[0]][zero[1]] ^= tmp[zero[0]][pos];
        tmp[zero[0]][pos] ^= tmp[zero[0]][zero[1]];
        tmp[zero[0]][zero[1]] ^= tmp[zero[0]][pos];

        mh += Math.abs(zero[0] - (tmp[zero[0]][zero[1]]-1)/size) + Math.abs(zero[1] - (tmp[zero[0]][zero[1]]-1)%size);

        byte lastMove = (index > 0) ? (byte)2 : 3;
        return new Board(tmp, this, data[0]+1, lastMove, mh, zero[0], pos);
    }

    public String toString() { return Arrays.deepToString(board); }

    public int manhattan() {
        int tmp = 0;
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) { zero[0] = i; zero[1] = j; }
                tmp += (board[i][j] == 0) ? 0 : Math.abs(i - (board[i][j]-1)/size) + Math.abs(j - (board[i][j]-1)%size);
            }
        }

        return tmp;
    }

    @Override
    public int compareTo(Board b) { return this.getPriority() - b.getPriority(); }

    @Override
    public boolean equals(Object obj) { return ((Board) obj).toString().equals(this.toString()); }
}
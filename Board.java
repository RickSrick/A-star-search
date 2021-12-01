import java.util.Arrays;
import java.util.Stack;

public final class Board implements Comparable<Board> {

    Board pater;
    int [][] board;
    final int [] data = new int[3]; //0 lenght, 1 move , 2 manhattan
    final boolean[] lastmove = new boolean[4]; //0 up ,  1 down , 2 left, 3 right 
    final int [] zero = new int[2];

    public Board(int[][] tiles) { this(tiles,null, 0, -1 ); }
    
    public Board(int[][] tiles, Board p, int m, int lm) {
        board = tiles;
        pater = p;
        data[0] = tiles.length;
        data[1] = m;
        data[2] = manhattan();

        if(lm >= 0) lastmove[lm] = true;
    }

    public int getPriority() { return data[1] + data [2]; }

    //O(4n^2)
    public Stack<Board> getChildren() {
        final Stack<Board> children = new Stack<Board>(); //max child num
        if((!lastmove[0]) ) {
            children.push(Vertical(-1));
        } 
        if(!lastmove[1]) {
            children.push(Vertical(1));
        }
        if(!lastmove[2]) {
            children.push(Horizontal(-1));
        }
        if(!lastmove[3]) {
            children.push(Horizontal(1));
        }
        return children;
    }

    //O(n^2)
   private int[][] copymatrix (int[][] pater) {
       int[][] tmp = new  int[data[0]][data[0]];
        for (int i = 0; i < data[0]; i++) {
            for (int j = 0; j < data[0]; j++) {
                tmp[i][j] = pater[i][j];
            }
        }

        return tmp; 
    }

    public Board Vertical (int index) {
        int pos = zero[0] + index;
        if(pos < 0 || pos >= data[0]) return null;

        int[][] tmp = copymatrix(board);

        tmp[zero[0]][zero[1]] ^= tmp[pos][zero[1]];
        tmp[pos][zero[1]] ^= tmp[zero[0]][zero[1]];
        tmp[zero[0]][zero[1]] ^= tmp[pos][zero[1]];

        byte m = 0;
        if(index > 0) { m = 0; }
        else {m = 1;}

        return new Board(tmp, this, data[1]+1, m);
    }

    public Board Horizontal (int index) {
        int pos = zero[1] + index;
        if(pos < 0 || pos >= data[0]) return null;

        int[][] tmp = copymatrix(board);

        tmp[zero[0]][zero[1]] ^= tmp[zero[0]][pos];
        tmp[zero[0]][pos] ^= tmp[zero[0]][zero[1]];
        tmp[zero[0]][zero[1]] ^= tmp[zero[0]][pos];

        byte m = 0;
        if(index > 0) { m = 2; }
        else {m = 3;}

        return new Board(tmp, this, data[1]+1, m);
    }

    public String toString() {
        return Arrays.deepToString(board);
    }


    public int manhattan() {
        int tmp = 0;

        for (byte i = 0; i < data[0]; i++) {
            for (byte j = 0; j < data[0]; j++) {
                if (board[i][j] == 0) { zero[0] = i; zero[1] = j; }
                tmp += (board[i][j] == 0) ? 0 : Math.abs(i - (board[i][j]-1)/data[0]) + Math.abs(j - (board[i][j]-1)%data[0]);
            }
        }
        return tmp;
    }

    @Override
    public int compareTo(Board b) {
        return this.getPriority() - b.getPriority();
    }

    @Override
    public boolean equals(Object obj) {
        
        if(obj == this) return true;
        //return Arrays.deepEquals(this.board, ((Board) obj).board); 
        return ((Board) obj).toString().equals(this.toString()); 
    }
}
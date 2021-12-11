import java.util.HashSet;
import java.util.PriorityQueue;

public class Solver {

    static final PriorityQueue<Board> pq = new PriorityQueue<Board>();
    static final HashSet<Board> hm = new HashSet<Board>();

    public static void main(String[] args) {

        double tot = 0;
        int maxTest = 1000;

        for (int i = 0; i < maxTest; i++){

        long x = System.currentTimeMillis();


        int[][] intial = {
            {1, 5, 6},
            {7, 4, 3},
            {0, 2, 8}
        };
        final Board b = new Board(intial);
        
        pq.add(b);
        hm.add(b);
        while(pq.peek().data[1] != 0){ solve(pq.poll()); }

        final Board[] result = printPath(pq.peek());

        System.out.println(result.length-1);
        for (int j = 0; j < result.length; j++) {
            System.out.println(result[j]);
        }

        long y =System.currentTimeMillis();

            tot += (y-x);
        }

        System.out.println(tot/maxTest);
    }

    static Board[] printPath(Board start){
        final int length = start.data[0];
        final Board[] path = new Board[length+1];

        for (int i = length; i > 0; i--) {
            path[i] = start;
            start = start.pater;
        }

        path[0] = start;
        return path;
    }

    static void solve (Board b){
        final Board[] children = b.getChildren();
        for(int i = 0; i < 4; i++) {
            if(children[i] != null && !hm.contains(children[i])) {
                pq.add(children[i]);
                hm.add(children[i]);
            }            
        }
    }
    
}
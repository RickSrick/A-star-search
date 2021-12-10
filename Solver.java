import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

public class Solver {

    static final PriorityQueue<Board> pq = new PriorityQueue<Board>();
    static final HashSet<Board> hm = new HashSet<Board>();

    public static void main(String[] args) {

        double tot = 0;
        int maxTest = 100;

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
        while(pq.peek().data[1] != 0){            
            solve(pq.poll());
        }


        final Stack<Board> res = printpath(pq.peek());

        System.out.println(res.size()-1);
        while(res.size() != 0){
            System.out.println(res.pop());
        }

        long y =System.currentTimeMillis();

            tot += (y-x);
        }

        System.out.println(tot/maxTest);
    }

    private static Stack<Board> printpath(Board start) {
        final Stack<Board> ll = new Stack<Board>();
        while(start.pater != null) {
            ll.add(start);
            start = start.pater;
        }
        ll.add(start);
        return ll;
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
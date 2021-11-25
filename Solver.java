import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

public class Solver {

    static  PriorityQueue<Board> pq = new PriorityQueue<Board>();
    static  HashSet<Board> hm = new HashSet<Board>();

    public static void main(String[] args) {

        long x = System.currentTimeMillis();


        int[][] intial = {
                            {5, 1, 2},
                            {4, 8, 3},
                            {0, 6, 7}
                         };
        final Board b = new Board(intial);
        
        pq.add(b);
        while(pq.peek().data[2] != 0){            
            solve(pq.peek());
        }


        final Stack<Board> res = printpath(pq.peek());

        System.out.println(res.size());
        while(res.size() != 0){
            System.out.println(res.pop());
        }

        long y =System.currentTimeMillis();

        System.out.println(y-x);
    }

    private static Stack<Board> printpath(Board start) {
       final Stack<Board> ll = new Stack<Board>();
       Board curr = start;
       while(curr.pater != null) {

            ll.add(curr);
            curr = curr.pater;
       }

       ll.add(curr);
       return ll;
        
    }

    static void solve (Board b){
        final Stack<Board> children = b.getChildren();
        pq.remove(b);
        hm.add(b);
        while(children.size() != 0) {
            
            Board bson = children.pop();

            if(bson != null){
                pq.add(bson);
                if(!hm.contains(bson)) { hm.add(bson); }
            }
        }
    }


    
}
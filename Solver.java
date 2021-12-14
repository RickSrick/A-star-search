import java.util.HashSet;
import java.util.PriorityQueue;

public class Solver {

	static final PriorityQueue<Board> pq = new PriorityQueue<Board>();
	static final HashSet<String> hm = new HashSet<>();

	public static void main(String[] args) {		
		
		double tot = 0;
		for (int i = 0; i < 1000; i++) {
			
		long x = System.currentTimeMillis();

		//9 10 15 5 3 11 14 7 12 6 1 13 2 8 0 4   
		int[][] intial = {
			{9, 0, 2, 7},
			{4, 1, 8, 3},
			{13, 15, 12, 10},
			{14, 6, 11, 5}
		};
		final Board b = new Board(intial);
		pq.add(b);
		
		while(pq.peek().data[1] != 0){ solve(pq.poll()); }

		final String[] result = printPathS(pq.peek());

		System.out.println(result.length-1);
		for (int j = 0; j < result.length; j++) {
			System.out.println(result[j]);
		}

		long y =System.currentTimeMillis();
		tot += y-x;	
		hm.clear();
		pq.clear();	
	}		
		System.out.println(tot/1000);
	}

	static String[] printPathS(Board start){
		final int length = start.data[0];
		final String[] path = new String[length+1];

		for (int i = length; i >= 0; i--) {
			path[i] = start.toString();
			start = start.pater;
		}
		return path;
	}

	static void solve (Board b){
		final Board[] children = b.getChildren();
		final int length = children.length;
		for(int i = 0; i < length; i++) {
			if(children[i] != null && !hm.contains(children[i].toString())) 
				pq.add(children[i]);
		}
		hm.add(b.toString());
		
	}
}
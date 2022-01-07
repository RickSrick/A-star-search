import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;

public class SolverV2 {

	static final PriorityQueue<BoardV2> pq = new PriorityQueue<BoardV2>();
	static final HashSet<String> hm = new HashSet<>();
	public static void main(String[] args) throws FileNotFoundException {		
		

		double x = System.currentTimeMillis();
		final Scanner sc = new Scanner(new File(args[0]));
		final int length = sc.nextInt();
		final int[] array = new int[length*length];
		
		for(int i = 0; sc.hasNext(); i++){
			array[i] = sc.nextInt();
		}

		final BoardV2 b = new BoardV2(array);	
		pq.add(b);
		while(pq.peek().data[1] != 0){ solve(pq.poll()); }

		final String[] result = printPathS(pq.peek());

		System.out.println(result.length-1);
		for (String passage : result) {
			System.out.println(passage);
		}
		double y = System.currentTimeMillis();
		System.out.println(y-x);
	}

	static String[] printPathS(BoardV2 start){
		final int length = start.data[0];
		final String[] path = new String[length+1];

		for (int i = length; i >= 0; i--) {
			path[i] = start.toString();
			start = start.pater;
		}
		return path;
	}

	static void solve (BoardV2 b){
		final BoardV2[] children = b.getChildren();
		for (BoardV2 boardV2 : children) {
			if(boardV2 != null && !hm.contains(boardV2.toString())) {
				pq.add(boardV2);
			}
		}
		hm.add(b.toString());
	}
}
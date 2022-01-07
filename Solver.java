import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solver {

	static final PriorityQueue<Board> pq = new PriorityQueue<Board>();
	static final HashSet<String> hm = new HashSet<>();
	public static void main(String[] args) throws FileNotFoundException {		
		

		double x = System.currentTimeMillis();
		Scanner sc = new Scanner(new File(args[0]));
		final int length = sc.nextInt();
		final int[] array = new int[length*length];
		
		for(int i = 0; sc.hasNext(); i++){
			array[i] = sc.nextInt();
		}

		int[][] initial = arrToMatrix(array, length);

		final Board b = new Board(initial);		
		pq.add(b);
		
		while(pq.peek().data[1] != 0){ 
			solve(pq.poll()); 
		}

		final String[] result = printPathS(pq.peek());

		System.out.println(result.length-1);
		for (int j = 0; j < result.length; j++) {
			System.out.println(result[j]);
		}
		double y = System.currentTimeMillis();
		System.out.println(y-x);
	}

	private static int[][] arrToMatrix(int[] array, int length) {
		final int[][] out = new int[length][length];

		int k = 0;

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				out[i][j] = array[k++];
			}
		}

		return out;
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
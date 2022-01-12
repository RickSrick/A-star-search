import static java.util.Arrays.binarySearch;
public class test {


	public static int algo(int[][] s) {
		int LINEARCONFLICTS = 0;


		for (int x = 0; x < Solver.size; x++) {
			int[] C = new int[Solver.size];
			int min = Solver.size*x + 1;
			int max = Solver.size*x + Solver.size;

			for (int i = 0; i < Solver.size; i++) {

				for (int k = 1; k < Solver.size; k++) {
					if (a[i] != 0 && a[i] >= min && a[i] <= max) {
						for (int j = 0; j < i; j++) {
							if (a[j] != 0 && a[j] >= min && a[j] <= max) {
								if ((a[i] < a[j]) != (i < j)) {
									inversions++;
								}
							}
						}
					} 
				}

			}

		}

	}


	public static int linearConflict(int[] tiles, int rc, int num){
		int inversions = 0;

		if(rc == 0) {
			int[] a = new int[Solver.size];
			System.arraycopy(tiles, Solver.size*num, a, 0, Solver.size);

			int min = Solver.size*num + 1;
			int max = Solver.size*num + Solver.size;

			int x = max;
			boolean end = (a[0] == x--);
			for (int i = 1; i < Solver.size; i++) { end &= (a[i] == x--); }
			System.out.println(end);

			if(end) return Solver.size-1;

			for (int i = 1; i < Solver.size; i++) {
				if (a[i] != 0 && a[i] >= min && a[i] <= max) {
					for (int j = 0; j < i; j++) {
						if (a[j] != 0 && a[j] >= min && a[j] <= max) {
							if ((a[i] < a[j]) != (i < j)) {
								inversions++;
							}
						}
					}
				} 
			}


		}
		else {
			int[] a = new int[Solver.size];
				for (int i = 0, j = num; i < Solver.size; i++, j+= Solver.size) {
					a[i] = tiles[j];
					System.out.print(a[i]);
				}
			int[] des = new int[Solver.size];
			for (int i = 0, j = num+1; i < Solver.size; i++, j+= Solver.size) {
				des[i] = (j < Solver.size*Solver.size) ? j : 0;
			}

			int x = Solver.size-1;
			boolean end = (a[0] == des[x--]);
			for (int i = 1; i < Solver.size; i++) {
				end &= (a[i] == des[x--]);
				System.out.println(end); 
				System.out.println(a[i] + " " + des[Solver.size-1]);
			}
			System.out.println(end);
			if(end) return Solver.size-1;

			for (int i = 1, iPos; i < Solver.size; i++) {
				if (a[i] != 0 && 0 <= (iPos = binarySearch(des, a[i]))) {
					for (int j = 0, jPos; j < i; j++) {
						if (a[j] != 0 && 0 <= (jPos = binarySearch(des, a[j]))) {
							if ((a[i] < a[j]) != (i < j)) {
								inversions++;
								}
							}
						}
					}
				}
			}
			return inversions;
		}
		public static void main(String[] args) {
		Solver.size = 3;
		
		int[] a = { 7, 2, 3, 4, 5, 4, 1, 7, 0 };

		//System.out.println(calcRow(a[0], 1));
		System.out.println(linearConflict(a, 1, 0));
		//System.out.println(linearConflict(a, 0, 2));
		//System.out.println("---------------------");
		//System.out.println("LC:" + linearConflict(a, 1, 0)+"\n---");
		//System.out.println("LC:" + linearConflict(a, 1, 1)+"\n---");
		//System.out.println("LC:" + linearConflict(a, 1, 2)+"\n---");*/
	}
}

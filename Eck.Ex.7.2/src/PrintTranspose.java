/** Eck Exercise 7.2
 * Write function to create transpose of a square 2D int matrix
 * @author Joel
 *
 */
public class PrintTranspose {
	public static void main(String[] args) {
		int[][] array = new int[6][6];
		
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				array[i][j] = (int)(Math.random()*100);			
			}
		}
		
		
		int[][] tra = createTranspose(array);
		
		// Print out original array
		printArray(array);
		System.out.println("--------------------------");
		//Print out transpose
		printArray(tra);
		
	}
	
	private static void printArray(int[][] arr) {
//		for (int i = 0; i < arr.length; i++) {
//			for (int j = 0; j < arr.length; j++) {
//				System.out.printf("%3d\t",arr[i][j]);			
//			}
//			System.out.println();
		//		}
		for (int[] row : arr) {
			for (int item : row) 
				System.out.printf("\t%3d",item);			
			System.out.println();
		}

	}
	
	private static int[][] createTranspose(int[][] arr) {
		int[][] tra;
		int R = arr.length;
		int C = arr[0].length;
		tra = new int[C][R];
		for (int i = 0; i < C; i++) {
			for (int j = 0; j < R; j++) {
				tra[i][j]=arr[j][i];
			}
		}
		return tra;		
	}
}

import java.util.Arrays;

/** Eck Exercise 7.3
 * Time Array.sort vs selectionSort
 * Apply to arrays of real numbers and strings
 * 
 * @author Joel
 *
 */
public class TimeSortingMethods {
	
	public static void main(String[] args) {
		// Create numberList: double array of random numbers
		// Make copy.
		// Let time 0 = current system time
		// Run selectionSort(double.)
		// Let timeSelectionSort = current system time - time0. 
		// Let time 0 = current system time
		// Run Array.sort(numberListCopy)
		// Let timeArraySort = current system time - time0. 
		
		// Create stringList: string array of random characters
		// Make copy.
		// Let time 0 = current system time
		// Run selectionSort(stringList)
		// Let timeSelectionSort = current system time - time0. 
		// Let time 0 = current system time
		// Run Array.sort(stringListCopy)
		// Let timeArraySort = current system time - time0. 
		
		final int SIZE = 1_000; // array size
		long time0; // start time of timers
		long timeSelectionSort; // time of selection sort run
		long timeArraySort; // time of Array.sort() run
		
		double[] numberList1, numberList2;
		String[] stringList1, stringList2;
		
		// Test on small arrays
		System.out.println("Testing selection sort on numbers");
		numberList1 = randomNumbers(10);
		selectionSort(numberList1);
		for (double item : numberList1)
			System.out.println(item);
		System.out.println("------------------");
		System.out.println("Testing selection sort on strings");
		stringList1 = randomStrings(10);
		selectionSort(stringList1);
		for (String item : stringList1)
			System.out.println(item);
		System.out.println("------------------");
		
		// Create numberList1 & copy
		numberList1 = randomNumbers(SIZE);
		numberList2 = Arrays.copyOf(numberList1, SIZE);
		
		// Create stringList1 & copy
		stringList1 = randomStrings(SIZE);
		stringList2 = Arrays.copyOf(stringList1, SIZE);
		
		
		// RUN ON NUMBERLISTS
		// time & run selectionSort
		time0 = System.nanoTime(); // start time		
		selectionSort(numberList1);
		timeSelectionSort = System.nanoTime() - time0;

		// time & run Arrays.sort
		time0 = System.nanoTime();  // start time		
		Arrays.sort(numberList2);
		timeArraySort = System.nanoTime() - time0;
		
		System.out.println("---NUMBER LISTS SIZE " + SIZE + "---");
		System.out.println("Selection Sort took " + timeSelectionSort + " ns.");
		System.out.println("Array.sort() took   " + timeArraySort + " ns.");
		if (timeArraySort < timeSelectionSort) {
			System.out.println("Array sort was faster by " + 
		(timeSelectionSort - timeArraySort) + "ns");
		} else
			System.out.println("Selection sort was faster by " + 
		(timeArraySort -timeSelectionSort) + "ns");

		// RUN ON STRINGLISTS
		// time & run selectionSort
		time0 = System.nanoTime(); // start time		
		selectionSort(stringList1);
		timeSelectionSort = System.nanoTime() - time0;

		// time & run Arrays.sort
		time0 = System.nanoTime();  // start time		
		Arrays.sort(stringList2);
		timeArraySort = System.nanoTime() - time0;
		
		System.out.println("---STRING LISTS SIZE " + SIZE + "---");
		System.out.println("Selection Sort took " + timeSelectionSort + " ns.");
		System.out.println("Array.sort() took   " + timeArraySort + " ns.");
		if (timeArraySort < timeSelectionSort) {
			System.out.println("Array sort was faster by " + 
		(timeSelectionSort - timeArraySort) + "ns");
		} else
			System.out.println("Selection sort was faster by " + 
		(timeArraySort -timeSelectionSort) + "ns");
	}

	/** Create String array of random strings
	 * Create String array of size SIZE, each item is a random string
	 * @param SIZE
	 * @return String array of size SIZE
	 */
	private static String[] randomStrings(int SIZE) {
		String[] array = new String[SIZE];
		for (int i = 0; i < SIZE; i++)
			array[i]=randomString();
		return array;
	}

	/** Create random strings in upper case of random length (5 to 25)
	 * 
	 * @return a string made of random letters
	 */
	private static String randomString() {
		// Generate random length
		int length = (int)( 5 + Math.random()*21);
		
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < length; i++) {
			char ch = (char)('A' + (int)(26*Math.random())); // random upper case char
			str.append(ch);
			}
		return str.toString();
	}

	/** 
	 * creates an array of type double[SIZE]: item values are randomly generated
	 * @param SIZE integer: size of array 
	 * @return double array of size SIZE 
	 */
	private static double[] randomNumbers(int SIZE) {
		double[] array = new double[SIZE];
		for (int i = 0; i < SIZE; i++) 
			array[i] = Math.random();
		return array;
	}
	
	// Method copied from Eck chapter 7
	private static void selectionSort(double[] A) {
		// Sort A into increasing order, using selection sort
		for (int lastPlace = A.length-1; lastPlace > 0; lastPlace--) {
			// Find the largest item among A[0], A[1], ...,
			// A[lastPlace], and move it into position lastPlace 
			// by swapping it with the number that is currently 
			// in position lastPlace.

			int maxLoc = 0;  // Location of largest item seen so far.

			for (int j = 1; j <= lastPlace; j++) {
				if (A[j] > A[maxLoc]) {
					// Since A[j] is bigger than the maximum we've seen
					// so far, j is the new location of the maximum value
					// we've seen so far.
					maxLoc = j;  
				}
			}

			double temp = A[maxLoc];  // Swap largest item with A[lastPlace].
			A[maxLoc] = A[lastPlace];
			A[lastPlace] = temp;

		}  // end of for loop
	}

	/** copy of selection sort with string array input
	 * Selection sort on String array; does not return any variable
	 * @param A String
	 */
	private static void selectionSort(String[] A) {
		// Sort A into increasing order, using selection sort
		for (int lastPlace = A.length-1; lastPlace > 0; lastPlace--) {
			// Find the largest item among A[0], A[1], ...,
			// A[lastPlace], and move it into position lastPlace 
			// by swapping it with the number that is currently 
			// in position lastPlace.

			int maxLoc = 0;  // Location of largest item seen so far.

			for (int j = 1; j <= lastPlace; j++) {
				if (A[j].compareTo(A[maxLoc])>0) {
					// Since A[j] is bigger than the maximum we've seen
					// so far, j is the new location of the maximum value
					// we've seen so far.
					maxLoc = j;  
				}
			}

			String temp = A[maxLoc];  // Swap largest item with A[lastPlace].
			A[maxLoc] = A[lastPlace];
			A[lastPlace] = temp;

		}  // end of for loop
	}


}

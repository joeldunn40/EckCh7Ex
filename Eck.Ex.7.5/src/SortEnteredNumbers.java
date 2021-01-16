/** Eck Exercise 7.5
 * User enters positive integers on keyboard
 * User can stop the entry by entering 0
 * Program returns numbers in order
 * @author jd07
 *
 */

import javax.swing.plaf.synth.SynthOptionPaneUI;

import textio.TextIO;

/* Ask the user to enter a number
 * check if number is valid (positive integer)
 * 	if negative, ask again
 * 	if zero, stop data entry
 * 	if positive, add to array
 * Slightly faster to apply insertion sort on data entry than use linear sort
 */
public class SortEnteredNumbers {
	public static void main(String[] args) {
		int maxSize = 100; // maximum size of array
		int[] arr = new int[maxSize]; // array to store integers
		int[] arrAsEntered = new int[maxSize]; // array to store integers as entered
		int size = 0; // current size of array
		int val; // temp var for holding current array element
		
		// test sorting
//		int[] arrTest = {4,3,6,5,2,1,9,7,8,0};
//		int[] arrTest = {4,2,3,1,0};
//		val = arrTest[size];
		
		System.out.println("Start to enter positive integers (0 to end)");
		val = TextIO.getlnInt();
		
		
		while( val > 0 || size > maxSize) {			
			arrAsEntered[size] = val; // build array as entered 
			insertionSort(arr, size, val);
			size++;
			val = TextIO.getlnInt();
//			val = arrTest[size];
		}
		
		selectionSort(arrAsEntered,size);
	
		System.out.println("Insertion Sort");
		printArray(arr, size);
		System.out.println("Selection Sort");
		printArray(arrAsEntered, size);
		
	} //end main

	private static void printArray(int[] numbers, int size) {
		for (int i = 0; i < size; i++)
			System.out.printf("Item %3d: %d\n", i+1, numbers[i]);
	}
	
	private static void insertionSort(int[] numbers, int size, int newNumber) {
		/* Assume already numbers inserted in numerical order: small -> big
		 * let count = 0
		 * while (count < size)
		 * 	if (numbers[count] > newNumber)
		 * 		break;
		 * for (int i = count; i < size; i++)
		 * 		numbers[i+1]=numbers[i];
		 * 	numbers[count] = newNumber;	 
		 */
		 int count = 0;
		 
		 while (count < size) {
		 	if (numbers[count] > newNumber)
		 		break;
		 	count++;
		 }
		 
		 // Start at end, make n[end] = n[end-1], and so on...
		 for (int i = size - 1; i >= count; i--)
			 numbers[i+1]=numbers[i];
		 
		 numbers[count] = newNumber;	 		 
	} // end insertionSort
	
	/** 
	 * Selection sort algorithm
	 * Requires full size array 
	 * @param numbers int array to sort
	 */
	private static void selectionSort(int[] numbers, int size) {
		
//		// Sort in decreasing order (max at numbers[0])
//		for (int top = numbers.length-1; top > 0; top-- ) {
//			// find smallest in numbers, move to end
//			int maxloc = 0;
//			for (int i = 1; i <= top; i++) {
//				if (numbers[i] < numbers[maxloc])
//					maxloc = i; // current lowest value
//			}
//			int temp = numbers[top]; 
//			numbers[top] = numbers[maxloc];
//			numbers[maxloc] = temp;
//		}
		
		// Sort the numbers in A[0], A[1], ..., A[count-1] into
		// increasing order using Selection Sort.
		for ( int lastPlace = size - 1; lastPlace > 0; lastPlace-- ) {
	      int maxLoc = 0;
	      for (int j = 1; j <= lastPlace; j++) {
	         if (numbers[j] > numbers[maxLoc]) {
	            maxLoc = j;
	         }
	      }
	      int temp = numbers[maxLoc];
	      numbers[maxLoc] = numbers[lastPlace];
	      numbers[lastPlace] = temp;
	   }
		
		
	} // end selectionSort
}
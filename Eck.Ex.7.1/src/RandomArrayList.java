import java.util.ArrayList;

/** Eck Exercise 7.1
 * Write subroutine to create ArrayList with random integers
 * all unique  
 * @author Joel
 *
 */

public class RandomArrayList {
	public static void main(String[] args) {
		int arraySize = 22;
		int maxInt = 20;
		ArrayList<Integer> newArr;
		
		newArr = createArray(arraySize,maxInt);
		
		for (int i = 0; i < arraySize; i++) {
			System.out.printf("%4d\t%4d\n",i,newArr.get(i));
		}
		
	}
	
	/** Create integer ArrayList of size arraySize of unique random numbers 
	 * Precondition: arraySize is integer larger than maxint
	 * Postcondition: create ArrayList with different random integers up to a maximum of maxint
	 * @param arraySize Size of array to create (integer)
	 * @param maxInt Maximum potential value of array
	 * @return ArraList<Integer> of size arraySize.
	 */
	private static ArrayList<Integer> createArray(int arraySize, int maxInt) {
		if (arraySize > (maxInt+1) ) {
            throw new IllegalArgumentException("Can't have " + arraySize + 
                    " different integers in the range  0 to " + maxInt);
        }
		
		ArrayList<Integer> intArr;
		intArr = new ArrayList<Integer>();
		
		// Eck used a shorter algorithm:
		// 	Just keep create random numbers while until not included
		// 	use the .indexOf(num) method to see if that's there...
		
		// Create list of ints up to maxInt
		int[] intFull = new int[maxInt+1];
		for (int i = 0; i < intFull.length; i++)
				intFull[i]=i;
		
		// Randomly sort intFull
		for (int i =  intFull.length - 1; i > 0; i--) {
			int randLoc = (int)(Math.random()*(i+1));
			int temp = intFull[randLoc];
			intFull[randLoc] = intFull[i];
			intFull[i] = temp;
		}
		
		// Add first arraySize from randomized intFull to intArr
		for (int i = 0; i < arraySize; i++ ) 
			intArr.add(intFull[i]);
		
		return intArr;
	}
}

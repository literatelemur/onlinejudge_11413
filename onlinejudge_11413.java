import java.util.*;
public class onlinejudge_11413 {
	public static void main(String[] args) {
		
		// Collecting input data.
		Scanner input = new Scanner(System.in);
		while (input.hasNext()){
			int numVes = input.nextInt();
			int numCon = input.nextInt();
			
			int[] vesSizes = new int[numVes];
			int[] conIndices = new int[numCon - 1];
			
			for (int i = 0; i < numVes; i++){
				String num = input.next();
				if (num.length() - 2 > -1) {
					if (num.substring(num.length() - 2, num.length()) != "\n"){
						vesSizes[i] = Integer.parseInt(num);
					}else {
						vesSizes[i] = Integer.parseInt(num.substring(num.length() - 2, num.length()));
					}
				}
			}
			
			// Instantiating an array to contain the indices of separation between containers and the max value for that set of containers.
			// The recursive function (should) run through all valid combinations and return the arrangement with the lowest high.
			int[] finalConAndMax = recFill(vesSizes, conIndices, 0, 0);
			for (int i = 0; i < finalConAndMax.length; i++) {System.out.println(finalConAndMax[i]);}
		}
		input.close();
	}
	
	public static int[] recFill(int[] vesSizes, int[] conIndices, int vesIn, int conIn) {
		if (vesIn < vesSizes.length - 1) {
			// Recursively calling for continuing without moving to a new container.
			int[] goConAndMax = recFill(vesSizes, conIndices, vesIn + 1, conIn);
			// Designating the container separator for the other option (to try all possiblities).
			conIndices[conIn] = vesIn;
			if (conIn + 1 < conIndices.length) {
				// Recursively calling for container separation.
				int[] stopConAndMax = recFill(vesSizes, conIndices, vesIn + 1, conIn + 1);
				// Comparing the two avenues and returning the one with the lowest high.
				if (goConAndMax[goConAndMax.length - 1] < stopConAndMax[goConAndMax.length - 1]) {return goConAndMax;}
				else {return stopConAndMax;}
			}else {
				return goConAndMax;
			}
			
		}else {
			// Base case: When the index for the vessel array has surpassed the length of the array, the recursive function must stop.
			// Checking numerical validity of container indices array (to weed out the cases where the second container is before the first etc.).
			boolean check = checkCon(vesSizes, conIndices);
			if (check){
				// Finding the maximum container value for a given set of containers.
				int max = findMax(vesSizes, conIndices);
				// Combining container index array with the maximum container value to return it.
				int[] conAndMax = new int[conIndices.length + 1];
				for (int i = 0; i < conIndices.length; i++) {
					conAndMax[i] = conIndices[i];
				}
				conAndMax[conAndMax.length - 1] = max;
				return conAndMax;
			}else {
				// Creating a special designator for when a container index check fails (and that set should be dropped).
				int[] badCheck = new int[1];
				badCheck[0] = -1;
				return badCheck;
			}
		}
	}
	
	public static boolean checkCon(int[] vesSizes, int[] conIndices) {
		// Going through all container index data and returning false when a decrease in values is found.
		for (int i = 1; i < conIndices.length; i++) {
			if (conIndices[i] <= conIndices[i - 1]) {return false;}
		}
		return true;
	}
	
	public static int findMax(int[] vesSizes, int[] conIndices) {
		// Instantiating totals array for the sum value for each container for all containers in a set.
		// Index variables are used to keep track of iteration among multiple arrays.
		int[] totals = new int[conIndices.length + 1];
		int totalIn = 0;
		int conIn = 0;
		
		// Running through all vessels and accumulating total values (as well as updating index variables along the way).
		for (int vesIn = 0; vesIn < vesSizes.length; vesIn++) {
			//for (int i=0;i<conIndices.length;i++) {System.out.println(conIndices[i]);}
			if (conIn < conIndices.length) {
				if (vesIn == conIndices[conIn]) {totalIn++; conIn++;}
					totals[totalIn] += vesSizes[vesIn];
			}
		}
		
		// Picking out max value from container totals.
		int max = 0;
		for (totalIn = 1; totalIn < totals.length; totalIn++) {
			if (totals[totalIn] > totals[totalIn - 1]) {max = totals[totalIn];}
		}
		return max;
	}
}

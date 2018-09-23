package nisum.zipcode.javaCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator; 
import java.util.List;
import java.util.Scanner;

public class ZipcodeRangePredictor {

	public static void main(String[] args) throws Exception {
		ZipcodeRangePredictor inputRange = new ZipcodeRangePredictor();

		List<int[]> rangeList = new ArrayList<>();
		Boolean doYouWantToContinue = true;
		char addMore;
		while (doYouWantToContinue) {
			int[] range = new int[2];
			System.out.println("Please enter lower bound");
			Scanner sc = new Scanner(System.in);
			int lowerBound = sc.nextInt();

			if (!inputRange.validate(lowerBound)) {
				System.out.println("please enter a five digit zipcode");
				continue;
			}

			System.out.println("Please enter upper bound");
			int upperBound = sc.nextInt();

			if (!inputRange.validate(upperBound)) {
				System.out.println("please enter a five digit zipcode");
				continue;
			}

			if (lowerBound > upperBound) {
				System.out.println("upper bound must be greater than lower bound. Please try again!");
				continue;
			}
			range[0] = lowerBound;
			range[1] = upperBound;

			rangeList.add(range);
			System.out.println("do you want to add more? (y/n)");
			addMore = sc.next().charAt(0);
			if (addMore == 'y') {
				continue;
			} else {
				doYouWantToContinue = false;
			}
		}

		int[][] rangeArray = new int[rangeList.size()][2];

		for (int i = 0; i < rangeList.size(); i++) {
			rangeArray[i][0] = rangeList.get(i)[0];
			rangeArray[i][1] = rangeList.get(i)[1];
		}
		
		int[][] optimizedRanges = inputRange.getOptimizedRanges(rangeArray);
		
		inputRange.print(optimizedRanges);

	}
	
	/*
	 * Validate upper bound and lower bound input should be in 5 digit only
	 */
	private Boolean validate(int range) {
		if (range < 10000 || range > 99999) {
			return false;
		} else {
			return true;
		}
	}
	
	/*
	 * Get actual range of zipcode
	 */
	private int[][] getOptimizedRanges(int[][] rangesToOptimize) throws ArrayIndexOutOfBoundsException, NullPointerException {

		int[][] newrange = new int[rangesToOptimize.length][];

		boolean[] optimizedRange = new boolean[rangesToOptimize.length];

		int newIndex = 0;

		this.sortRange(rangesToOptimize);

		for (int i = 0; i < rangesToOptimize.length; i++) {

			if (optimizedRange[i])
				continue;

			if (i == (rangesToOptimize.length - 1)) {
				newrange[newIndex] = rangesToOptimize[i];
				break;
			}

			int min = rangesToOptimize[i][0]; 
			int max = rangesToOptimize[i][1]; 

			int nextMin = rangesToOptimize[i + 1][0]; 
			int nextMax = rangesToOptimize[i + 1][1]; 

			if (max >= nextMin) {
				optimizedRange[i + 1] = true;
					if(max<=nextMax)
						max=nextMax;
					int count=i+1;
					if(count<rangesToOptimize.length-1)
					{
						nextMin = rangesToOptimize[count + 1][0]; 
						nextMax= rangesToOptimize[count + 1][1];
						while(max>=nextMin &&count<rangesToOptimize.length-1 )
						{
							optimizedRange[count + 1] = true;
							count=count+1;
							if(max<=nextMax)
							{
								max=nextMax;
								if(count<rangesToOptimize.length-1)
								{
									nextMin = rangesToOptimize[count + 1][0]; 
									nextMax= rangesToOptimize[count + 1][1];
								}
									
							}
								
						}
						
					}

					newrange[newIndex] = new int[] { min, max };

			} else {

				newrange[newIndex] = rangesToOptimize[i];
			}

			newIndex++;

		}

		return newrange;

	}
	
	/*
	 * Method for printing the array
	 */
	private void print(int[][] outputRange) {

		if (outputRange == null || outputRange.length <= 0)
			throw new IllegalArgumentException("range is null");

		for (int i = 0; i < outputRange.length; i++) {

			if (outputRange[i] != null) {

				System.out.println("[" + outputRange[i][0] + ", " + outputRange[i][1] + "]");
			}
		}
	}

	/*
	 * Sorting Method
	 */
	private void sortRange(int[][] range) {

		Arrays.sort(range, new Comparator<int[]>() {

			@Override
			public int compare(int[] range1, int[] range2) {

				int range1Min = range1[0];
				int range2Min = range2[0];

				if (range1Min > range2Min)
					return 1;

				if (range1Min < range2Min)
					return -1;

				return 0;
			}

		});
	}
}
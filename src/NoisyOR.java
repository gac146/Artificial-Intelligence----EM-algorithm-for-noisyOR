import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * Author: Gustavo Carbone
 * Date: 05/21/2017
 */

public class NoisyOR {
	
	private static final int T = 267;
	private static final int n = 23;
	private static Matrix matrix = new Matrix();
	private static double[] pi = new double[n];
	private static double[] oldPi = new double[n];
	private static int[] tSubi;
	private static int[] yValues;

	
	public static void main(String[] args) {
		//loading values of y
		yValues = read("hw5_noisyOr_y.txt");
		
		//getting Ti for all Xi
		tSubi = getTsubI();
		
		//Initializing Pis
		for(int i=0; i<n; i++) {
			oldPi[i] = pi[i] = 1 / n;			
		}
		
		//Calculating Pi for all Xi
		for(int counter=0; counter < 512; counter++) {
			//temp array to hold new Pis
			double[] tmpPi = new double[n];
			
			//Calculating new Pi for all Xi
			for(int xi=0; xi < n; xi++) {
				double sum = 0;
				double denominator = 0;
				double numerator = 0;
				
				//Summing over T for all Xi
				for(int rowNumber=0; rowNumber < T; rowNumber++) {
					Xnode tmpNode = matrix.getXnode(rowNumber, xi);
					numerator = yValues[rowNumber]*tmpNode.getValue()*oldPi[xi];
					denominator = denominator(rowNumber);
					sum += (numerator/denominator);		
				}				
				tmpPi[xi] = (1/tSubi[xi]) * sum;
			}
			//Updating Pis
			oldPi = pi;
			pi = tmpPi;			
		}	
	}
	
	private static double denominator(int rowNumber) {
		double denom = 1;
		
		for(int i=0; i < n; i++) {
			Xnode temp = matrix.getXnode(rowNumber, i);
			if(temp.getValue() == 1)
				denom *= (1 - oldPi[i]);
		}
		
		return (1 - denom);
	}
	
	/**
	 * Calculates the Ti for all Xi
	 * @return array with Ti values for node 1 to n
	 */
	private static int[] getTsubI() {
		//Getting Ti for all Xi
		int[] temp = new int[n];
		
		for( int i=0; i < n; i++) {
			int ti = 0;
			for(int j=0; j < T; j++) {
				Xnode curr = matrix.getXnode(j, i);
				ti += curr.getValue();
			}			
			temp[i] = ti;
		}
		
		return temp;
	}
	
	/**
	 * Loads the values to yNodes from the data files
	 * 
	 * @param file - name of file to read from
	 * @return array of integers with y values
	 */
	private static int[] read(String file) {
		int counter = 0;
		int[] yVals = new int[T];
		Scanner reader = null;
		
		try {
			reader = new Scanner(new File(file));
		}  catch( FileNotFoundException a) {
			System.out.println("File not found");
		} 
		
		while(reader.hasNextInt()) {
			yVals[counter] = reader.nextInt();
			counter++;
		}
		
		return yVals;
	}
}

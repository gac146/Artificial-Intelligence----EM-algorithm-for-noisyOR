import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

/*
 * Author: Gustavo Carbone
 * Date: 05/21/2017
 */

public class NoisyOR {
	
	private static final double T = 267;
	private static final double n = 23;
	private static Matrix matrix = new Matrix();
	private static double[] pi = new double[(int)n];
	private static double[] tSubi;
	private static double[] yValues;

	
	public static void main(String[] args) {
		//loading values of y
		yValues = read("hw5_noisyOr_y.txt");
		
		//getting Ti for all Xi
		tSubi = getTsubI();
		
		//Initializing Pis
		for(int i=0; i<n; i++) {
			pi[i] = 1.0/n;			
		}
		
		//Calculating Pi for all Xi
		for(int counter=0; counter < 513; counter++) {
			//temp array to hold new Pis
			double[] tmpPi = new double[(int)n];
			System.out.print("iteratio " + counter + " = " );
			computeLogLikelihood();
			
			//Calculating new Pi for all Xi
			for(int xi=0; xi < n; xi++) {
				double sum = 0;
				double denominator = 0;
				double numerator = 0;
				
				//Summing over T for all Xi
				for(int rowNumber=0; rowNumber < T; rowNumber++) {
					Xnode tmpNode = matrix.getXnode(rowNumber, xi);
					numerator = yValues[rowNumber] * tmpNode.getValue() * pi[xi];
					denominator = denominator(rowNumber);
					sum += (numerator/denominator);		
				}				
				tmpPi[xi] = (1.0/tSubi[xi]) * sum;
			}
			//Updating Pis
			pi = tmpPi;			
		}	
	}
	
	private static void computeLogLikelihood() {
		
		double likelihood = 0;
		int yVal = 0;
		
		for(int row=0; row<T; row++) {
			if(yValues[row] == 1 ) likelihood += Math.log(denominator(row));
			else likelihood += Math.log( (1.0 - denominator(row)) );
		}
		
		likelihood = (1.0/T)*likelihood;
		System.out.println( new DecimalFormat("#0.0000").format(likelihood));
	}
	
	/**
	 * 
	 * @param rowNumber
	 * @return
	 */
	private static double denominator(int rowNumber) {
		double denom = 1;
		
		for(int i=0; i < n; i++) {
			Xnode temp = matrix.getXnode(rowNumber, i);
			if(temp.getValue() == 1) { 
				denom *= (1.0 - pi[i]);
			}
			
		}		
		return (1.0 - denom);
	}
	
	/**
	 * Calculates the Ti for all Xi
	 * @return array with Ti values for node 1 to n
	 */
	private static double[] getTsubI() {
		//Getting Ti for all Xi
		double[] temp = new double[(int)n];
		
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
	private static double[] read(String file) {
		int counter = 0;
		double[] yVals = new double[(int)T];
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

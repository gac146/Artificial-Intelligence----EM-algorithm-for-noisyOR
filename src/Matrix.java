import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * Author: Gustavo Carbone
 * Date: 05/21/2017
 */

public class Matrix {
	
	private static int rows = 267;
	private static int columns = 23;
	private Xnode[][] matrix = new Xnode[rows][columns];
	
	/**
	 * Constructor - loading the matrix with all nodes from i = 1 to n = 23
	 */
	public Matrix() {
		Scanner col = null;
		int i = 0;
		int j = 0;
		
		try {
			col = new Scanner(new File("hw5_noisyOr_x.txt"));			
		} catch( FileNotFoundException a) {
			System.out.println("File not found");
		} 
		
		while(col.hasNextLine()) {
			Scanner row = new Scanner(col.nextLine());
			while(row.hasNext()) {
				int xVal = Integer.parseInt(row.next());
				double prob =  1 / columns;  
				matrix[i][j % columns] = new Xnode(xVal, prob, prob);
				j++;
			}
			i++;
		}
	}
	
	/**
	 * Returns the node at the specified row x column
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public Xnode getXnode(int row, int col) {
		return matrix[row][col];
	}
}

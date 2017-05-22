/*
 * Author: Gustavo Carbone
 * Date: 05/21/2017
 */


public class Xnode {
	
	private double xValue;
	private double currProb;
	private double oldProb;
	
	/**
	 * Constructor - Initialize node 
	 * @param xVal where xVal = 0 or 1
	 * @param newProb - updated probability
	 * @param oldProb - old probability
	 */
	public Xnode(int xVal, double newProb, double oldProb) {
		this.xValue = xVal;
		this.currProb = newProb;
		this.oldProb = oldProb;
	}
	
	/**
	 * @return value of current node
	 */
	public double getValue() {
		return this.xValue;
	}
	
	/**
	 * @return current probability of node
	 */
	public double getCurrProb() {
		return this.currProb;
	}
	
	/**
	 * @return old probability of node
	 */
	public double getOldProb() {
		return this.oldProb;
	}
	
	/**
	 * Sets current probability with a newly calculated probability
	 * @param newProb
	 */
	public void setCurrProb(double newProb) {
		this.currProb = newProb;
	}
	
	/**
	 * Sets old probability with a current probability
	 * @param newProb
	 */
	public void setOldProb() {
		this.oldProb = this.currProb;
	}	
	
}

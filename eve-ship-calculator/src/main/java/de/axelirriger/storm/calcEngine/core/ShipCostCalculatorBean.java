package de.axelirriger.storm.calcEngine.core;

public class ShipCostCalculatorBean {
	double costSum = 0d;

	double costTritanium = 0;
	double costMexallon = 0;
	double costIsogen = 0;
	double costNocxium = 0;
	double costZydrine = 0;
	double costMegacyte=0;
	double costPyerite=0;

	public final void calculateShipCost() {
		costSum = costTritanium+costMexallon+costIsogen+costNocxium+costZydrine+costMegacyte+costPyerite;
	}

	/**
	 * 
	 * @param cost
	 */
	public final void setTritaniumCost(final double cost) {
		costTritanium = cost;
	}

	/**
	 * 
	 * @param cost
	 */
	public final void setMexallonCost(final double cost) {
		costMexallon = cost;
	}

	/**
	 * 
	 * @param cost
	 */
	public final void setIsogenCost(final double cost) {
		costIsogen = cost;
	}

	/**
	 * 
	 * @param cost
	 */
	public final void setNocxiumCost(final double cost) {
		costNocxium = cost;
	}

	/**
	 * 
	 * @param cost
	 */
	public final void setZydrineCost(final double cost) {
		costZydrine = cost;
	}

	/**
	 * 
	 * @return
	 */
	public double getShipCost() {
		return costSum;
	}

	/**
	 * 
	 * @param cost
	 */
	public void setMegacyteCost(double cost) {
		costMegacyte = cost;
	}
	
	/**
	 * 
	 * @param cost
	 */
	public void setPyeriteCost(double cost) {
		costPyerite = cost;
	}
}

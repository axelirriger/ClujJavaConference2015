package de.axelirriger.storm.calcEngine.core;

/**
 * This class calculates the cost of a ship.
 * 
 * @author irrigera
 *
 */
public class ShipCostCalculatorBean {
	/**
	 * The ship cost
	 */
	double costSum = 0d;

	/**
	 * The cost for Tritanium
	 */
	double costTritanium = 0;

	/**
	 * The cost for Mexallon
	 */
	double costMexallon = 0;

	/**
	 * The cost for Isogen
	 */
	double costIsogen = 0;

	/**
	 * The cost for Nocxium
	 */
	double costNocxium = 0;

	/**
	 * The cost for Zydrine
	 */
	double costZydrine = 0;

	/**
	 * The cost for Megacyte
	 */
	double costMegacyte = 0;

	/**
	 * The cost for Pyerite
	 */
	double costPyerite = 0;

	/**
	 * This method computes the ship cost, based on partial costs
	 */
	public final void calculateShipCost() {
		costSum = costTritanium + costMexallon + costIsogen + costNocxium + costZydrine + costMegacyte + costPyerite;
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

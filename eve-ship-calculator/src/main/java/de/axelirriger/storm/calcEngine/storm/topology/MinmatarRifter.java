package de.axelirriger.storm.calcEngine.storm.topology;

import backtype.storm.topology.IBasicBolt;
import de.axelirriger.storm.calcEngine.storm.bolts.ShipCostCalculatorBolt;

/**
 * This class creates the ship cost bolt for Minmatar Rifter frigate.
 * 
 * @author irrigera
 *
 */
public class MinmatarRifter extends AbstractShipTopologyBuilder {

	static {
		// Set the ship name to listen to
		SHIP = "minmatar-rifter";
	}

	/*
	 * (non-Javadoc)
	 * @see de.axelirriger.storm.calcEngine.storm.topology.AbstractShipTopologyBuilder#createShipCostCalculator()
	 */
	@Override
	protected IBasicBolt createShipCostCalculator() {
		final IBasicBolt shipBolt = new ShipCostCalculatorBolt(SHIP);
		return shipBolt;
	}

	/*
	 * (non-Javadoc)
	 * @see de.axelirriger.storm.calcEngine.storm.topology.AbstractShipTopologyBuilder#getTritanium()
	 */
	@Override
	public int getTritanium() {
		return 22576;
	}

	/*
	 * (non-Javadoc)
	 * @see de.axelirriger.storm.calcEngine.storm.topology.AbstractShipTopologyBuilder#getMexallon()
	 */
	@Override
	public int getMexallon() {
		return 2025;
	}

	/*
	 * (non-Javadoc)
	 * @see de.axelirriger.storm.calcEngine.storm.topology.AbstractShipTopologyBuilder#getIsogen()
	 */
	@Override
	public int getIsogen() {
		return 349;
	}

	/*
	 * (non-Javadoc)
	 * @see de.axelirriger.storm.calcEngine.storm.topology.AbstractShipTopologyBuilder#getNocxium()
	 */
	@Override
	public int getNocxium() {
		return 130;
	}

	/*
	 * (non-Javadoc)
	 * @see de.axelirriger.storm.calcEngine.storm.topology.AbstractShipTopologyBuilder#getZydrine()
	 */
	@Override
	public int getZydrine() {
		return 14;
	}

	/*
	 * (non-Javadoc)
	 * @see de.axelirriger.storm.calcEngine.storm.topology.AbstractShipTopologyBuilder#getMegacyte()
	 */
	@Override
	public int getMegacyte() {
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * @see de.axelirriger.storm.calcEngine.storm.topology.AbstractShipTopologyBuilder#getPyerite()
	 */
	@Override
	public int getPyerite() {
		return 6082;
	}

}

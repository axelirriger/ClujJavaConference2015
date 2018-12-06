package de.axelirriger.storm.calcEngine.storm.topology;

import org.apache.storm.topology.IBasicBolt;

import de.axelirriger.storm.calcEngine.storm.bolts.ShipCostCalculatorBolt;

/**
 * This class creates the ship cost bolt for Gallente Imicus frigate.
 * 
 * @author irrigera
 *
 */
public class GallenteImicus extends AbstractShipTopologyBuilder {

	static {
		// Set the ship name to listen to 
		SHIP = "gallente-imicus";
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
		return 7292;
	}

	/*
	 * (non-Javadoc)
	 * @see de.axelirriger.storm.calcEngine.storm.topology.AbstractShipTopologyBuilder#getMexallon()
	 */
	@Override
	public int getMexallon() {
		return 1425;
	}

	/*
	 * (non-Javadoc)
	 * @see de.axelirriger.storm.calcEngine.storm.topology.AbstractShipTopologyBuilder#getIsogen()
	 */
	@Override
	public int getIsogen() {
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * @see de.axelirriger.storm.calcEngine.storm.topology.AbstractShipTopologyBuilder#getNocxium()
	 */
	@Override
	public int getNocxium() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see de.axelirriger.storm.calcEngine.storm.topology.AbstractShipTopologyBuilder#getZydrine()
	 */
	@Override
	public int getZydrine() {
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * @see de.axelirriger.storm.calcEngine.storm.topology.AbstractShipTopologyBuilder#getMegacyte()
	 */
	@Override
	public int getMegacyte() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see de.axelirriger.storm.calcEngine.storm.topology.AbstractShipTopologyBuilder#getPyerite()
	 */
	@Override
	public int getPyerite() {
		return 5964;
	}

}

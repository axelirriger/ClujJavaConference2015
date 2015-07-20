package de.axelirriger.storm.calcEngine.storm.topology;

import backtype.storm.topology.IBasicBolt;
import de.axelirriger.storm.calcEngine.storm.bolts.AbstractCostCalculatorBolt;
import de.axelirriger.storm.calcEngine.storm.bolts.RifterCalculatorBolt;

public class Rifter extends AbstractShipTopologyBuilder {

	static {
		SHIP = "rifter";
	}

	protected int tritanium = 22576;
	protected int mexallon = 2025;
	protected int isogen = 349;
	protected int nocxium = 130;
	protected int zydrine = 14;
	protected int megacyte = 1;
	protected int pyerite = 6082;

	@Override
	protected IBasicBolt createShipCostCalculator() {
		final IBasicBolt shipBolt = new RifterCalculatorBolt();
		return shipBolt;
	}

}

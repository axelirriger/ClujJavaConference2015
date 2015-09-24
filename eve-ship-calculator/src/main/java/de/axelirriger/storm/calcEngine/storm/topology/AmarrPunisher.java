package de.axelirriger.storm.calcEngine.storm.topology;

import backtype.storm.topology.IBasicBolt;
import de.axelirriger.storm.calcEngine.storm.bolts.KestrelCalculatorBolt;

public class AmarrPunisher extends AbstractShipTopologyBuilder {

	static {
		SHIP = "punisher";
	}

	protected int tritanium = 22570;
	protected int mexallon = 2919;
	protected int isogen = 397;
	protected int nocxium = 87;
	protected int zydrine = 17;
	protected int megacyte = 0;
	protected int pyerite = 6072;

	@Override
	protected IBasicBolt createShipCostCalculator() {
		final IBasicBolt shipBolt = new KestrelCalculatorBolt();
		return shipBolt;
	}

}

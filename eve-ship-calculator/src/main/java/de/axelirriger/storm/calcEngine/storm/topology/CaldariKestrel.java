package de.axelirriger.storm.calcEngine.storm.topology;

import backtype.storm.topology.IBasicBolt;
import de.axelirriger.storm.calcEngine.storm.bolts.KestrelCalculatorBolt;

public class CaldariKestrel extends AbstractShipTopologyBuilder {

	static {
		SHIP = "kestrel";
	}

	protected int tritanium = 16337;
	protected int mexallon = 2837;
	protected int isogen = 947;
	protected int nocxium = 1;
	protected int zydrine = 1;
	protected int megacyte = 0;
	protected int pyerite = 0;

	@Override
	protected IBasicBolt createShipCostCalculator() {
		final IBasicBolt shipBolt = new KestrelCalculatorBolt();
		return shipBolt;
	}

}

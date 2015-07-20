package de.axelirriger.storm.calcEngine.storm.topology;

import backtype.storm.topology.BoltDeclarer;
import backtype.storm.topology.IBasicBolt;
import backtype.storm.topology.TopologyBuilder;
import de.axelirriger.storm.calcEngine.CalculateTopology;
import de.axelirriger.storm.calcEngine.storm.bolts.AbstractCostCalculatorBolt;
import de.axelirriger.storm.calcEngine.storm.bolts.ShipMaterialCostCalculatorBolt;

public abstract class AbstractShipTopologyBuilder {

	protected static String SHIP = "kestrel";

	private String BOLT_SHIP_ZYDRINE = SHIP + "-zydrine";
	private String BOLT_SHIP_TRITANIUM = SHIP + "-tritanium";
	private String BOLT_SHIP_MEXALLON = SHIP + "-mexallon";
	private String BOLT_SHIP_ISOGEN = SHIP + "-isogen";
	private String BOLT_SHIP_NOCXIUM = SHIP + "-nocxium";
	private String BOLT_SHIP_MEGACYTE = SHIP + "-megacyte";
	private String BOLT_SHIP_PYERITE = SHIP + "-pyerite";

	protected int tritanium = 16337;
	protected int mexallon = 2837;
	protected int isogen = 947;
	protected int nocxium = 1;
	protected int zydrine = 1;
	protected int megacyte = 0;
	protected int pyerite = 0;

	/**
	 * 
	 * @param builder
	 */
	public void createTopology(TopologyBuilder builder) {
		final IBasicBolt kestrelTritanium = new ShipMaterialCostCalculatorBolt(BOLT_SHIP_TRITANIUM, tritanium);
		final IBasicBolt kestrelMexallon = new ShipMaterialCostCalculatorBolt(BOLT_SHIP_MEXALLON, mexallon);
		final IBasicBolt kestrelIsogen = new ShipMaterialCostCalculatorBolt(BOLT_SHIP_ISOGEN, isogen);
		final IBasicBolt kestrelNocxium = new ShipMaterialCostCalculatorBolt(BOLT_SHIP_NOCXIUM, nocxium);
		final IBasicBolt kestrelZydrine = new ShipMaterialCostCalculatorBolt(BOLT_SHIP_ZYDRINE, zydrine);
		final IBasicBolt kestrelMegacyte = new ShipMaterialCostCalculatorBolt(BOLT_SHIP_MEGACYTE, megacyte);
		final IBasicBolt kestrelPyerite = new ShipMaterialCostCalculatorBolt(BOLT_SHIP_PYERITE, pyerite);

		builder.setBolt(BOLT_SHIP_TRITANIUM, kestrelTritanium, 1).shuffleGrouping(CalculateTopology.BOLT_MATERIAL_SPLITTER,"tritanium");
		builder.setBolt(BOLT_SHIP_MEXALLON, kestrelMexallon, 1).shuffleGrouping(CalculateTopology.BOLT_MATERIAL_SPLITTER, "mexallon");
		builder.setBolt(BOLT_SHIP_ISOGEN, kestrelIsogen, 1).shuffleGrouping(CalculateTopology.BOLT_MATERIAL_SPLITTER, "isogen");
		builder.setBolt(BOLT_SHIP_NOCXIUM, kestrelNocxium, 1).shuffleGrouping(CalculateTopology.BOLT_MATERIAL_SPLITTER, "nocxium");
		builder.setBolt(BOLT_SHIP_ZYDRINE, kestrelZydrine, 1).shuffleGrouping(CalculateTopology.BOLT_MATERIAL_SPLITTER, "zydrine");
		builder.setBolt(BOLT_SHIP_MEGACYTE, kestrelMegacyte, 1).shuffleGrouping(CalculateTopology.BOLT_MATERIAL_SPLITTER, "megacyte");
		builder.setBolt(BOLT_SHIP_PYERITE, kestrelPyerite, 1).shuffleGrouping(CalculateTopology.BOLT_MATERIAL_SPLITTER, "pyerite");

		final IBasicBolt shipBolt = createShipCostCalculator();
		final BoltDeclarer shipBoltDeclarer = builder.setBolt(SHIP, shipBolt, 1);
		shipBoltDeclarer.shuffleGrouping(BOLT_SHIP_TRITANIUM);
		shipBoltDeclarer.shuffleGrouping(BOLT_SHIP_MEXALLON);
		shipBoltDeclarer.shuffleGrouping(BOLT_SHIP_ISOGEN);
		shipBoltDeclarer.shuffleGrouping(BOLT_SHIP_NOCXIUM);
		shipBoltDeclarer.shuffleGrouping(BOLT_SHIP_ZYDRINE);
		shipBoltDeclarer.shuffleGrouping(BOLT_SHIP_MEGACYTE);
		shipBoltDeclarer.shuffleGrouping(BOLT_SHIP_PYERITE);
	}

	protected abstract IBasicBolt createShipCostCalculator();

}

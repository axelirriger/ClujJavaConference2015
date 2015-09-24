package de.axelirriger.storm.calcEngine.storm.topology;

import backtype.storm.topology.BoltDeclarer;
import backtype.storm.topology.IBasicBolt;
import backtype.storm.topology.TopologyBuilder;
import de.axelirriger.storm.calcEngine.CalculateTopology;
import de.axelirriger.storm.calcEngine.storm.bolts.AbstractCostCalculatorBolt;
import de.axelirriger.storm.calcEngine.storm.bolts.ShipMaterialCostCalculatorBolt;

public abstract class AbstractShipTopologyBuilder {

	protected static String SHIP = "--";

	private String BOLT_SHIP_ZYDRINE = SHIP + "-zydrine";
	private String BOLT_SHIP_TRITANIUM = SHIP + "-tritanium";
	private String BOLT_SHIP_MEXALLON = SHIP + "-mexallon";
	private String BOLT_SHIP_ISOGEN = SHIP + "-isogen";
	private String BOLT_SHIP_NOCXIUM = SHIP + "-nocxium";
	private String BOLT_SHIP_MEGACYTE = SHIP + "-megacyte";
	private String BOLT_SHIP_PYERITE = SHIP + "-pyerite";

	protected int tritanium = 0;
	protected int mexallon = 0;
	protected int isogen = 0;
	protected int nocxium = 0;
	protected int zydrine = 0;
	protected int megacyte = 0;
	protected int pyerite = 0;

	/**
	 * 
	 * @param builder
	 */
	public void createTopology(TopologyBuilder builder) {
		final IBasicBolt shipTritanium = new ShipMaterialCostCalculatorBolt(BOLT_SHIP_TRITANIUM, tritanium);
		final IBasicBolt shipMexallon = new ShipMaterialCostCalculatorBolt(BOLT_SHIP_MEXALLON, mexallon);
		final IBasicBolt shipIsogen = new ShipMaterialCostCalculatorBolt(BOLT_SHIP_ISOGEN, isogen);
		final IBasicBolt shipNocxium = new ShipMaterialCostCalculatorBolt(BOLT_SHIP_NOCXIUM, nocxium);
		final IBasicBolt shipZydrine = new ShipMaterialCostCalculatorBolt(BOLT_SHIP_ZYDRINE, zydrine);
		final IBasicBolt shipMegacyte = new ShipMaterialCostCalculatorBolt(BOLT_SHIP_MEGACYTE, megacyte);
		final IBasicBolt shipPyerite = new ShipMaterialCostCalculatorBolt(BOLT_SHIP_PYERITE, pyerite);

		builder.setBolt(BOLT_SHIP_TRITANIUM, shipTritanium, 1).shuffleGrouping(CalculateTopology.BOLT_MATERIAL_SPLITTER,"tritanium");
		builder.setBolt(BOLT_SHIP_MEXALLON, shipMexallon, 1).shuffleGrouping(CalculateTopology.BOLT_MATERIAL_SPLITTER, "mexallon");
		builder.setBolt(BOLT_SHIP_ISOGEN, shipIsogen, 1).shuffleGrouping(CalculateTopology.BOLT_MATERIAL_SPLITTER, "isogen");
		builder.setBolt(BOLT_SHIP_NOCXIUM, shipNocxium, 1).shuffleGrouping(CalculateTopology.BOLT_MATERIAL_SPLITTER, "nocxium");
		builder.setBolt(BOLT_SHIP_ZYDRINE, shipZydrine, 1).shuffleGrouping(CalculateTopology.BOLT_MATERIAL_SPLITTER, "zydrine");
		builder.setBolt(BOLT_SHIP_MEGACYTE, shipMegacyte, 1).shuffleGrouping(CalculateTopology.BOLT_MATERIAL_SPLITTER, "megacyte");
		builder.setBolt(BOLT_SHIP_PYERITE, shipPyerite, 1).shuffleGrouping(CalculateTopology.BOLT_MATERIAL_SPLITTER, "pyerite");

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

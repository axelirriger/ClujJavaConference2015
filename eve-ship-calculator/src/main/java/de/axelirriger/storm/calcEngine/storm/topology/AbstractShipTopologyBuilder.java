package de.axelirriger.storm.calcEngine.storm.topology;

import backtype.storm.topology.BoltDeclarer;
import backtype.storm.topology.IBasicBolt;
import backtype.storm.topology.TopologyBuilder;
import de.axelirriger.storm.calcEngine.CalculateTopology;
import de.axelirriger.storm.calcEngine.storm.bolts.ShipMaterialCostCalculatorBolt;

/**
 * This class creates the (partial) topology, to calculate a ship component
 * cost. It does this by creating a bolt for each ship-material combination (see
 * instance variables for how it's done). Each bolt is then connected to the
 * material router bolt.
 * 
 * @author irrigera
 *
 */
public abstract class AbstractShipTopologyBuilder {

	/**
	 * Each ship this builder should create a partial topology for, is uniquely
	 * identified by a prefix
	 */
	protected static String SHIP = "--";

	/**
	 * The Zydrine part of the ship cost
	 */
	private String BOLT_SHIP_ZYDRINE = SHIP + "-zydrine";

	/**
	 * The Tritanium part of the ship cost
	 */
	private String BOLT_SHIP_TRITANIUM = SHIP + "-tritanium";

	/**
	 * The Mexallon part of the ship cost
	 */
	private String BOLT_SHIP_MEXALLON = SHIP + "-mexallon";

	/**
	 * The Isogen part of the ship cost
	 */
	private String BOLT_SHIP_ISOGEN = SHIP + "-isogen";

	/**
	 * The Noxcium part of the ship cost
	 */
	private String BOLT_SHIP_NOCXIUM = SHIP + "-nocxium";

	/**
	 * The Megacyte part of the ship cost
	 */
	private String BOLT_SHIP_MEGACYTE = SHIP + "-megacyte";

	/**
	 * The Pyerite part of the ship cost
	 */
	private String BOLT_SHIP_PYERITE = SHIP + "-pyerite";

	/**
	 * @return The amount of Tritanium needed for the specific ship type
	 */
	public abstract int getTritanium();

	/**
	 * @return The amount of Mexallon needed for the specific ship type
	 */
	public abstract int getMexallon();

	/**
	 * @return The amount of Isogen needed for the specific ship type
	 */
	public abstract int getIsogen();

	/**
	 * @return The amount of Nocxium needed for the specific ship type
	 */
	public abstract int getNocxium();

	/**
	 * @return The amount of Zydrine needed for the specific ship type
	 */
	public abstract int getZydrine();

	/**
	 * @return The amount of Megacyte needed for the specific ship type
	 */
	public abstract int getMegacyte();

	/**
	 * @return The amount of Pyerite needed for the specific ship type
	 */
	public abstract int getPyerite();

	/**
	 * This method creates the (partial) topology, to calculate a ship component
	 * cost. It does this by creating a bolt for each ship-material combination
	 * (see instance variables for how it's done). Each bolt is then connected
	 * to the material router bolt.
	 * 
	 * @see {@link CalculateTopology#createMaterialSplitter(TopologyBuilder)}
	 * @param builder
	 */
	public void createTopology(TopologyBuilder builder) {
		/*
		 * For each ship component, create the corresponding bolt instance
		 */
		final IBasicBolt shipTritanium = new ShipMaterialCostCalculatorBolt(BOLT_SHIP_TRITANIUM, getTritanium());
		final IBasicBolt shipMexallon = new ShipMaterialCostCalculatorBolt(BOLT_SHIP_MEXALLON, getMexallon());
		final IBasicBolt shipIsogen = new ShipMaterialCostCalculatorBolt(BOLT_SHIP_ISOGEN, getIsogen());
		final IBasicBolt shipNocxium = new ShipMaterialCostCalculatorBolt(BOLT_SHIP_NOCXIUM, getNocxium());
		final IBasicBolt shipZydrine = new ShipMaterialCostCalculatorBolt(BOLT_SHIP_ZYDRINE, getZydrine());
		final IBasicBolt shipMegacyte = new ShipMaterialCostCalculatorBolt(BOLT_SHIP_MEGACYTE, getMegacyte());
		final IBasicBolt shipPyerite = new ShipMaterialCostCalculatorBolt(BOLT_SHIP_PYERITE, getPyerite());

		/*
		 * Put the created bolts to the topology and connect them to the
		 * material router. Let them read from the corresponding material
		 * stream. Also, it's unimportant which bolt instance gets which tuple,
		 * so shuffleGrouping (=round-robin) is fine.
		 */
		int numberOfExecutors = 1;
		builder.setBolt(BOLT_SHIP_TRITANIUM, shipTritanium, numberOfExecutors)
				.shuffleGrouping(CalculateTopology.BOLT_MATERIAL_SPLITTER, "tritanium");
		builder.setBolt(BOLT_SHIP_MEXALLON, shipMexallon, numberOfExecutors)
				.shuffleGrouping(CalculateTopology.BOLT_MATERIAL_SPLITTER, "mexallon");
		builder.setBolt(BOLT_SHIP_ISOGEN, shipIsogen, numberOfExecutors)
				.shuffleGrouping(CalculateTopology.BOLT_MATERIAL_SPLITTER, "isogen");
		builder.setBolt(BOLT_SHIP_NOCXIUM, shipNocxium, numberOfExecutors)
				.shuffleGrouping(CalculateTopology.BOLT_MATERIAL_SPLITTER, "nocxium");
		builder.setBolt(BOLT_SHIP_ZYDRINE, shipZydrine, numberOfExecutors)
				.shuffleGrouping(CalculateTopology.BOLT_MATERIAL_SPLITTER, "zydrine");
		builder.setBolt(BOLT_SHIP_MEGACYTE, shipMegacyte, numberOfExecutors)
				.shuffleGrouping(CalculateTopology.BOLT_MATERIAL_SPLITTER, "megacyte");
		builder.setBolt(BOLT_SHIP_PYERITE, shipPyerite, numberOfExecutors)
				.shuffleGrouping(CalculateTopology.BOLT_MATERIAL_SPLITTER, "pyerite");

		/*
		 * For the specific ship, create the bolt to calculate its overall cost.
		 * Add the bolt to the topology and connect it to each material cost
		 * stream for that ship. Since we don't want parallel prices to be
		 * calculated, we only need one bolt instance
		 */
		final IBasicBolt shipBolt = createShipCostCalculator();
		final BoltDeclarer shipBoltDeclarer = builder.setBolt(SHIP, shipBolt, numberOfExecutors);
		shipBoltDeclarer.shuffleGrouping(BOLT_SHIP_TRITANIUM);
		shipBoltDeclarer.shuffleGrouping(BOLT_SHIP_MEXALLON);
		shipBoltDeclarer.shuffleGrouping(BOLT_SHIP_ISOGEN);
		shipBoltDeclarer.shuffleGrouping(BOLT_SHIP_NOCXIUM);
		shipBoltDeclarer.shuffleGrouping(BOLT_SHIP_ZYDRINE);
		shipBoltDeclarer.shuffleGrouping(BOLT_SHIP_MEGACYTE);
		shipBoltDeclarer.shuffleGrouping(BOLT_SHIP_PYERITE);
	}

	/**
	 * 
	 * @return The bolt to create a specific ship cost
	 */
	protected abstract IBasicBolt createShipCostCalculator();

}

package de.axelirriger.storm.calcEngine.storm.bolts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import de.axelirriger.storm.calcEngine.core.ShipCostCalculatorBean;

/**
 * 
 * This class reads updated price tuples for each material recalculates the ship
 * prices.
 * 
 * @author irrigera
 *
 */
public class ShipCostCalculatorBolt extends BaseBasicBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6100515822704546120L;

	/**
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ShipCostCalculatorBolt.class);

	/**
	 * Bean to handle computation
	 */
	private transient ShipCostCalculatorBean calcBean;

	/**
	 * The ship to listen to
	 */
	protected String shipPrefix = "--";

	/**
	 * 
	 * @param targetShipPrefix
	 *            The prefix of the ship to calculate the price for
	 */
	public ShipCostCalculatorBolt(final String targetShipPrefix) {
		this.shipPrefix = targetShipPrefix.toLowerCase();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * backtype.storm.topology.IBasicBolt#execute(backtype.storm.tuple.Tuple,
	 * backtype.storm.topology.BasicOutputCollector)
	 */
	@Override
	public void execute(final Tuple arg0, final BasicOutputCollector arg1) {
		final String material = arg0.getStringByField("ship-material");
		final double cost = arg0.getDoubleByField("cost");

		// Support lazy initialization
		if (calcBean == null)
			calcBean = new ShipCostCalculatorBean();

		if ((shipPrefix + "-tritanium").equals(material))
			calcBean.setTritaniumCost(cost);
		else if ((shipPrefix + "-mexallon").equals(material))
			calcBean.setMexallonCost(cost);
		else if ((shipPrefix + "-isogen").equals(material))
			calcBean.setIsogenCost(cost);
		else if ((shipPrefix + "-nocxium").equals(material))
			calcBean.setNocxiumCost(cost);
		else if ((shipPrefix + "-zydrine").equals(material))
			calcBean.setZydrineCost(cost);
		else if ((shipPrefix + "-megacyte").equals(material))
			calcBean.setMegacyteCost(cost);
		else if ((shipPrefix + "-pyerite").equals(material))
			calcBean.setPyeriteCost(cost);

		// Recalculate the ship cost
		calcBean.calculateShipCost();

		if (LOG.isErrorEnabled()) {
			LOG.error(shipPrefix + " ship price " + calcBean.getShipCost() + " ISK");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * backtype.storm.topology.IComponent#declareOutputFields(backtype.storm.
	 * topology.OutputFieldsDeclarer)
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// Nothing to do here, as this is the sink of the topology
	}

}

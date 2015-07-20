package de.axelirriger.storm.calcEngine.storm.bolts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import de.axelirriger.storm.calcEngine.core.ShipCostCalculatorBean;

@SuppressWarnings("serial")
public abstract class AbstractCostCalculatorBolt extends BaseBasicBolt {

	/**
	 *  Logger 
	 */
	private static final Logger LOG = LoggerFactory.getLogger(AbstractCostCalculatorBolt.class);

	/**
	 *  Bean to handle computation 
	 */
	private transient ShipCostCalculatorBean calcBean;
	
	protected String shipPrefix = "kestrel";
	
	/*
	 * (non-Javadoc)
	 * @see backtype.storm.topology.IBasicBolt#execute(backtype.storm.tuple.Tuple, backtype.storm.topology.BasicOutputCollector)
	 */
	@Override
	public void execute(Tuple arg0, BasicOutputCollector arg1) {
		String material = arg0.getStringByField("ship-material");
		double cost = arg0.getDoubleByField("cost");

		if(calcBean == null)
			calcBean = new ShipCostCalculatorBean();
		
		if((shipPrefix + "-tritanium").equals(material))
			calcBean.setTritaniumCost(cost);
		else if((shipPrefix + "-mexallon").equals(material))
			calcBean.setMexallonCost(cost);
		else if((shipPrefix+"-isogen").equals(material))
			calcBean.setIsogenCost(cost);
		else if((shipPrefix+"-nocxium").equals(material))
			calcBean.setNocxiumCost(cost);
		else if((shipPrefix+"-zydrine").equals(material))
			calcBean.setZydrineCost(cost);
		else if((shipPrefix+"-megacyte").equals(material))
			calcBean.setMegacyteCost(cost);
		else if((shipPrefix+"-pyerite").equals(material))
			calcBean.setPyeriteCost(cost);

		calcBean.calculateShipCost();
				
		LOG.error(shipPrefix + " ship price " + calcBean.getShipCost() + " ISK");
	}
	
	/*
	 * (non-Javadoc)
	 * @see backtype.storm.topology.IComponent#declareOutputFields(backtype.storm.topology.OutputFieldsDeclarer)
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// TODO Auto-generated method stub

	}

}

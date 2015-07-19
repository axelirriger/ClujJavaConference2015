package de.axelirriger.storm.calcEngine.storm.bolts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import de.axelirriger.storm.calcEngine.core.KestrelCostCalculatorBean;

@SuppressWarnings("serial")
public class KestrelCostCalculatorBolt extends BaseBasicBolt {

	/* Logger */
	private static final Logger LOG = LoggerFactory.getLogger(KestrelCostCalculatorBolt.class);

	/* Bean to handle computation */
	private transient KestrelCostCalculatorBean calcBean = new KestrelCostCalculatorBean();
	
	/*
	 * (non-Javadoc)
	 * @see backtype.storm.topology.IBasicBolt#execute(backtype.storm.tuple.Tuple, backtype.storm.topology.BasicOutputCollector)
	 */
	@Override
	public void execute(Tuple arg0, BasicOutputCollector arg1) {
		String material = arg0.getStringByField("ship-material");
		double cost = arg0.getDoubleByField("cost");

		if("kestrel-tritanium".equals(material))
			calcBean.setTritaniumCost(cost);
		else if("kestrel-mexallon".equals(material))
			calcBean.setMexallonCost(cost);
		else if("kestel-isogen".equals(material))
			calcBean.setIsogenCost(cost);
		else if("kestrel-nocxium".equals(material))
			calcBean.setNocxiumCost(cost);
		else if("kestrel-zydrine".equals(material))
			calcBean.setZydrineCost(cost);

		calcBean.calculateShipCost();
		
		LOG.error("Kestrel ship price " + calcBean.getShipCost() + " ISK");
	}

	/*
	 * 
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// TODO Auto-generated method stub

	}

}

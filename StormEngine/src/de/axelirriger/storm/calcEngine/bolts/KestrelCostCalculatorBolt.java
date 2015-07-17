package de.axelirriger.storm.calcEngine.bolts;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

@SuppressWarnings("serial")
public class KestrelCostCalculatorBolt extends BaseBasicBolt {

	Double priceSum = 0d;

	double costTritanium = 0;
	double costMexallon = 0;
	double costIsogen = 0;
	double costNocxium = 0;
	double costZydrine = 0;

	@Override
	public void execute(Tuple arg0, BasicOutputCollector arg1) {
		String material = arg0.getStringByField("ship-material");
		double cost = arg0.getDoubleByField("cost");

		if("kestrel-tritanium".equals(material))
			costTritanium = cost;
		else if("kestrel-mexallon".equals(material))
			costMexallon = cost;
		else if("kestel-isogen".equals(material))
			costIsogen = cost;
		else if("kestrel-nocxium".equals(material))
			costNocxium = cost;
		else if("kestrel-zydrine".equals(material))
			costZydrine = cost;
		
		priceSum = costTritanium+costMexallon+costIsogen+costNocxium+costZydrine;
		
		System.err.println("Kestrel price: " + priceSum + " ISK");
	}

	/*
	 * 
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// TODO Auto-generated method stub

	}

}

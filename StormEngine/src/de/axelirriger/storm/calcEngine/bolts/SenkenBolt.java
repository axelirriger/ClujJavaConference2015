package de.axelirriger.storm.calcEngine.bolts;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

public class SenkenBolt extends BaseBasicBolt {

	Double priceSum = 0d;
	
	@Override
	public void execute(Tuple arg0, BasicOutputCollector arg1) {
		priceSum += arg0.getDoubleByField("price");
		
		System.err.println(priceSum);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// TODO Auto-generated method stub

	}

}

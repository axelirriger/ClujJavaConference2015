package de.axelirriger.storm.calcEngine.bolts;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

@SuppressWarnings("serial")
public class ProcessorBolt extends BaseBasicBolt {

	@Override
	public void execute(Tuple arg0, BasicOutputCollector arg1) {
		double d = arg0.getDoubleByField("price");
		String elementName = arg0.getStringByField("element");
		
		arg1.emit(new Values(elementName, d));
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		arg0.declare(new Fields("element", "price"));
	}

}

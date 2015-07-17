package de.axelirriger.storm.calcEngine.bolts;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

@SuppressWarnings("serial")
public class MaterialSplitterBolt extends BaseRichBolt {

	private OutputCollector outputCollector;

	@Override
	public void execute(Tuple arg0) {
		final String element = arg0.getStringByField("element");

		String outputStream = element.toLowerCase();
		outputCollector.emit(outputStream, arg0.getValues());
	}

	@Override
	public void prepare(@SuppressWarnings("rawtypes") Map arg0, TopologyContext arg1, OutputCollector arg2) {
		this.outputCollector = arg2;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		/* Declare dedicated streams per material */
		arg0.declareStream("tritanium", new Fields("element", "price"));
		arg0.declareStream("mexallon", new Fields("element", "price"));
		arg0.declareStream("isogen", new Fields("element", "price"));
		arg0.declareStream("nocxium", new Fields("element", "price"));
		arg0.declareStream("zydrine", new Fields("element", "price"));
	}

}

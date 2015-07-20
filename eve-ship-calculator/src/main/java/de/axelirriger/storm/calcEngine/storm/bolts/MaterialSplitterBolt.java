package de.axelirriger.storm.calcEngine.storm.bolts;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

@SuppressWarnings("serial")
public class MaterialSplitterBolt extends BaseRichBolt {

	/**
	 * The output collector
	 */
	private transient OutputCollector outputCollector;

	/*
	 * (non-Javadoc)
	 * @see backtype.storm.task.IBolt#execute(backtype.storm.tuple.Tuple)
	 */
	@Override
	public void execute(Tuple arg0) {
		final String element = arg0.getStringByField("element");

		final String outputStream = element.toLowerCase();
		outputCollector.emit(outputStream, arg0.getValues());
	}

	/*
	 * (non-Javadoc)
	 * @see backtype.storm.task.IBolt#prepare(java.util.Map, backtype.storm.task.TopologyContext, backtype.storm.task.OutputCollector)
	 */
	@Override
	public void prepare(@SuppressWarnings("rawtypes") Map arg0, TopologyContext arg1, OutputCollector arg2) {
		this.outputCollector = arg2;
	}

	/*
	 * (non-Javadoc)
	 * @see backtype.storm.topology.IComponent#declareOutputFields(backtype.storm.topology.OutputFieldsDeclarer)
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		/* Declare dedicated streams per material */
		arg0.declareStream("tritanium", new Fields("element", "price"));
		arg0.declareStream("mexallon", new Fields("element", "price"));
		arg0.declareStream("isogen", new Fields("element", "price"));
		arg0.declareStream("nocxium", new Fields("element", "price"));
		arg0.declareStream("zydrine", new Fields("element", "price"));
		arg0.declareStream("megacyte", new Fields("element", "price"));
		arg0.declareStream("pyerite", new Fields("element", "price"));
	}

}

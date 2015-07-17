package de.axelirriger.storm.calcEngine.spouts;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

@SuppressWarnings("serial")
public class QuellenSpout extends BaseRichSpout {

	private SpoutOutputCollector outputCollector;

	private Map<String, Double> priceArray = new HashMap<String, Double>();
	private Iterator<Entry<String, Double>> iterator;
	
	@Override
	public void nextTuple() {
		Utils.sleep(1000);
		Entry<String, Double> item = iterator.next();
		
		outputCollector.emit(new Values(item.getKey(), item.getValue()));
	}

	@Override
	public void open(@SuppressWarnings("rawtypes") Map arg0, TopologyContext arg1, SpoutOutputCollector arg2) {
		this.outputCollector = arg2;
		
		// Prepare some sample data
		priceArray.put("Tritanium", 2.37d);
		priceArray.put("Cyclin", 15.0d);
		priceArray.put("Metalin", 7.8d);
		
		// Save an iterator to be able to easily handle "nextTuple"
		this.iterator = priceArray.entrySet().iterator();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// Two elements will be issued
		arg0.declare(new Fields("element", "price"));
	}

}

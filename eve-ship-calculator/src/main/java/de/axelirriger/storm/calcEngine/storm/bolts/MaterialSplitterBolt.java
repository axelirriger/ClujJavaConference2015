package de.axelirriger.storm.calcEngine.storm.bolts;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

/**
 * This class gets tuples from all material sources and re-emits them to material specific stream.
 * 
 * @author irrigera
 *
 */
public class MaterialSplitterBolt extends BaseRichBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3951334071534818897L;

	/**
	 * The output stream of Pyerite prices
	 */
	public static final String STREAM_PYERITE = "pyerite";
	
	/**
	 * The output stream of Megacyte prices
	 */
	public static final String STREAM_MEGACYTE = "megacyte";
	
	/**
	 * The output stream of Zydrine prices
	 */
	public static final String STREAM_ZYDRINE = "zydrine";
	
	/**
	 * The output stream of Nocxium prices
	 */
	public static final String STREAM_NOCXIUM = "nocxium";
	
	/**
	 * The output stream of Isogen prices
	 */
	public static final String STREAM_ISOGEN = "isogen";
	
	/**
	 * The output stream of Mexallonvprices
	 */
	public static final String STREAM_MEXALLON = "mexallon";
	
	/**
	 * The output stream of Tritanium prices
	 */
	public static final String STREAM_TRITANIUM = "tritanium";

	/**
	 * The output collector
	 */
	private transient OutputCollector outputCollector;

	/*
	 * (non-Javadoc)
	 * @see backtype.storm.task.IBolt#execute(backtype.storm.tuple.Tuple)
	 */
	@Override
	public void execute(final Tuple arg0) {
		// Get the material name from the input tuple ...
		final String element = arg0.getStringByField("element");

		// ... and emit it the material specific output stream
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
	public void declareOutputFields(final OutputFieldsDeclarer arg0) {
		 //  Declare the tuple output declaration
		final Fields outputTupleDeclaration = new Fields("element", "price");
		
		/* 
		 * Declare dedicated streams per material
		 */
		arg0.declareStream(STREAM_TRITANIUM, outputTupleDeclaration);
		arg0.declareStream(STREAM_MEXALLON, outputTupleDeclaration);
		arg0.declareStream(STREAM_ISOGEN, outputTupleDeclaration);
		arg0.declareStream(STREAM_NOCXIUM, outputTupleDeclaration);
		arg0.declareStream(STREAM_ZYDRINE, outputTupleDeclaration);
		arg0.declareStream(STREAM_MEGACYTE, outputTupleDeclaration);
		arg0.declareStream(STREAM_PYERITE, outputTupleDeclaration);
	}

}

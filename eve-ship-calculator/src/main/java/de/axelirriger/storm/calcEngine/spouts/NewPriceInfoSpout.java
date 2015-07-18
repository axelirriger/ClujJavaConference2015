package de.axelirriger.storm.calcEngine.spouts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

@SuppressWarnings("serial")
public class NewPriceInfoSpout extends BaseRichSpout {

	/* Logger */
	private static final Logger LOG = LoggerFactory.getLogger(NewPriceInfoSpout.class);
	
	/* Output emitter */
	private transient SpoutOutputCollector outputCollector;

	/* Reader instance for the input files */
	private transient BufferedReader fileReader;

	/* Key with which read prices are emitted */
	private String materialKey;

	/* The file to read */
	private String inputFile;
	
	/**
	 * Default constructor
	 * 
	 * @param inputFile The file to read from the archiv
	 * @param materialKey The key under which to emit information
	 */
	public NewPriceInfoSpout(final String inputFile, final String materialKey) {
		LOG.info("Creating new price info spout for '" + materialKey + "' from file '" + inputFile + "'");
		this.materialKey = materialKey;
		this.inputFile = inputFile;
	}

	/*
	 * (non-Javadoc)
	 * @see backtype.storm.spout.ISpout#nextTuple()
	 */
	@Override
	public void nextTuple() {
		Utils.sleep(1000);

		if(fileReader != null) {
			processFile();
		}
	}

	/**
	 * 
	 */
	private void processFile() {
		try {
			if(fileReader.ready()) {
				readAndEmitLine();
			} else {
				LOG.info("Completely read file '" + inputFile + "'");
				fileReader.close();
				fileReader = null;
			}
		} catch (NumberFormatException | IOException e) {
			LOG.error(e.getMessage());
		}
	}

	/**
	 * 
	 */
	private void readAndEmitLine() {
		String[] columns = new String[2];
		try {
			columns = fileReader.readLine().split(",");
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		outputCollector.emit(new Values(materialKey, Double.parseDouble(columns[1])));
	}

	/*
	 * (non-Javadoc)
	 * @see backtype.storm.spout.ISpout#open(java.util.Map, backtype.storm.task.TopologyContext, backtype.storm.spout.SpoutOutputCollector)
	 */
	@Override
	public void open(@SuppressWarnings("rawtypes") Map arg0, TopologyContext arg1, SpoutOutputCollector arg2) {
		this.outputCollector = arg2;
		
		try {
			fileReader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(inputFile)));
			// Ignore first line, as it is the header
			fileReader.readLine();
		} catch (FileNotFoundException e) {
			LOG.error(e.getMessage());
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}

	}

	@Override
	public void close() {
		if(fileReader != null)
			try {
				fileReader.close();
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// Two elements will be issued
		arg0.declare(new Fields("element", "price"));
	}

}

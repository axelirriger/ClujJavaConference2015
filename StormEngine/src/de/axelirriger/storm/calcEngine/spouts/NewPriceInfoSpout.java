package de.axelirriger.storm.calcEngine.spouts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

@SuppressWarnings("serial")
public class NewPriceInfoSpout extends BaseRichSpout {

	private SpoutOutputCollector outputCollector;

	/* Reader instance for the input files */
	private transient BufferedReader fileReader;

	/* Key with which read prices are emitted */
	private String materialKey;

	private String inputFile;
	
	public NewPriceInfoSpout(String inputFile, String materialKey) {
		this.materialKey = materialKey;
		this.inputFile = inputFile;
	}
	
	@Override
	public void nextTuple() {
		Utils.sleep(1000);

		String[] columns = new String[2];
		try {
			columns = fileReader.readLine().split(",");
		} catch (IOException e) {
			e.printStackTrace();
		}
		outputCollector.emit(new Values(materialKey, Double.parseDouble(columns[1])));
	}

	@Override
	public void open(@SuppressWarnings("rawtypes") Map arg0, TopologyContext arg1, SpoutOutputCollector arg2) {
		this.outputCollector = arg2;
		
		File input = new File(inputFile);
		try {
			fileReader = new BufferedReader(new FileReader(input));
			// Ignore first line, as it is the header
			fileReader.readLine();
		} catch (FileNotFoundException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		}

	}

	@Override
	public void close() {
		if(fileReader != null)
			try {
				fileReader.close();
			} catch (IOException e) {
				
			}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// Two elements will be issued
		arg0.declare(new Fields("element", "price"));
	}

}

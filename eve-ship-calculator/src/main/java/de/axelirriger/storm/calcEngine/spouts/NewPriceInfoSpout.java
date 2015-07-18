package de.axelirriger.storm.calcEngine.spouts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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

	private transient SpoutOutputCollector outputCollector;

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

		try {
			if(fileReader.ready()) {
				String[] columns = new String[2];
				try {
					columns = fileReader.readLine().split(",");
				} catch (IOException e) {
					System.err.println(e);
				}
				outputCollector.emit(new Values(materialKey, Double.parseDouble(columns[1])));
			} else {
				System.err.println("Completely read line; file is finished");
				fileReader.close();
			}
		} catch (NumberFormatException | IOException e) {
			System.err.println(e);
		}
	}

	@Override
	public void open(@SuppressWarnings("rawtypes") Map arg0, TopologyContext arg1, SpoutOutputCollector arg2) {
		this.outputCollector = arg2;
		
		try {
			fileReader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(inputFile)));
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
				System.err.println(e);
			}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// Two elements will be issued
		arg0.declare(new Fields("element", "price"));
	}

}

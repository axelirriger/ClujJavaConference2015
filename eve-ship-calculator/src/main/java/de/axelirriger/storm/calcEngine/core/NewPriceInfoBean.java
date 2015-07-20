package de.axelirriger.storm.calcEngine.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.tuple.Values;

public class NewPriceInfoBean {
	/**
	 * Logger instance
	 */
	private static final Logger LOG = LoggerFactory.getLogger(NewPriceInfoBean.class);
	
	/**
	 *  Reader instance for the input files 
	 */
	private transient BufferedReader fileReader;

	/**
	 *  The input stream to the source file 
	 */
	private transient InputStreamReader inputStreamReader;


	/**
	 *  Key with which read prices are emitted 
	 */
	private String materialKey;

	/**
	 * 
	 */
	public void closeInputFile() {
		if(fileReader != null) {
			try {
				fileReader.close();
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
		}	
	}

	/**
	 * 
	 * @param inputFileInputStream
	 */
	public void openInputFile(final InputStream inputFileInputStream) {
		try {
			inputStreamReader = new InputStreamReader(inputFileInputStream);
			fileReader = new BufferedReader(inputStreamReader);
			// Ignore first line, as it is the header
			fileReader.readLine();
		} catch (FileNotFoundException e) {
			LOG.error(e.getMessage());
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
	}


	/**
	 * Checks whether the file is ready for consumption and 
	 * emits a line from it.
	 * @param outputCollector 
	 */
	public void processFile(SpoutOutputCollector outputCollector) {
		if(fileReader != null) {
			try {
				if(fileReader.ready()) {
					readAndEmitLine(outputCollector);
				} else {
					LOG.info("Completely read file");
					fileReader.close();
					fileReader = null;
				}
			} catch (NumberFormatException | IOException e) {
				LOG.error(e.getMessage());
			}
		}
	}

	/**
	 * Reads a new line from the file, splits it by a comma and commits the first column
	 * with a <code>materialKey</code>.
	 * @param outputCollector 
	 */
	private void readAndEmitLine(final SpoutOutputCollector outputCollector) {
		String[] columns = new String[2];
		try {
			columns = fileReader.readLine().split(",");
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		outputCollector.emit(new Values(materialKey, Double.parseDouble(columns[1])));
	}

	/**
	 * 
	 * @param materialKey2
	 */
	public void setMaterialKey(String materialKey2) {
		this.materialKey = materialKey2;
	}
	
}

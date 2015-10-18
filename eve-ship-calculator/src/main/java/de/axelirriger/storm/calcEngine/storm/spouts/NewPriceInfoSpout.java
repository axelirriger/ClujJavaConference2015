package de.axelirriger.storm.calcEngine.storm.spouts;

import java.io.InputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;
import de.axelirriger.storm.calcEngine.core.NewPriceInfoBean;

/**
 * The class emits tuples to streams.
 * 
 * @author irrigera
 *
 */
public class NewPriceInfoSpout extends BaseRichSpout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8968374826748203469L;

	/**
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(NewPriceInfoSpout.class);

	/**
	 * Output emitter
	 */
	private transient SpoutOutputCollector outputCollector;

	/**
	 * The file to read
	 */
	private String inputFile;

	/**
	 * The business logic
	 */
	private transient NewPriceInfoBean priceInfoBean;

	/**
	 * The name of the material to emit
	 */
	private String materialKey;

	/**
	 * Default constructor
	 * 
	 * @param inputFile
	 *            The file to read from the archiv
	 * @param materialKey
	 *            The key under which to emit information
	 */
	public NewPriceInfoSpout(final String inputFile, final String materialKey) {
		if(LOG.isInfoEnabled()) {
			LOG.info("Creating new price info spout for '" + materialKey + "' from file '" + inputFile + "'");
		}

		this.inputFile = inputFile;
		this.materialKey = materialKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see backtype.storm.spout.ISpout#nextTuple()
	 */
	@Override
	public void nextTuple() {
		// What one sec between emitting tuples
		// Utils.sleep(1000);

		priceInfoBean.processFile(outputCollector);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see backtype.storm.spout.ISpout#open(java.util.Map,
	 * backtype.storm.task.TopologyContext,
	 * backtype.storm.spout.SpoutOutputCollector)
	 */
	@Override
	public void open(@SuppressWarnings("rawtypes") Map arg0, TopologyContext arg1, SpoutOutputCollector arg2) {
		this.outputCollector = arg2;

		final InputStream inputFileInputStream = this.getClass().getClassLoader().getResourceAsStream(inputFile);

		priceInfoBean = new NewPriceInfoBean();
		priceInfoBean.setMaterialKey(materialKey);
		priceInfoBean.openInputFile(inputFileInputStream);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see backtype.storm.topology.base.BaseRichSpout#close()
	 */
	@Override
	public void close() {
		priceInfoBean.closeInputFile();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * backtype.storm.topology.IComponent#declareOutputFields(backtype.storm.
	 * topology.OutputFieldsDeclarer)
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// Two elements will be emitted
		arg0.declare(new Fields("element", "price"));
	}

}

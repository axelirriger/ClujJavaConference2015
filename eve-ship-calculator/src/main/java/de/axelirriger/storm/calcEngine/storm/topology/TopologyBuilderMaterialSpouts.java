package de.axelirriger.storm.calcEngine.storm.topology;

import org.apache.storm.topology.TopologyBuilder;

import de.axelirriger.storm.calcEngine.storm.spouts.NewPriceInfoSpout;

/**
 * This class creates the partial topology for the spouts (=sources).
 * 
 * @author irrigera
 *
 */
public class TopologyBuilderMaterialSpouts {

	/**
	 * The name of the spout emitting Zydrine prices
	 */
	public static final String SPOUT_ZYDRINE = "zydrine-spout";
	/**
	 * The name of the spout emitting Zydrine prices
	 */
	public static final String SPOUT_NOCXIUM = "nocxium-spout";
	/**
	 * The name of the spout emitting Isogen prices
	 */
	public static final String SPOUT_ISOGEN = "isogen-spout";
	/**
	 * The name of the spout emitting Mexallon prices
	 */
	public static final String SPOUT_MEXALLON = "mexallon-spout";
	/**
	 * The name of the spout emitting Tritanium prices
	 */
	public static final String SPOUT_TRITANIUM = "tritanium-spout";
	/**
	 * The name of the spout emitting Megacyte prices
	 */
	public static final String SPOUT_MEGACYTE = "megacyte-spout";
	/**
	 * The name of the spout emitting Pyerite prices
	 */
	public static final String SPOUT_PYERITE = "pyerite-spout";

	/**
	 * This method creates a new spout per material and puts it to the topology
	 * 
	 * @param builder The overall topology builder instance
	 */
	public void createSpouts(final TopologyBuilder builder) {
		int numberOfTasksPerSpout = 1;
		int numberOfExecturs = 1;
		builder.setSpout(SPOUT_TRITANIUM, new NewPriceInfoSpout("Tritanium-Apple.csv", "Tritanium"), numberOfExecturs).setNumTasks(numberOfTasksPerSpout);
		builder.setSpout(SPOUT_MEXALLON, new NewPriceInfoSpout("Mexallon-Microsoft.csv", "Mexallon"), numberOfExecturs).setNumTasks(numberOfTasksPerSpout);
		builder.setSpout(SPOUT_ISOGEN, new NewPriceInfoSpout("Isogen-ATT.csv", "Isogen"), numberOfExecturs).setNumTasks(numberOfTasksPerSpout);
		builder.setSpout(SPOUT_NOCXIUM, new NewPriceInfoSpout("Nocxium-Oracle.csv", "Nocxium"), numberOfExecturs).setNumTasks(numberOfTasksPerSpout);
		builder.setSpout(SPOUT_ZYDRINE, new NewPriceInfoSpout("Zydrine-Motorola.csv", "Zydrine"), numberOfExecturs).setNumTasks(numberOfTasksPerSpout);
		builder.setSpout(SPOUT_MEGACYTE, new NewPriceInfoSpout("Megacyte-Motorola.csv", "Megacyte"), numberOfExecturs).setNumTasks(numberOfTasksPerSpout);
		builder.setSpout(SPOUT_PYERITE, new NewPriceInfoSpout("Pyerite-Motorola.csv", "Pyerite"), numberOfExecturs).setNumTasks(numberOfTasksPerSpout);
		
	}
}

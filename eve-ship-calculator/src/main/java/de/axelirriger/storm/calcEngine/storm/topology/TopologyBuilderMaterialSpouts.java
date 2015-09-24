package de.axelirriger.storm.calcEngine.storm.topology;

import backtype.storm.topology.TopologyBuilder;
import de.axelirriger.storm.calcEngine.storm.spouts.NewPriceInfoSpout;

public class TopologyBuilderMaterialSpouts {

	public static final String SPOUT_ZYDRINE = "zydrine-spout";
	public static final String SPOUT_NOCXIUM = "nocxium-spout";
	public static final String SPOUT_ISOGEN = "isogen-spout";
	public static final String SPOUT_MEXALLON = "mexallon-spout";
	public static final String SPOUT_TRITANIUM = "tritanium-spout";
	public static final String SPOUT_MEGACYTE = "megacyte-spout";
	public static final String SPOUT_PYERITE = "pyerite-spout";

	public void createSpouts(TopologyBuilder builder) {
		builder.setSpout(SPOUT_TRITANIUM, new NewPriceInfoSpout("Tritanium-Apple.csv", "Tritanium"), 1).setNumTasks(10);
		builder.setSpout(SPOUT_MEXALLON, new NewPriceInfoSpout("Mexallon-Microsoft.csv", "Mexallon"), 1).setNumTasks(10);
		builder.setSpout(SPOUT_ISOGEN, new NewPriceInfoSpout("Isogen-ATT.csv", "Isogen"), 1).setNumTasks(10);
		builder.setSpout(SPOUT_NOCXIUM, new NewPriceInfoSpout("Nocxium-Oracle.csv", "Nocxium"), 1).setNumTasks(10);
		builder.setSpout(SPOUT_ZYDRINE, new NewPriceInfoSpout("Zydrine-Motorola.csv", "Zydrine"), 1).setNumTasks(10);
		builder.setSpout(SPOUT_MEGACYTE, new NewPriceInfoSpout("Megacyte-Motorola.csv", "Megacyte"), 1).setNumTasks(10);
		builder.setSpout(SPOUT_PYERITE, new NewPriceInfoSpout("Pyerite-Motorola.csv", "Pyerite"), 1).setNumTasks(10);
		
	}
}

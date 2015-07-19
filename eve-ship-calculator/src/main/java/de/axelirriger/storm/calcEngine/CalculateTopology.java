package de.axelirriger.storm.calcEngine;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.BoltDeclarer;
import backtype.storm.topology.IBasicBolt;
import backtype.storm.topology.TopologyBuilder;
import de.axelirriger.storm.calcEngine.storm.bolts.KestrelCostCalculatorBolt;
import de.axelirriger.storm.calcEngine.storm.bolts.MaterialSplitterBolt;
import de.axelirriger.storm.calcEngine.storm.bolts.ShipMaterialCostCalculatorBolt;
import de.axelirriger.storm.calcEngine.storm.spouts.NewPriceInfoSpout;

public class CalculateTopology {

	private static final String BOLT_MATERIAL_SPLITTER = "materialSplitter";
	private static final String BOLT_KESTREL_ZYDRINE = "kestrel-zydrine";
	private static final String BOLT_KESTREL_NOCXIUM = "kestrel-nocxium";
	private static final String BOLT_KESTREL_ISOGEN = "kestrel-isogen";
	private static final String BOLT_KESTREL_MEXALLON = "kestrel-mexallon";
	private static final String BOLT_KESTREL_TRITANIUM = "kestrel-tritanium";
	private static final String SPOUT_ZYDRINE = "zydrine-spout";
	private static final String SPOUT_NOCXIUM = "nocxium-spout";
	private static final String SPOUT_ISOGEN = "isogen-spout";
	private static final String SPOUT_MEXALLON = "mexallon-spout";
	private static final String SPOUT_TRITANIUM = "tritanium-spout";

	private CalculateTopology() {}
	
	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, InterruptedException {
		TopologyBuilder builder = new TopologyBuilder();
		
		/*
		 * Source components (spouts). Each one simply operates on the given file
		 */
		builder.setSpout(SPOUT_TRITANIUM, new NewPriceInfoSpout("Tritanium-Apple.csv", "Tritanium"), 1);
		builder.setSpout(SPOUT_MEXALLON, new NewPriceInfoSpout("Mexallon-Microsoft.csv", "Mexallon"), 1);
		builder.setSpout(SPOUT_ISOGEN, new NewPriceInfoSpout("Isogen-ATT.csv", "Isogen"), 1);
		builder.setSpout(SPOUT_NOCXIUM, new NewPriceInfoSpout("Nocxium-Oracle.csv", "Nocxium"), 1);
		builder.setSpout(SPOUT_ZYDRINE, new NewPriceInfoSpout("Zydrine-Motorola.csv", "Zydrinen"), 1);

		/*
		 * The splitter which connects to all sources and splits according the material found
		 */
		final BoltDeclarer materialSplitter = builder.setBolt(BOLT_MATERIAL_SPLITTER, new MaterialSplitterBolt(), 1);
		materialSplitter.shuffleGrouping(SPOUT_TRITANIUM);
		materialSplitter.shuffleGrouping(SPOUT_MEXALLON);
		materialSplitter.shuffleGrouping(SPOUT_ISOGEN);
		materialSplitter.shuffleGrouping(SPOUT_NOCXIUM);
		materialSplitter.shuffleGrouping(SPOUT_ZYDRINE);
		
		final IBasicBolt kestrelTritanium = new ShipMaterialCostCalculatorBolt(BOLT_KESTREL_TRITANIUM, 16337);
		final IBasicBolt kestrelMexallon = new ShipMaterialCostCalculatorBolt(BOLT_KESTREL_MEXALLON, 2837);
		final IBasicBolt kestrelIsogen = new ShipMaterialCostCalculatorBolt(BOLT_KESTREL_ISOGEN, 947);
		final IBasicBolt kestrelNocxium = new ShipMaterialCostCalculatorBolt(BOLT_KESTREL_NOCXIUM, 1);
		final IBasicBolt kestrelZydrine = new ShipMaterialCostCalculatorBolt(BOLT_KESTREL_ZYDRINE, 1);

		builder.setBolt(BOLT_KESTREL_TRITANIUM, kestrelTritanium, 1).shuffleGrouping(BOLT_MATERIAL_SPLITTER, "tritanium");
		builder.setBolt(BOLT_KESTREL_MEXALLON, kestrelMexallon, 1).shuffleGrouping(BOLT_MATERIAL_SPLITTER, "mexallon");
		builder.setBolt(BOLT_KESTREL_ISOGEN, kestrelIsogen, 1).shuffleGrouping(BOLT_MATERIAL_SPLITTER, "isogen");
		builder.setBolt(BOLT_KESTREL_NOCXIUM, kestrelNocxium, 1).shuffleGrouping(BOLT_MATERIAL_SPLITTER, "nocxium");
		builder.setBolt(BOLT_KESTREL_ZYDRINE, kestrelZydrine, 1).shuffleGrouping(BOLT_MATERIAL_SPLITTER, "zydrine");
		
		final IBasicBolt kestrel = new KestrelCostCalculatorBolt();
		final BoltDeclarer kestrelBolt = builder.setBolt("kestrel", kestrel, 1);
		kestrelBolt.shuffleGrouping(BOLT_KESTREL_TRITANIUM);
		kestrelBolt.shuffleGrouping(BOLT_KESTREL_MEXALLON);
		kestrelBolt.shuffleGrouping(BOLT_KESTREL_ISOGEN);
		kestrelBolt.shuffleGrouping(BOLT_KESTREL_NOCXIUM);
		kestrelBolt.shuffleGrouping(BOLT_KESTREL_ZYDRINE);
		
		Config conf = new Config();
	    conf.setDebug(true);
	    
	    if (args != null && args.length > 0) {
	        //parallelism hint to set the number of workers
	        conf.setNumWorkers(6);
	        //submit the topology
	        StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
	      }
	      //Otherwise, we are running locally
	      else {
	        //Cap the maximum number of executors that can be spawned
	        //for a component to 3
	        conf.setMaxTaskParallelism(3);
	        //LocalCluster is used to run locally
	        LocalCluster cluster = new LocalCluster();
	        //submit the topology
	        cluster.submitTopology("eve-sample", conf, builder.createTopology());
	        //sleep
	        Thread.sleep(1000000);
	        //shut down the cluster
	        cluster.shutdown();
	      }
	}

}

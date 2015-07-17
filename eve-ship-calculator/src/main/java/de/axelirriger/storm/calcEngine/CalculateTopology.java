package de.axelirriger.storm.calcEngine;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.BoltDeclarer;
import backtype.storm.topology.IBasicBolt;
import backtype.storm.topology.TopologyBuilder;
import de.axelirriger.storm.calcEngine.bolts.KestrelCostCalculatorBolt;
import de.axelirriger.storm.calcEngine.bolts.MaterialSplitterBolt;
import de.axelirriger.storm.calcEngine.bolts.ShipMaterialCostCalculatorBolt;
import de.axelirriger.storm.calcEngine.spouts.NewPriceInfoSpout;

public class CalculateTopology {

	private CalculateTopology() {}
	
	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, InterruptedException {
		TopologyBuilder builder = new TopologyBuilder();
		
		/*
		 * Source components (spouts). Each one simply operates on the given file
		 */
		builder.setSpout("tritanium-spout", new NewPriceInfoSpout("Tritanium-Apple.csv", "Tritanium"), 1);
		builder.setSpout("mexallon-spout", new NewPriceInfoSpout("Mexallon-Microsoft.csv", "Mexallon"), 1);
		builder.setSpout("isogen-spout", new NewPriceInfoSpout("Isogen-ATT.csv", "Isogen"), 1);
		builder.setSpout("nocxium-spout", new NewPriceInfoSpout("Nocxium-Oracle.csv", "Nocxium"), 1);
		builder.setSpout("zydrine-spout", new NewPriceInfoSpout("Zydrine-Motorola.csv", "Zydrinen"), 1);

		/*
		 * The splitter which connects to all sources and splits according the material found
		 */
		final BoltDeclarer materialSplitter = builder.setBolt("materialSplitter", new MaterialSplitterBolt(), 5);
		materialSplitter.shuffleGrouping("tritanium-spout");
		materialSplitter.shuffleGrouping("mexallon-spout");
		materialSplitter.shuffleGrouping("isogen-spout");
		materialSplitter.shuffleGrouping("nocxium-spout");
		materialSplitter.shuffleGrouping("zydrine-spout");
		
		final IBasicBolt kestrelTritanium = new ShipMaterialCostCalculatorBolt("kestrel-tritanium", 16337);
		final IBasicBolt kestrelMexallon = new ShipMaterialCostCalculatorBolt("kestrel-mexallon", 2837);
		final IBasicBolt kestrelIsogen = new ShipMaterialCostCalculatorBolt("kestrel-isogen", 947);
		final IBasicBolt kestrelNocxium = new ShipMaterialCostCalculatorBolt("kestrel-nocxium", 1);
		final IBasicBolt kestrelZydrine = new ShipMaterialCostCalculatorBolt("kestrel-zydrine", 1);

		builder.setBolt("kestrel-tritanium", kestrelTritanium, 1).shuffleGrouping("materialSplitter", "tritanium");
		builder.setBolt("kestrel-mexallon", kestrelMexallon, 1).shuffleGrouping("materialSplitter", "mexallon");
		builder.setBolt("kestrel-isogen", kestrelIsogen, 1).shuffleGrouping("materialSplitter", "isogen");
		builder.setBolt("kestrel-nocxium", kestrelNocxium, 1).shuffleGrouping("materialSplitter", "nocxium");
		builder.setBolt("kestrel-zydrine", kestrelZydrine, 1).shuffleGrouping("materialSplitter", "zydrine");
		
		final IBasicBolt kestrel = new KestrelCostCalculatorBolt();
		final BoltDeclarer kestrelBolt = builder.setBolt("kestrel", kestrel, 1);
		kestrelBolt.shuffleGrouping("kestrel-tritanium");
		kestrelBolt.shuffleGrouping("kestrel-mexallon");
		kestrelBolt.shuffleGrouping("kestrel-isogen");
		kestrelBolt.shuffleGrouping("kestrel-nocxium");
		kestrelBolt.shuffleGrouping("kestrel-zydrine");
		
		Config conf = new Config();
	    conf.setDebug(true);
	    
	    if (args != null && args.length > 0) {
	        //parallelism hint to set the number of workers
	        conf.setNumWorkers(3);
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

package de.axelirriger.storm.calcEngine;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.BoltDeclarer;
import backtype.storm.topology.TopologyBuilder;
import de.axelirriger.storm.calcEngine.storm.bolts.MaterialSplitterBolt;
import de.axelirriger.storm.calcEngine.storm.topology.Kestrel;
import de.axelirriger.storm.calcEngine.storm.topology.Rifter;
import de.axelirriger.storm.calcEngine.storm.topology.TopologyBuilderMaterialSpouts;

public class CalculateTopology {

	public static final String BOLT_MATERIAL_SPLITTER = "materialSplitter";
	
	private CalculateTopology() {}
	
	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, InterruptedException {
		TopologyBuilder builder = new TopologyBuilder();
		
		/*
		 * Source components (spouts). Each one simply operates on the given file
		 */
		TopologyBuilderMaterialSpouts spoutBuilder = new TopologyBuilderMaterialSpouts();
		spoutBuilder.createSpouts(builder);
		
		/*
		 * The splitter which connects to all sources and splits according the material found
		 */
		createMaterialSplitter(builder);
		
		/*
		 * Create Kestrel information 
		 */
		Kestrel kestrel = new Kestrel();
		kestrel.createTopology(builder);
		
		/*
		 * Create Rifter information
		 */
		Rifter rifter = new Rifter();
		rifter.createTopology(builder);
		
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

	/**
	 * 
	 * @param builder
	 */
	private static void createMaterialSplitter(TopologyBuilder builder) {
		final BoltDeclarer materialSplitter = builder.setBolt(BOLT_MATERIAL_SPLITTER, new MaterialSplitterBolt(), 1);
		materialSplitter.shuffleGrouping(TopologyBuilderMaterialSpouts.SPOUT_TRITANIUM);
		materialSplitter.shuffleGrouping(TopologyBuilderMaterialSpouts.SPOUT_MEXALLON);
		materialSplitter.shuffleGrouping(TopologyBuilderMaterialSpouts.SPOUT_ISOGEN);
		materialSplitter.shuffleGrouping(TopologyBuilderMaterialSpouts.SPOUT_NOCXIUM);
		materialSplitter.shuffleGrouping(TopologyBuilderMaterialSpouts.SPOUT_ZYDRINE);
		materialSplitter.shuffleGrouping(TopologyBuilderMaterialSpouts.SPOUT_MEGACYTE);
		materialSplitter.shuffleGrouping(TopologyBuilderMaterialSpouts.SPOUT_PYERITE);
	}

}

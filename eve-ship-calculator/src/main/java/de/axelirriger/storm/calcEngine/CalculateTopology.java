package de.axelirriger.storm.calcEngine;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.BoltDeclarer;
import backtype.storm.topology.TopologyBuilder;
import de.axelirriger.storm.calcEngine.storm.bolts.MaterialSplitterBolt;
import de.axelirriger.storm.calcEngine.storm.topology.AmarrPunisher;
import de.axelirriger.storm.calcEngine.storm.topology.CaldariKestrel;
import de.axelirriger.storm.calcEngine.storm.topology.GallenteImicus;
import de.axelirriger.storm.calcEngine.storm.topology.MinmatarRifter;
import de.axelirriger.storm.calcEngine.storm.topology.TopologyBuilderMaterialSpouts;

/**
 * This is the main class of the calculation topology. It creates the topology
 * by using smaller partial topology creating classes and submits it the
 * cluster.
 * 
 * @author irrigera
 *
 */
public class CalculateTopology {

	public static final String BOLT_MATERIAL_SPLITTER = "materialSplitter";

	private CalculateTopology() {
	}

	public static void main(String[] args)
			throws AlreadyAliveException, InvalidTopologyException, InterruptedException {
		TopologyBuilder builder = new TopologyBuilder();

		/*
		 * Source components (spouts). Each one simply operates on the given
		 * file
		 */
		TopologyBuilderMaterialSpouts spoutBuilder = new TopologyBuilderMaterialSpouts();
		spoutBuilder.createSpouts(builder);

		/*
		 * The splitter which connects to all sources and splits according the
		 * material found
		 */
		createMaterialSplitter(builder);

		/*
		 * Create Kestrel information
		 */
		CaldariKestrel kestrel = new CaldariKestrel();
		kestrel.createTopology(builder);

		/*
		 * Create Rifter information
		 */
		MinmatarRifter rifter = new MinmatarRifter();
		rifter.createTopology(builder);

		/*
		 * Create Imicus information
		 */
		GallenteImicus imicus = new GallenteImicus();
		imicus.createTopology(builder);

		/*
		 * Create Punisher information
		 */
		AmarrPunisher punisher = new AmarrPunisher();
		punisher.createTopology(builder);

		Config conf = new Config();
		conf.setDebug(true);

		if (args != null && args.length > 0) {
			// parallelism hint to set the number of workers
			conf.setNumWorkers(1);
			// submit the topology
			StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
		}
		// Otherwise, we are running locally
		else {
			// LocalCluster is used to run locally
			LocalCluster cluster = new LocalCluster();
			// submit the topology
			cluster.submitTopology("eve-sample", conf, builder.createTopology());
			// sleep
			Thread.sleep(1000000);
			// shut down the cluster
			cluster.shutdown();
		}
	}

	/**
	 * 
	 * @param builder
	 */
	private static void createMaterialSplitter(TopologyBuilder builder) {
		int numberOfTasks = 1;
		int numberOfExecutors = 1;
		final BoltDeclarer materialSplitter = builder.setBolt(BOLT_MATERIAL_SPLITTER, new MaterialSplitterBolt(), numberOfExecutors)
				.setNumTasks(numberOfTasks);
		materialSplitter.shuffleGrouping(TopologyBuilderMaterialSpouts.SPOUT_TRITANIUM);
		materialSplitter.shuffleGrouping(TopologyBuilderMaterialSpouts.SPOUT_MEXALLON);
		materialSplitter.shuffleGrouping(TopologyBuilderMaterialSpouts.SPOUT_ISOGEN);
		materialSplitter.shuffleGrouping(TopologyBuilderMaterialSpouts.SPOUT_NOCXIUM);
		materialSplitter.shuffleGrouping(TopologyBuilderMaterialSpouts.SPOUT_ZYDRINE);
		materialSplitter.shuffleGrouping(TopologyBuilderMaterialSpouts.SPOUT_MEGACYTE);
		materialSplitter.shuffleGrouping(TopologyBuilderMaterialSpouts.SPOUT_PYERITE);
	}

}

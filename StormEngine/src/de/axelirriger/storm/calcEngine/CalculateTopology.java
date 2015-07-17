package de.axelirriger.storm.calcEngine;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import de.axelirriger.storm.calcEngine.bolts.ProcessorBolt;
import de.axelirriger.storm.calcEngine.bolts.SenkenBolt;
import de.axelirriger.storm.calcEngine.spouts.QuellenSpout;

public class CalculateTopology {

	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, InterruptedException {
		TopologyBuilder builder = new TopologyBuilder();
		
		builder.setSpout("spout", new QuellenSpout(), 2);
		builder.setBolt("filter", new ProcessorBolt(), 8).shuffleGrouping("spout");
		builder.setBolt("senke", new SenkenBolt(), 1).shuffleGrouping("filter");

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
	        cluster.submitTopology("calculate-sample", conf, builder.createTopology());
	        //sleep
	        Thread.sleep(1000000);
	        //shut down the cluster
	        cluster.shutdown();
	      }
	}

}

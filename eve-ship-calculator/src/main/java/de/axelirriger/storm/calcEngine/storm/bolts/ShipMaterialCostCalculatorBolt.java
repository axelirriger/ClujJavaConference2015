	package de.axelirriger.storm.calcEngine.storm.bolts;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

@SuppressWarnings("serial")
public class ShipMaterialCostCalculatorBolt extends BaseBasicBolt {

	protected static final String FIELD_PRICE = "price";
	protected static final String FIELD_COST = "cost";
	protected static final String FIELD_SHIP_MATERIAL = "ship-material";
	
	public ShipMaterialCostCalculatorBolt( final String ship_material, int amount) {
		FIELD_MATERIAL = ship_material;
		this.amount = amount;
	}
	
	/**
	 * The ship-specific material to be emitted
	 */
	protected String FIELD_MATERIAL = "--";

	/**
	 * Declares the amount of material necessary.
	 */
	int amount = -1;

	/*
	 * (non-Javadoc)
	 * @see backtype.storm.topology.IBasicBolt#execute(backtype.storm.tuple.Tuple, backtype.storm.topology.BasicOutputCollector)
	 */
	@Override
	public void execute(final Tuple arg0, final BasicOutputCollector arg1) {
		final double price = getPriceFromTuple(arg0);
		
		final double cost = amount * price;
		
		arg1.emit(new Values(FIELD_MATERIAL, cost));
	}

	/**
	 * Extracts the price field from the given tuple
	 * @param arg0
	 * @return
	 */
	private final double getPriceFromTuple(final Tuple arg0) {
		double price = arg0.getDoubleByField(FIELD_PRICE);
		return price;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		arg0.declare(new Fields(FIELD_SHIP_MATERIAL, FIELD_COST));
	}

}
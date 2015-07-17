package de.axelirriger.storm.calcEngine.bolts;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

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
	protected String FIELD_MATERIAL = "kestrel-tritanium";

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
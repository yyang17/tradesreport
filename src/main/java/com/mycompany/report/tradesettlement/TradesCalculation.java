package com.mycompany.report.tradesettlement;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * This class does calculations on a given list of trades to get 
 * the incoming/outgoing USD amount of the trades and the ranking
 * of entities based on incoming/outgoing amount.
 * 
 * @author  Y. Yang
 * @version 1.0
 * @since   2017-05-01
 */
public class TradesCalculation {
	
	private LinkedList<Trade> tradeList = new LinkedList<Trade>();
	private LocalDate date; //The date for which the settlement amount is to be calculated
	private ResultsCalculated results = null; //Store the calculated results
	
	/**
	 * The constructor creates a TradesCalculation object on a given list
	 * of trades. 
	 * @param tradeList A list of trades.
	 * @param date The date for which the settlement amount is to be calculated.
	 */
	public TradesCalculation( LinkedList<Trade> tradeList, LocalDate date ) {
		this.tradeList = tradeList;
		this.date = date;
		
		//Perform calculation
		results = new ResultsCalculated( date, 
									settledAmountInUsd( "B" ), 	//Outgoing USD amount
									settledAmountInUsd( "S" ), 	//Incoming USD amount
									rankEntities( "B" ), 		//Entity rankings with respect to the outgoing amount 
									rankEntities( "S" ) );		//Entity rankings with respect to the incoming amount
	}

	/**
	 * The sum of incoming/outgoing USD amount on a given date.
	 * 
	 * @param direction Flag indicating whether it's incoming or outgoing: "B" or "S".
	 * @return The incoming or outgoing USD amount on a given date.
	 */
	private BigDecimal settledAmountInUsd( String direction ) {
		BigDecimal amountSum = BigDecimal.ZERO;
		
		if( tradeList != null && tradeList.size() > 0 ) {			
			for( Trade trade : tradeList ) {
				if( trade.getDirection().equalsIgnoreCase( direction ) ) {
					if( trade.getActualSettleDate().equals( date ) ) {
						amountSum = amountSum.add( trade.getUsdAmount() );
					}
				}
			}
		}
		
		return amountSum;
	}
	
	/**
	 * This method ranks entities according to their incoming/outgoing USD amount.
	 * 
	 * @param direction Flag indicating whether it's ranked for incoming or outgoing USD amount.
	 * @return The ranked list of entities.
	 */
	private Map<String, BigDecimal> rankEntities( String direction ) {
		HashMap<String, BigDecimal> amountMap = new HashMap<String, BigDecimal>(); //Store the USD amount for each entity
		
		if( tradeList != null && tradeList.size() > 0 ) {
			for( Trade trade : tradeList ) {
				if( trade.getDirection().equalsIgnoreCase( direction ) ) {
					if( trade.getActualSettleDate().equals( date ) ) {
						String entity = trade.getEntity().toLowerCase();
						if( amountMap.containsKey( entity ) ) {
							amountMap.put( entity.toLowerCase(), amountMap.get( entity ).add( trade.getUsdAmount() ) ); //An entity could have more than one trades to be settled on the same day.
						} else
							amountMap.put( entity.toLowerCase(), trade.getUsdAmount() );
					}
				}
			}
		}
		
		//Sort by amount 
		Map<String, BigDecimal> sortedMap =  sortByValue( amountMap );
		return sortedMap;
	}
	
	/**
	 * Sort a given map by its values.
	 * @param map The map to be sorted by its values.
	 * @return Map that has been sorted by its values.
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
	    return map.entrySet()
	              .stream()
	              .sorted( Map.Entry.comparingByValue( Collections.reverseOrder() ) )
	              .collect( Collectors.toMap(
	            		  Map.Entry::getKey, 
	            		  Map.Entry::getValue, 
	            		  (e1, e2) -> e1, 
	            		  LinkedHashMap::new      //Use LinkedHashMap for sorting
	              ));
	}
	
	/**
	 * Gets the calculated results.
	 * @return
	 */
	public ResultsCalculated getResults() {
		return this.results;
	}
}

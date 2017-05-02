package com.mycompany.report.tradesettlement;

import java.util.LinkedList;
import java.time.LocalDate;
import java.util.Map;
import java.math.BigDecimal;

/**
 * This class generates a report which lists the USD amount settled
 * incoming/outgoing on a given date. It also shows rankings of entities
 * based on the incoming and outgoing amount.
 * 
 * @author  Y. Yang
 * @version 1.0
 * @since   2017-05-01
 */
public class ReportGenerator {
	private LocalDate date; //The date for which a report is to be generated.
	private ResultsCalculated results = null;
	
	/**
	 * Constructs an object of ReportGenerator and calls TradesCalculation to get the results.
	 * 
	 * @param trades The list of trades.
	 * @param date The date for which the report is to be generated.
	 */
	public ReportGenerator( LinkedList<Trade> trades, LocalDate date ){
		this.date = date;
		TradesCalculation tradesCalc = new TradesCalculation( trades, date );
		this.results = tradesCalc.getResults();
	}
	
	/**
	 * This method prints out the settled amount in USD and the rankings of entities.
	 * 
	 */
	public void displayReport() {
		System.out.println( "Report for Date: " + date.getDayOfWeek() + " " + date );
		System.out.println( "" );
		
		if( this.results != null ) {
			BigDecimal outgoingAmount = this.results.getOutgoingAmount().setScale( 2, BigDecimal.ROUND_FLOOR );
			BigDecimal incomingAmount = this.results.getIncomingAmount().setScale( 2, BigDecimal.ROUND_FLOOR );
		
			System.out.println( "Outgoing amount in USD: " + outgoingAmount );
			System.out.println( "Incoming amount in USD: " + incomingAmount );
			System.out.println( "" );
		
			System.out.println( "Rankings with repect to the outgoing amount: " );
			displayRankings( this.results.getOutgoingRankings() );
			System.out.println( "" );
			System.out.println( "Rankings with repect to the incoming amount: " );
			displayRankings( this.results.getIncomingRankings() );
		} else {
			System.out.println( "Nothing has been calculated!" );
		}
	}
	
	/**
	 * Display the list of ranked entities
	 * 
	 * @param rankings
	 */
	private void displayRankings( Map<String, BigDecimal> rankings ) {
        // Displaying elements of LinkedHashMap
        for( Map.Entry<String, BigDecimal> entry : rankings.entrySet() ) {
        	System.out.println( "("+ entry.getKey() + ", "+ entry.getValue().setScale( 2, BigDecimal.ROUND_FLOOR ) + ")" );
        }

	}
}

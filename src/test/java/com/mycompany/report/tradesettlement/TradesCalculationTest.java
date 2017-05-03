package com.mycompany.report.tradesettlement;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class TradesCalculationTest {
	
	private LinkedList<Trade> trades = null;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "dd MMM yyyy", Locale.ENGLISH );

	@Before
	public void initialize() {
		//set up test data
		trades = new LinkedList<Trade>( Arrays.asList( 
			new Trade("foo", "B", 0.20, "SGP", "01 Jan 2017", "02 Jan 2017", 200, 100.25), //"02 Jan 2017" Monday 
			new Trade("sai", "S", 0.50, "CAN", "01 Jan 2017", "07 Jan 2017", 150, 99.75), //"07 Jan 2017" Saturday
			new Trade("sai", "B", 1.0, "GBP", "01 Jan 2017", "08 Jan 2017", 150, 99.75), //"08 Jan 2017" Sunday
			new Trade("tes", "S", 0.85, "AED", "01 Jan 2017", "05 Jan 2017", 300, 98.00), //"05 Jan 2017" Thursday
			new Trade("tes", "S", 0.85, "AED", "01 Jan 2017", "06 Jan 2017", 300, 100.00), //"06 Jan 2017" Friday
			new Trade("wai", "B", 0.79, "SAR", "01 Jan 2017", "07 Jan 2017", 220, 101.25) //"07 Jan 2017" Saturday
		) ); 
	};
	
	@Test
	public void testIfNoTradesOrTradeListEmpty() {
		testIfZeroTradesArePassedIn( null ); //If no trades 
		testIfZeroTradesArePassedIn( new LinkedList<Trade>() ); //If trade list is empty
	}
	
	private void testIfZeroTradesArePassedIn(LinkedList<Trade> tradeList) {
		TradesCalculation calc = new TradesCalculation( tradeList, LocalDate.now() ); 
		
		assertNotNull( calc );
		
		assertTrue( "Incoming amount in USD", calc.getResults().getIncomingAmount().doubleValue() == 0.0 );
		assertTrue( "Outgoinging amount in USD", calc.getResults().getOutgoingAmount().doubleValue() == 0.0 );
		
		assertTrue( "Entity rankings with repect to outgoing amount", calc.getResults().getOutgoingRankings().isEmpty() );
		assertTrue( "Entity rankings with repect to incoming amount", calc.getResults().getIncomingRankings().isEmpty() );
	}
	
	@Test
	public void testSettledAmountInUSD() {		
		String[] dates = { "07 Jan 2017",//Saturday
						"09 Jan 2017", //Monday
						"06 Jan 2017", //Friday
						"08 Jan 2017" //Sunday
		};
	
		for( String date : dates ) {
			testSettledAmountInUsdOnAGivenDate( trades, LocalDate.parse( date, formatter ) ); 
		}		
	}
	
	private void testSettledAmountInUsdOnAGivenDate( LinkedList<Trade> tradeList, LocalDate date ) {
		TradesCalculation calc = new TradesCalculation( tradeList, date );
		
		BigDecimal settledIncomingSum = BigDecimal.ZERO, settledOutgoingSum = BigDecimal.ZERO;
		for( Trade trade : trades ) {
			if( trade.getActualSettleDate().equals( date ) ) {
				if( trade.getDirection().equalsIgnoreCase( "S" ) ) {
					settledIncomingSum  = settledIncomingSum.add( trade.getUsdAmount() );
				} else if( trade.getDirection().equalsIgnoreCase( "B" ) ) {
					settledOutgoingSum  = settledOutgoingSum .add( trade.getUsdAmount() );
				}
			}
		}
		
		System.out.println( settledOutgoingSum );
		System.out.println( settledIncomingSum );
		
		assertTrue( "Total outgoing amount in USD", settledOutgoingSum.compareTo( calc.getResults().getOutgoingAmount() ) == 0 );
		assertTrue( "Total incoming amount in USD", settledIncomingSum.compareTo( calc.getResults().getIncomingAmount() ) == 0 );
	}

	@Test
	public void testRankingEntities() {
		String[] dates = { "07 Jan 2017",//Saturday
						"09 Jan 2017", //Monday
						"06 Jan 2017", //Friday
						"08 Jan 2017" //Sunday
		};
	
		for( String date : dates ) {
			testRankingEntitiesOnAGivenDate( trades, LocalDate.parse( date, formatter ) ); 
		}		
	}
	
	private void testRankingEntitiesOnAGivenDate( LinkedList<Trade> tradeList, LocalDate date ) {
		TradesCalculation calc = new TradesCalculation( tradeList, date );
		
		assertTrue( "Rankings with respect to incoming amount in USD", isOrdered( calc.getResults().getIncomingRankings() ) );
		assertTrue( "Rankings with respect to outgoing amount in USD", isOrdered( calc.getResults().getOutgoingRankings() ) );
	} 
	
	private boolean isOrdered( Map<String, BigDecimal> rankings ) {
		BigDecimal max = BigDecimal.valueOf( Double.MAX_VALUE );
		boolean isOrdered = true;
		for( Map.Entry<String, BigDecimal> entry : rankings.entrySet() ) {
			if( entry.getValue().doubleValue() < max.doubleValue() )
				max = entry.getValue();
			else
				isOrdered = false;
		}
		
		return isOrdered;
	}
}

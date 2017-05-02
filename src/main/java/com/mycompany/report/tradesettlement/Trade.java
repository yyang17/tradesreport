package com.mycompany.report.tradesettlement;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Class that inherits all attributes from the basic trade and 
 * calculates derived attributes.
 * 
 * @author  Y. Yang
 * @version 1.0
 * @since   2017-05-01
 */
public class Trade extends BasicTrade {
	
	private BigDecimal usdAmount; //USD amount of a trade
	private LocalDate actualSettleDate; //The date on which the trade is actually settled
	
	/**
	 * This method/constructor invokes the constructor of the parent class BasicTrade
	 * and derives values for some more attributes.
	 * @param direction
	 * @param rate
	 * @param currency
	 * @param instructionDate
	 * @param settlementDate
	 * @param units
	 * @param unitPrice
	 */
	public Trade(String entity,
			String direction,
			double rate,
			String currency,
			String instructionDate,
			String settlementDate,
			long units,
			double unitPrice) {
		
		super(entity,
			direction,
			rate,
			currency,
			instructionDate,
			settlementDate,
			units,
			unitPrice);
		
		usdAmount = usdAmount();
		actualSettleDate = actualSettlementDate();
	}

	/**
	 * This method calculates the USD amount of the trade.
	 * @return the USD amount.
	 */
	private BigDecimal usdAmount() {
		return ( new BigDecimal( getUnits() ) ).multiply( getUnitPrice().multiply( getRate() ) );
	}
	
	/**
	 * This method calculates the actual settlement date:
	 * 1. If the currency of a trade is AED or SAR, work week starts Sunday and ends Thursday.
	 * otherwise, work week starts Monday and ends Friday.
	 * 2. A trade can only be settled on a working day.
	 * 3. If settlement date falls on a weekend, then it should be changed to next working day.
	 * @return the actual settlement date of the trade.
	 */
	private LocalDate actualSettlementDate() {
		LocalDate instructedSettleDate = getSettlementDate();
		LocalDate actualSettleDate = instructedSettleDate;
		
		if (instructedSettleDate != null ) {			
			DayOfWeek dayOfWeek = instructedSettleDate.getDayOfWeek();  
			
			switch( getCurrency() ) {
				case "AED":
				case "SAR": if( dayOfWeek == DayOfWeek.FRIDAY ) actualSettleDate = actualSettleDate.plusDays( 2 );
							else if( dayOfWeek == DayOfWeek.SATURDAY ) actualSettleDate = actualSettleDate.plusDays( 1 );
				            break;
				default: if( dayOfWeek == DayOfWeek.SATURDAY ) actualSettleDate = actualSettleDate.plusDays( 2 );
						 else if( dayOfWeek == DayOfWeek.SUNDAY ) actualSettleDate = actualSettleDate.plusDays( 1 );
			}
		} 
		
		return actualSettleDate;
	}
	
	//Define getters
	
	public BigDecimal getUsdAmount() {
		return usdAmount;
	}
	
	public LocalDate getActualSettleDate() {
		return actualSettleDate;
	}
}

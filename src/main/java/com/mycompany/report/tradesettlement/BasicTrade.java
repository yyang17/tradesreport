package com.mycompany.report.tradesettlement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Class that describes a trade to be executed for a financial entity 
 * according to client instructions.
 * 
 * @author  Y. Yang
 * @version 1.0
 * @since   2017-05-01
 */
public class BasicTrade {
	
	private String entity; //Financial entity
	private String direction; //Buy or sell
	private BigDecimal rate; //Foreign exchange rate with respect to USD
	private String currency; //currency in which the trade is to be executed
	private LocalDate instructionDate; //Date on which the instruction was sent by client
	private LocalDate settlementDate; //Date on which the client wished for the trade to be settled
	private long units; //Number of shares to be bought or sold
	private BigDecimal unitPrice; //Price per unit
	
	/**
	 * This constructor creates a basic trade using the attributes passed in.
	 * 
	 * @param entity
	 * @param direction
	 * @param rate
	 * @param currency
	 * @param instructionDate
	 * @param settlementDate
	 * @param units
	 * @param unitPrice
	 */
	public BasicTrade(String entity,
				String direction,
				double rate,
				String currency,
				String instructionDate,
				String settlementDate,
				long units,
				double unitPrice) {
		
		this.entity = entity;
		this.direction = direction;
		this.rate = new BigDecimal( rate );
		this.currency = currency;		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "dd MMM yyyy", Locale.ENGLISH );
		try{
			this.instructionDate = LocalDate.parse( instructionDate, formatter );
			this.settlementDate = LocalDate.parse( settlementDate, formatter );
		} catch( DateTimeParseException e ) {
			System.out.println("Incorrect date format: " + e.getStackTrace() );
		}
		this.units = units;
		this.unitPrice = new BigDecimal( unitPrice );
	}
	
	//Define getters
	
	public String getEntity() {
		return entity;
	}
	
	public String getDirection() {
		return direction;
	}
	
	public BigDecimal getRate() {
		return rate;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public LocalDate getInstructionDate() {
		return instructionDate;
	}
	
	public LocalDate getSettlementDate() {
		return settlementDate;
	}
	
	public long getUnits() {
		return units;
	}
	
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
}

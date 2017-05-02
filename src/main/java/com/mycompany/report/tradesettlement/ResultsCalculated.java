package com.mycompany.report.tradesettlement;

import java.math.BigDecimal;
import java.util.Map;
import java.time.LocalDate;

/**
 * This class represents the calculated settlement amount and rankings.
 * 
 * @author  Y. Yang
 * @version 1.0
 * @since   2017-05-01
 */
public class ResultsCalculated {
	private LocalDate date; //The date for which the settlement amount is to be calculated
	private BigDecimal outgoingAmount = BigDecimal.ZERO; //The settled outgoing amount in USD.
	private BigDecimal incomingAmount = BigDecimal.ZERO; //the settled incoming amount in USD.
	private Map<String, BigDecimal> outgoingRankings = null; //Entity rankings with respect to the outgoing amount.
	private Map<String, BigDecimal> incomingRankings = null; //Entity rankings with respect to the incoming amount.
	
	/**
	 * Constructs the calculated results object.
	 * 
	 * @param date
	 * @param outgoingAmount
	 * @param incomingAmount
	 * @param outgoingRankings
	 * @param incomingRankings
	 */
	public ResultsCalculated( LocalDate date, 
						BigDecimal outgoingAmount, 
						BigDecimal incomingAmount, 
						Map<String, BigDecimal> outgoingRankings, 
						Map<String, BigDecimal> incomingRankings ) {
		this.date = date;
		this.outgoingAmount = outgoingAmount;
		this.incomingAmount = incomingAmount;
		this.outgoingRankings = outgoingRankings;
		this.incomingRankings = incomingRankings;
	}
	
	//Define getters
	
	public BigDecimal getOutgoingAmount() {
		return this.outgoingAmount;
	}
	
	public BigDecimal getIncomingAmount() {
		return this.incomingAmount;
	}
	
	public Map<String, BigDecimal> getOutgoingRankings() {
		return this.outgoingRankings;
	}
	
	public Map<String, BigDecimal> getIncomingRankings() {
		return this.incomingRankings;
	}
	
	public LocalDate getDate() {
		return this.date;
	}
}

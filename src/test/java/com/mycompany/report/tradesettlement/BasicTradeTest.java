package com.mycompany.report.tradesettlement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class BasicTradeTest {
	
	String entity;
	String direction;
	double rate;
	String currency;
	String instructionDate;
	String settlementDate;
	long units;
	double unitPrice;

	public BasicTradeTest(String entity,
			String direction,
			double rate,
			String currency,
			String instructionDate,
			String settlementDate,
			long units,
			double unitPrice) {
		this.entity = entity;
		this.direction = direction;
		this.rate = rate;
		this.currency = currency;
		this.instructionDate = instructionDate;
		this.settlementDate = settlementDate;
		this.units = units;
		this.unitPrice = unitPrice;
	}
	
	//Creates test data
	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] { {"foo", "B", 0.50, "SGP", "01 Ja 2017", "02 Jan 2017", 200, 100.25}, 
								{"sai", "S", 0.50, "CAN", "01 Jan 2017", "02 Jan 17", 100, 100.25}, 
								{"tes", "B", 0.50, "GBP", "01 Jan 2017", "02 January 2017", 100, 100.25},
								{"wai", "S", 0.50, "USD", "2017 Jan 01", "02 Jan 2017", 100, 100.25}};
		return Arrays.asList(data);
	}
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	/**
	 * This method is executed multiple times for each set of parameters above.
	 * Instruction Date and Settlement Date should be of the correct format.
	 */
	@Test(expected = DateTimeParseException.class)
	public void testFormatException() {
		exception.expectMessage( "DateTimeParseException" );
		new BasicTrade(entity, direction, rate, currency, instructionDate, settlementDate, units, unitPrice);
	}

}

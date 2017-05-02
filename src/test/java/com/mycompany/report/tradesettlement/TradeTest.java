package com.mycompany.report.tradesettlement;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class TradeTest {

	@Parameter(0)
	public String entity;
	@Parameter(1)
	public String direction;
	@Parameter(2)
	public double rate;
	@Parameter(3)
	public String currency;
	@Parameter(4)
	public String instructionDate;
	@Parameter(5)
	public String settlementDate;
	@Parameter(6)
	public long units;
	@Parameter(7)
	public double unitPrice;
	@Parameter(8)
	public double expectedUsdAmount;
	@Parameter(9)
	public String expectedSettleDate;
	
	/**
	 * Creates test data for trades of currency AED or SAR and other currencies.
	 * @return
	 */
	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] { {"foo", "B", 0.20, "SGP", "01 Jan 2017", "02 Jan 2017", 200, 100.25, 4010, "02 Jan 2017"}, //"02 Jan 2017" Monday 
									{"sai", "S", 0.50, "CAN", "01 Jan 2017", "07 Jan 2017", 150, 99.75, 7481.25, "09 Jan 2017"}, //"07 Jan 2017" Saturday
									{"sai", "S", 1.0, "GBP", "01 Jan 2017", "08 Jan 2017", 150, 99.75, 14962.5, "09 Jan 2017"}, //"08 Jan 2017" Sunday
									{"tes", "B", 0.85, "AED", "01 Jan 2017", "05 Jan 2017", 300, 98.00, 24990, "05 Jan 2017"}, //"05 Jan 2017" Thursday
									{"tes", "S", 0.85, "AED", "01 Jan 2017", "06 Jan 2017", 300, 100.00, 25500, "08 Jan 2017"}, //"06 Jan 2017" Friday
									{"wai", "B", 0.79, "SAR", "01 Jan 2017", "07 Jan 2017", 220, 101.25, 17597.25, "08 Jan 2017"}}; //"07 Jan 2017" Saturday
		return Arrays.asList(data);
	}
	
	@Test
	public void testUsdAmount() {
		Trade trade = new Trade(entity, direction, rate, currency, instructionDate, settlementDate, units, unitPrice);
		assertEquals( "USD Amount", expectedUsdAmount, trade.getUsdAmount().doubleValue(), 2 );
	}

	/**
	 * Run multiple times on the test data. The below cases are tested:
	 * Currency = AED, SettleDate is Thursday
	 * Currency = AED, SettleDate is Friday
	 * Currency = SAR, SettleDate is Saturday
	 * Currency = Non AED/SAR, SettleDate is Monday
	 * Currency = Non AED/SAR, SettleDate is Saturday
	 * Currency = Non AED/SAR, SettleDate is Sunday
	 */
	@Test
	public void testActualSettlementDate() {
		Trade trade = new Trade(entity, direction, rate, currency, instructionDate, settlementDate, units, unitPrice);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "dd MMM yyyy", Locale.ENGLISH );
		LocalDate settleDate = LocalDate.parse( expectedSettleDate, formatter );

		assertTrue( "Actual Settlement Date", settleDate.isEqual( trade.getActualSettleDate() ) );
	}

}

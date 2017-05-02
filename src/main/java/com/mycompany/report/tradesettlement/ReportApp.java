package com.mycompany.report.tradesettlement;

import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.time.LocalDate;
import java.util.LinkedList;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * This is the main class for generating a report based on the list of trades.
 * 
 * @author  Y. Yang
 * @version 1.0
 * @since   2017-05-01
 *
 */
public class ReportApp {

	//Set sample data
	private static LinkedList<Trade> trades = new LinkedList<Trade>( Arrays.asList(
			//Entity, Buy/Sell, AgreedFx, Currency, InstructionDate, SettlementDate, Units, Price per unit 
			new Trade( "foo", "B", 0.50, "SGP", "01 Jan 2017", "02 Jan 2017", 200, 100.25 ),
			new Trade( "bar", "S", 0.22, "AED", "05 Jan 2017", "08 Jan 2017", 450, 150.5 ),
			new Trade( "foo", "B", 1.30, "GBP", "01 Jan 2017", "02 Jan 2017", 200, 110.25 ),
			new Trade("tes", "S", 0.68, "SAR", "05 Jan 2017", "09 Jan 2017", 400, 130.5 ),
			new Trade("wai", "S", 0.34, "CAN", "05 Jan 2017", "07 Jan 2017", 280, 120.5 ),
			new Trade("sai", "B", 0.34, "CAN", "05 Jan 2017", "07 Jan 2017", 460, 120.5 )
	) );
	
	/**
	 * This main method does calculations on the list of trades and 
	 * generates a report of the calculated results.
	 * 
	 * @param args
	 * @throws DateTimeParseException
	 */
	public static void main(String[] args) throws DateTimeParseException {		
		//Get the current date for which the report is to be generated
		LocalDate date = LocalDate.now();  
		//date = LocalDate.parse( "09 Jan 2017", DateTimeFormatter.ofPattern( "dd MMM yyyy", Locale.ENGLISH ) );
		
		( new ReportGenerator( trades, date ) ).displayReport();
	}
}

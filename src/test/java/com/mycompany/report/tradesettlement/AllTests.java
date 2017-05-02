package com.mycompany.report.tradesettlement;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BasicTradeTest.class, TradesCalculationTest.class, TradeTest.class })
public class AllTests {

}

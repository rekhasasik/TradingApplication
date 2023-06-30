package com.trade.signal.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SignalProcessorTest {
	private final PrintStream standardOut = System.out;
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

	@BeforeEach
	public void setUp() {
	    System.setOut(new PrintStream(outputStreamCaptor));
	}
	
	@AfterEach
	public void tearDown() {
	    System.setOut(standardOut);
	}
	
	@Test
	void testprocessNullSignal() {
		SignalProcessor processor = new SignalProcessor();
		assertThrows(NullPointerException.class, () -> processor.handleSignal(null, null));
		
	}
	
	@Test
	void testProcessSignal1() {
		SignalProcessor processor = new SignalProcessor();
		processor.handleSignal(1, null);
		 assertTrue(outputStreamCaptor.toString().contains("setUp"));
		 assertTrue(outputStreamCaptor.toString().contains("setAlgoParam 1,60"));
		 assertTrue(outputStreamCaptor.toString().contains("performCalc"));
		 assertTrue(outputStreamCaptor.toString().contains("submitToMarket"));
		 assertTrue(outputStreamCaptor.toString().contains("doAlgo"));
	}
	
	@Test
	void testProcessSignal3() {
		SignalProcessor processor = new SignalProcessor();
		processor.handleSignal(3, null);
		assertTrue(outputStreamCaptor.toString().contains("setAlgoParam 1,90"));
		 assertTrue(outputStreamCaptor.toString().contains("setAlgoParam 2,15"));
		 assertTrue(outputStreamCaptor.toString().contains("performCalc"));
		 assertTrue(outputStreamCaptor.toString().contains("submitToMarket"));
		 assertTrue(outputStreamCaptor.toString().contains("doAlgo"));
	}
	
	@Test
	void testProcessSignal2() {
		SignalProcessor processor = new SignalProcessor();
		processor.handleSignal(2, null);
		 assertTrue(outputStreamCaptor.toString().contains("reverse"));
		 assertTrue(outputStreamCaptor.toString().contains("setAlgoParam 1,80"));
		 assertTrue(outputStreamCaptor.toString().contains("submitToMarket"));
		 assertTrue(outputStreamCaptor.toString().contains("doAlgo"));
	}
	
	@Test
	void testProcessSignalDefault() {
		SignalProcessor processor = new SignalProcessor();
		processor.handleSignal(100, null);
		 assertTrue(outputStreamCaptor.toString().contains("cancelTrades"));
		 assertTrue(outputStreamCaptor.toString().contains("doAlgo"));
	}
	

}

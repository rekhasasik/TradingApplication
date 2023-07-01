package com.trade.command.executor;

import static com.trade.util.TestUtil.signal1;
import static com.trade.util.TestUtil.signal2;
import static com.trade.util.TestUtil.signal3;
import static com.trade.util.TestUtil.signalDefault;
import static com.trade.util.TestUtil.signalWithInvalidCommandName;
import static com.trade.util.TestUtil.signalWithInvalidParams;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.trade.exception.BadInputException;

@SpringBootTest
public class CommandExecutorTest {
	
	@Autowired
	private CommandExecutor commandExecutor;
	
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
	@DisplayName("Should not throw an exception")
	void testExecuteForEmptyCommands() {
		assertDoesNotThrow(() -> commandExecutor.execute(null));
	}
	
	@Test
	void testExecuteSignal1() {
		commandExecutor.execute(signal1());
		 assertTrue(outputStreamCaptor.toString().contains("setUp"));
		 assertTrue(outputStreamCaptor.toString().contains("setAlgoParam 1,60"));
		 assertTrue(outputStreamCaptor.toString().contains("performCalc"));
		 assertTrue(outputStreamCaptor.toString().contains("submitToMarket"));	}
	
	@Test
	void testExecuteSignal3() {
		commandExecutor.execute(signal3());
		assertTrue(outputStreamCaptor.toString().contains("setAlgoParam 1,90"));
		 assertTrue(outputStreamCaptor.toString().contains("setAlgoParam 2,15"));
		 assertTrue(outputStreamCaptor.toString().contains("performCalc"));
		 assertTrue(outputStreamCaptor.toString().contains("submitToMarket"));
	}
	
	@Test
	void testExecuteSignal2() {
		 commandExecutor.execute(signal2());
		 assertTrue(outputStreamCaptor.toString().contains("reverse"));
		 assertTrue(outputStreamCaptor.toString().contains("setAlgoParam 1,80"));
		 assertTrue(outputStreamCaptor.toString().contains("submitToMarket"));
	}
	
	@Test
	void testExecuteSignalDefault() {
		 commandExecutor.execute(signalDefault());
		 assertTrue(outputStreamCaptor.toString().contains("cancelTrades"));
	}
	
	@Test
	void testExecuteSignalWithInvalidParams() {
		 assertThrows(BadInputException.class, () -> commandExecutor.execute(signalWithInvalidParams()));
	}
	
	@Test
	void testExecuteSignalWithInvalidCommand() {
		 BadInputException exp = assertThrows(BadInputException.class, () -> commandExecutor.execute(signalWithInvalidCommandName()));
		 assertEquals("Command setParam Invalid", exp.getMessage());
	}

		
	

}

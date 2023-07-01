package com.trade.adaptor;

import static com.trade.util.TestUtil.commandWithInvalidName;
import static com.trade.util.TestUtil.commandWithInvalidParams;
import static com.trade.util.TestUtil.doAlgo;
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
public class AlgoAdapterTest {
	
	@Autowired
	private AlgoAdapter adaptor;
	
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
	@DisplayName("Should throw an exception")
	void testExecuteForEmptyCommands() {
		assertThrows(BadInputException.class, () -> adaptor.execute(null));
		
	}
	
	@Test
	void testExecuteSignal() {
		adaptor.execute(doAlgo());
		 assertTrue(outputStreamCaptor.toString().contains("doAlgo"));
	}
	
	@Test
	void testExecuteSignalWithInvalidParams() {
		 assertThrows(BadInputException.class, () -> adaptor.execute(commandWithInvalidParams()));
	}
	
	@Test
	void testExecuteSignalWithInvalidCommand() {
		 BadInputException exp = assertThrows(BadInputException.class, () -> adaptor.execute(commandWithInvalidName()));
		 assertEquals("Command setParam Invalid", exp.getMessage());
	}
}

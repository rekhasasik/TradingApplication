package com.trade.signal.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.trade.command.Command;
import com.trade.exception.BadInputException;

@SpringBootTest
public class SignalConfigTest {
	
	@Autowired
	private SignalConfig config;
	
	@Test
	void testValidConfigForSignal() {
		List<Command> commands = config.getCommands(1);
		assertNotNull(commands);
		assertEquals(4, commands.size());
		
	}
	

	@Test
	void testValidConfigForSignal10() {
		List<Command> commands = config.getCommands(10);
		assertNotNull(commands);
		assertEquals(4, commands.size());
		
	}
	
	@Test
	void testValidConfigForSignal3() {
		List<Command> commands = config.getCommands(3);
		assertNotNull(commands);
		assertEquals(4, commands.size());
		
	}
	
	@Test
	void testValidConfigForDefaultSignal() {
		List<Command> commands = config.getCommands(-1);
		assertNotNull(commands);
		assertEquals(1, commands.size());
		assertEquals("cancelTrades", commands.get(0).getName());
		
	}
	
	@Test
	void testException() {
		BadInputException exp =  assertThrows(BadInputException.class, () -> config.getCommands(null));
		assertEquals("No Input Signal to Match", exp.getMessage());
	}

}

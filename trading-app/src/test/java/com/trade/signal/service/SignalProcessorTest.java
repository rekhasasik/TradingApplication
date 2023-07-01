package com.trade.signal.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import com.trade.command.Command;
import com.trade.command.executor.CommandExecutor;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SignalProcessorTest {
	
	
	
	@SpyBean
	private CommandExecutor commandExecutor;

	@InjectMocks 
	private SignalProcessor processor;
	
	
	
	@Test
	void testprocessNullSignal() {
		assertThrows(NullPointerException.class, () -> processor.handleSignal(null, null));
		
	}
	
	@Test
	void testProcessSignal1() {
		 processor.handleSignal(1, null);
		 verify(commandExecutor).execute(argThat((List<Command> commands) -> commands.size()== 4));
	}
	
	@Test
	void testProcessSignal2() {
		 processor.handleSignal(2, null);
		 verify(commandExecutor).execute(argThat((List<Command> commands) -> commands.size()== 3));
	}
	
	@Test
	void testProcessSignal3() {
		 processor.handleSignal(3, null);
		 verify(commandExecutor).execute(argThat((List<Command> commands) -> commands.size()== 4));
	}
	
	@Test
	void testProcessSignaDefault() {
		 processor.handleSignal(5, null);
		 verify(commandExecutor, times(2)).execute(argThat((List<Command> commands) -> commands.size()== 1));
	}
	
}

package com.trade.signal.service;

import static com.trade.util.TestUtil.signal1;
import static com.trade.util.TestUtil.signal2;
import static com.trade.util.TestUtil.signal3;
import static com.trade.util.TestUtil.signalDefault;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import com.trade.command.Command;
import com.trade.command.executor.CommandExecutor;
import com.trade.signal.config.SignalConfig;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SignalProcessorTest {

	@SpyBean
	private CommandExecutor commandExecutor;

	@MockBean
	private SignalConfig signalConfig;

	@InjectMocks
	private SignalProcessor processor;

	@Test
	void testprocessNullSignal() {
		assertThrows(IllegalArgumentException.class, () -> processor.handleSignal(null, null));

	}

	@Test
	void testProcessSignal1() {
		doReturn(signal1()).when(signalConfig).getCommands(eq(1));
		 processor.handleSignal(1, null);
		 verify(commandExecutor).execute(argThat((List<Command> commands) -> commands.size()== 4));
	}

	@Test
	void testProcessSignal2() {
		doReturn(signal2()).when(signalConfig).getCommands(eq(2));
		processor.handleSignal(2, null);
		verify(commandExecutor).execute(argThat((List<Command> commands) -> commands.size() == 3));
	}

	@Test
	void testProcessSignal3() {
		doReturn(signal3()).when(signalConfig).getCommands(eq(3));
		processor.handleSignal(3, null);
		verify(commandExecutor).execute(argThat((List<Command> commands) -> commands.size() == 4));
	}

	@Test
	void testProcessSignaDefault() {
		doReturn(signalDefault()).when(signalConfig).getCommands(eq(10));
		processor.handleSignal(10, null);
		verify(commandExecutor, times(2)).execute(argThat((List<Command> commands) -> commands.size() == 1));
	}

}

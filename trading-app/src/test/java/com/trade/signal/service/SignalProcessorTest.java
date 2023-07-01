package com.trade.signal.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import com.trade.adaptor.AlgoAdapter;
import com.trade.command.Command;
import com.trade.factory.BeanFactory;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SignalProcessorTest {
	
	@Mock
	private BeanFactory beanFactory;
	
	@SpyBean
	private AlgoAdapter adapter;

	@InjectMocks 
	private SignalProcessor processor;
	
	
	
	@Test
	void testprocessNullSignal() {
		assertThrows(NullPointerException.class, () -> processor.handleSignal(null, null));
		
	}
	
	@Test
	void testProcessSignal1() {
		doReturn(adapter).when(beanFactory).getCommandExecutor(refEq("algo"));
		 processor.handleSignal(1, null);
		 verify(adapter).execute(argThat((List<Command> commands) -> commands.size()== 4));
	}
	
	@Test
	void testProcessSignal2() {
		doReturn(adapter).when(beanFactory).getCommandExecutor(refEq("algo"));
		 processor.handleSignal(2, null);
		 verify(adapter).execute(argThat((List<Command> commands) -> commands.size()== 3));
	}
	
	@Test
	void testProcessSignal3() {
		doReturn(adapter).when(beanFactory).getCommandExecutor(refEq("algo"));
		 processor.handleSignal(3, null);
		 verify(adapter).execute(argThat((List<Command> commands) -> commands.size()== 4));
	}
	
	@Test
	void testProcessSignaDefault() {
		doReturn(adapter).when(beanFactory).getCommandExecutor(refEq("algo"));
		 processor.handleSignal(0, null);
		 verify(adapter).execute(argThat((List<Command> commands) -> commands.size()== 1));
	}
	
	/*
	
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
	*/

}

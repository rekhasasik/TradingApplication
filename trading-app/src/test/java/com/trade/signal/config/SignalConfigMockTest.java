package com.trade.signal.config;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.trade.util.ResourceUtil;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SignalConfigMockTest {
	
	@Test
	void testInvalidInput() {
		ResourceUtil resourceUtil = mock(ResourceUtil.class);
		doReturn("").when(resourceUtil).getResourceAsString(anyString());
		SignalConfig config = new SignalConfig(resourceUtil);
		assertThrows(IllegalArgumentException.class , () -> config.getCommands(1));
	}

}

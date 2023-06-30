package com.trade.signal.handler;

import java.util.Map;

import org.springframework.lang.Nullable;

/**
 * Signal Handler Interface which can be implemented by Signal Process or any other services in future
 * @author Sasi Rekha
 *
 */
public interface ISignalHandler {
	
	void handleSignal(Integer signalId, @Nullable Map<String, Object> params);

}

package com.trade.signal.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.trade.signal.handler.ISignalHandler;

import com.trade.algo.Algo;


/**
 * Service that Process Signal
 * @author Sasi Rekha
 *
 */
@Service("process")
public class SignalProcessor implements ISignalHandler {
	private static final Logger logger = LoggerFactory.getLogger(SignalProcessor.class);
	
	@Override
	public void handleSignal(@NonNull Integer signalId, @Nullable Map<String, Object> params) {
		
        Algo algo = new Algo();
		
		switch (signalId) {
        case 1:
            algo.setUp();
            algo.setAlgoParam(1,60);
            algo.performCalc();
            algo.submitToMarket();
            break;

        case 2:
            algo.reverse();
            algo.setAlgoParam(1,80);
            algo.submitToMarket();
            break;

        case 3:
            algo.setAlgoParam(1,90);
            algo.setAlgoParam(2,15);
            algo.performCalc();
            algo.submitToMarket();
            break;

        default:
            algo.cancelTrades();
            break;
    }

    algo.doAlgo();
}


	

}

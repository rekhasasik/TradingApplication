package com.trade.adaptor;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.trade.algo.Algo;
import com.trade.command.Command;
import com.trade.command.Handler;
import com.trade.exception.BadInputException;

/**
 * Executes commands against Algo class to process the signal
 * The respective handler implementation for each method of Algo is dynamically implemented and stored in a map
 * for easy lookup.
 * Command name decides the method that needs to be invoked on Algo class
 * Since the methods of Algo class are simple with in the current scope, handler
 * is implemented and stored in this way. 
 * Separate handlers can be created for each of the method of Algo 
 * those handle series of commands of a signal
 * 
 * @author Sasi Rekha
 *
 */
@Component("algo")
public class AlgoAdapter implements IAdaptor {
	
	
	private Map<String, Handler<Algo>> handlerMap = new HashMap<>();
	
	@PostConstruct
	private void loadHandlerMap() {
		handlerMap.put("doAlgo",  (command, t) -> 
			{
			
				t.doAlgo();
			}
		);
		
		handlerMap.put("setAlgoParam",  (command, t) -> 
		{
		    
			t.setAlgoParam(castToInteger(command.getParams().get("param")),castToInteger(command.getParams().get("value")));
		});
		
		handlerMap.put("cancelTrades",  (command, t) -> 
		{
		    
			t.cancelTrades();
		});
		
		handlerMap.put("reverse",  (command, t) -> 
		{
		    
			t.reverse();
		});
		
		handlerMap.put("submitToMarket",  (command, t) -> 
		{
		    
			t.submitToMarket();
		});
		
		handlerMap.put("performCalc",  (command, t) -> 
		{
		    
			t.performCalc();
		});
		
		handlerMap.put("setUp",  (command, t) -> 
		{
		    
			t.setUp();
		});
	}
	
	

	@Override
	public void execute(Command command) {
		    Algo algo = new Algo();
			if(null == command) {
				throw new BadInputException("NULL Command");
			}
			
			
				
				if(null == handlerMap.get(command.getName())) {
					throw new BadInputException("Command "+command.getName()+" Invalid");
				}
				
				handlerMap.get(command.getName()).handle(command , algo);
		
	}
	
	
	private Integer castToInteger(Object o) {
		if(o == null) {
			throw new BadInputException("cannot cast to Integer");
		}
		try {
			return (Integer)o;
		}catch(Exception e) {
			throw new BadInputException(e.getMessage());
		}
	}





}

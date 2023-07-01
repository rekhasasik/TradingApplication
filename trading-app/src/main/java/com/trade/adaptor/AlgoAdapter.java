package com.trade.adaptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.trade.algo.Algo;
import com.trade.command.Command;
import com.trade.command.Handler;
import com.trade.command.ICommandExecutor;
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
public class AlgoAdapter implements ICommandExecutor {
	
	private static final Logger logger = LoggerFactory.getLogger(AlgoAdapter.class);
	
	
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
	public void execute(List<Command> command) {
		    Algo algo = new Algo();
			if(CollectionUtils.isEmpty(command)) {
				logger.warn("No commands to process");
				return;
			}
			
			command.forEach(c ->  {
				
				if(null == handlerMap.get(c.getName())) {
					throw new BadInputException("Command "+c.getName()+" Invalid");
				}
				
				handlerMap.get(c.getName()).handle(c , algo);
			});
			Command doCommand = doAlgoCommand();
			handlerMap.get(doCommand.getName()).handle(doCommand, algo);
	}
	
	private Command doAlgoCommand() {
		return new Command("doAlgo", "algo", null);
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

package com.trade.signal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.trade.command.Command;
import com.trade.command.executor.CommandExecutor;
import com.trade.exception.BadInputException;
import com.trade.signal.handler.ISignalHandler;


/**
 * Service that Process Signal
 * @author Sasi Rekha
 *
 */
@Service("process")
public class SignalProcessor implements ISignalHandler {
	
	@Autowired
	private CommandExecutor commandExecutor;
	
	
	
	/**
	 * Executes the Given Signal. If signalId doesn't match given cases, then executed default block
	 */
	
	@Override
	public void handleSignal(@NonNull Integer signalId, @Nullable Map<String, Object> params) {
		
		  
				
		switch (signalId) {
        case 1:
        	execute(buildSignal1(), 1);
        	break;
        	
        case 2:
        	execute(buildSignal2(), 2);
            break;

        case 3:
        	execute(buildSignal3(), 3);
            break;

        default:
        	execute(signalDefault(), null);
            break;
    }
		
	execute(postProcessingSignal(), signalId);

   
}

	
	
	
	
	/**
	 * Picks the right ICommandExecutor based on type of command and executes the commands of signal
	 * The current design only takes care of AlgoAdapter as its the only class that is available
	 * However In Future when there will be multiple @Algo classes, 
	 * the logic groups Commands based on CommandExecutor and execute them
	 * @param commands
	 * @param Signal
	 */
	
	private void execute(List<Command> commands, Integer Signal) {
		if(CollectionUtils.isEmpty(commands)) {
			throw new BadInputException("No Commands for "+((Signal == null)?"default":Integer.toString(Signal)));
		}
		commandExecutor.execute(commands);
	}
	
	
	private List<Command> buildSignal1() {
		List<Command> commands = new ArrayList<>();
		Command command = new Command("setUp", "algo", null);
		commands.add(command);
		Map<String, Object> params = new HashedMap<>();
		params.put("param", 1);
		params.put("value", 60);
		command = new Command("setAlgoParam", "algo", params);
		commands.add(command);
		command = new Command("performCalc", "algo", null);
		commands.add(command);
		command = new Command("submitToMarket", "algo", null);
		commands.add(command);
		return commands;
			
	}
	
	private List<Command> buildSignal2() {
		List<Command> commands = new ArrayList<>();
		Command command = new Command("reverse", "algo", null);
		commands.add(command);
		Map<String, Object> params = new HashedMap<>();
		params.put("param", 1);
		params.put("value", 80);
		command = new Command("setAlgoParam", "algo", params);
		commands.add(command);
		command = new Command("submitToMarket", "algo", null);
		commands.add(command);
		return commands;
		
		
	}
	
	private List<Command> buildSignal3() {
		List<Command> commands = new ArrayList<>();
		Map<String, Object> params = new HashedMap<>();
		params.put("param", 1);
		params.put("value", 90);
		Command command = new Command("setAlgoParam", "algo", params);
		commands.add(command);
		params = new HashedMap<>();
		params.put("param", 2);
		params.put("value", 15);
		command = new Command("setAlgoParam", "algo", params);
		commands.add(command);
		command = new Command("performCalc", "algo", null);
		commands.add(command);
		command = new Command("submitToMarket", "algo", null);
		commands.add(command);
		return commands;
		
		
	}
	
	private List<Command> signalDefault() {
		List<Command> commands = new ArrayList<>();
		Command command = new Command("cancelTrades", "algo", null);
		commands.add(command);
		return commands;
		
		
	}
	
	private List<Command> postProcessingSignal() {
		List<Command> commands = new ArrayList<>();
		Command command = new Command("doAlgo", "algo", null);
		commands.add(command);
		return commands;
		
		
	}


	

}

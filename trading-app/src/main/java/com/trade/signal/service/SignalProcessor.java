package com.trade.signal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.trade.command.Command;
import com.trade.command.executor.CommandExecutor;
import com.trade.signal.config.SignalConfig;
import com.trade.signal.handler.ISignalHandler;


/**
 * Service that Process Signal
 * @author Sasi Rekha
 *
 */
@Service("process")
public class SignalProcessor implements ISignalHandler {
	
	@Autowired
	private SignalConfig signalConfig;
	
	@Autowired
	private CommandExecutor commandExecutor;
	
	
	/**
	 * Executes the Given Signal. If signalId doesn't match given config, then default config is executed
	 * Config files are of json format under resources folder and contains the information about a signal in the form of commands
	 * An Optional params is passed to method to handle overriding input or similar use cases in future
	 */
	
	@Override
	public void handleSignal(@NonNull Integer signalId, @Nullable Map<String, Object> params) {
		
	
		execute(signalConfig.getCommands(signalId), signalId);
	
		
		execute(postProcessingSignal(), signalId);

   
	}

	
	
	
	
	/**
	 * passes on the List of commands to COmmand Executor to execute them
	 * @param commands
	 * @param Signal
	 */
	
	private void execute(List<Command> commands, Integer Signal) {
		if(CollectionUtils.isEmpty(commands)) {
			throw new IllegalArgumentException("No Commands for "+Signal);
		}
		commandExecutor.execute(commands);
	}
	
	
	
	private List<Command> postProcessingSignal() {
		List<Command> commands = new ArrayList<>();
		Command command = new Command("doAlgo", "algo", null);
		commands.add(command);
		return commands;
		
		
	}
	

}

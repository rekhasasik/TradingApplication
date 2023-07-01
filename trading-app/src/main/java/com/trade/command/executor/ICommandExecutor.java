package com.trade.command.executor;

import java.util.List;

import com.trade.command.Command;

/**
 * Executes a given command from an adaptor class
 * Return Type can be changed as generic response for future enhancements
 * @author Sasi Rekha
 *
 */
public interface ICommandExecutor {
	
	void execute(List<Command> command);

}

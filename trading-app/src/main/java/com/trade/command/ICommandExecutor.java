package com.trade.command;

import java.util.List;

/**
 * Executes a given command from an adaptor class
 * Return Type can be changed as generic response for future enhancements
 * @author Sasi Rekha
 *
 */
public interface ICommandExecutor {
	
	void execute(List<Command> command);

}

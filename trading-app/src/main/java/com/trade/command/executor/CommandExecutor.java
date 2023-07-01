package com.trade.command.executor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.trade.adaptor.IAdaptor;
import com.trade.command.Command;
import com.trade.exception.BadInputException;
import com.trade.factory.BeanFactory;

/**
 * Runs commands in given order
 * @author Sasi Rekha
 *
 */
@Service("default")
public class CommandExecutor implements ICommandExecutor{
	

	@Autowired 
	private BeanFactory beanFactory;

	@Override
	public void execute(List<Command> commands) {
		if(CollectionUtils.isEmpty(commands)) {
			return;
		}
		
		commands.stream().forEach(c -> getAdaptor(c.getType()).execute(c));
		
	}
	
	
	/**
	 * Picks the right Adaptor class based on type of command and executes the command of signal
	 * @param command
	 * @return
	 */
	private IAdaptor getAdaptor(String type) {
		if(type == null) {
			throw new BadInputException("Null Executor Input");
		}
		return beanFactory.getAdaptor(type);
	}

}

package com.trade.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.trade.command.ICommandExecutor;

@Component
public class BeanFactory {
	
	private final ApplicationContext context;
	
	public BeanFactory(ApplicationContext context) {
		this.context = context;
	}
	
	public ICommandExecutor getCommandExecutor(final String name) {
		return context.getBean(name, ICommandExecutor.class);
	}

}

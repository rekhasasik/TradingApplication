package com.trade.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.trade.adaptor.IAdaptor;

@Component
public class BeanFactory {
	
	private final ApplicationContext context;
	
	public BeanFactory(ApplicationContext context) {
		this.context = context;
	}
	
	public IAdaptor getAdaptor(final String name) {
		return context.getBean(name, IAdaptor.class);
	}

}

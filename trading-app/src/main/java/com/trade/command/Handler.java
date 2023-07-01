package com.trade.command;

@FunctionalInterface
public interface Handler<T> {
	void handle(Command command, T t );

}

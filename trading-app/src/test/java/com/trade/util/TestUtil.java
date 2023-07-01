package com.trade.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

import com.trade.command.Command;

public class TestUtil {
	
	public static List<Command> signal1() {
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
	
	public static List<Command> signal2() {
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
	
	public static List<Command> signal3() {
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
	
	public static List<Command> signalDefault() {
		List<Command> commands = new ArrayList<>();
		Command command = new Command("cancelTrades", "algo", null);
		commands.add(command);
		return commands;
		
		
	}
	
	public static List<Command> signalWithInvalidParams() {
		List<Command> commands = new ArrayList<>();
		Command command = new Command("setUp", "algo", null);
		commands.add(command);
		Map<String, Object> params = new HashedMap<>();
		params.put("param", 1.5);
		params.put("value", 60);
		command = new Command("setAlgoParam", "algo", params);
		commands.add(command);
		return commands;
	}
	
	public static List<Command> signalWithInvalidCommandName() {
		List<Command> commands = new ArrayList<>();
		Command command = new Command("setUp", "algo", null);
		commands.add(command);
		Map<String, Object> params = new HashedMap<>();
		params.put("param", 1.5);
		params.put("value", 60);
		command = new Command("setParam", "algo", params);
		commands.add(command);
		return commands;
	}

}

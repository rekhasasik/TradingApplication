package com.trade.signal.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trade.command.Command;
import com.trade.exception.BadInputException;
import com.trade.util.ResourceUtil;


/**
 * Picks up the matching config from the config file for a given signalId
 * config files are json files under signal-config directory of resource folder
 * Typical Config spec: 
 * {
		"signal": 1,  -- signal Id to be process
		"commands":[   -- List of instructions to be executed for signal
			{
				"name":"setUp",  
				"type": "algo"
			},
			{
				"name":"setAlgoParam",
				"type": "algo",
				"params": {
					"param":1,
					"value":60
				}
			},
			{
				"name":"performCalc",
				"type": "algo"
			},
			{
				"name":"submitToMarket",
				"type": "algo"
			}			
		]
	
 * @author Sasi Rekha
 *
 */
@Component
public class SignalConfig {
	
	@Value("${signal.plan.path:signal-config}") String signalPlanPath;
	@Value("classpath:signal-config/*")
	private Resource[] configs;
	
	private static final Logger logger = LoggerFactory.getLogger(SignalConfig.class);
	
	
	private final ResourceUtil resourceUtil;
	
	public SignalConfig(ResourceUtil resourceUtil) {
		this.resourceUtil = resourceUtil;
	}
	
	/**
	 * Gets the series of commands matching signal
	 * if no match found then default signal commands are returned
	 * @param signal
	 * @return
	 */
	public List<Command> getCommands(Integer signal) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<Signal> signals = mapper.readValue(resourceUtil.getResourceAsString(signalPlanPath+File.separator+getConfigFileName(signal)),
					new TypeReference<List<Signal>>() {
					});
			return matchSignal(signal, signals);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
			throw new IllegalArgumentException(String.format("Failed to parse Resource %s",signalPlanPath), e);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
			throw new IllegalArgumentException(String.format("Failed to parse Resource %s",signalPlanPath), e);
		}
	}
	
	private List<Command> matchSignal(Integer signal, List<Signal> signals) {
		if(null == signal) {
			throw new BadInputException("No Input Signal to Match");
		}
		if(CollectionUtils.isEmpty(signals)) {
			logger.warn("No Config defined");
			return new ArrayList<>();
		}
		return signals.stream().filter(s -> s.getSignal().equals(signal)).findFirst().map(Signal::getCommands).orElse(getDefaultCommands());
	}
	
	private List<Command> getDefaultCommands() {
		ObjectMapper mapper = new ObjectMapper();	
		try {
			List<Signal> signals = mapper.readValue(resourceUtil.getResourceAsString(signalPlanPath+File.separator+getDefaultFileName()),
					new TypeReference<List<Signal>>() {
					});
			return signals.get(0).getCommands();
		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
			throw new IllegalArgumentException(String.format("Failed to parse Resource %s",getDefaultFileName()), e);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
			throw new IllegalArgumentException(String.format("Failed to parse Resource %s",getDefaultFileName()), e);
		}
		
		
	}
	
	/**
	 * This method can help pick config file that contains the signal information.
	 * TODO: Should be implemented in Future when the number of signals are more than 1000. 
	 * For now defaulting to only available file
	 * Example Signal 3 should be in a file with suffix 1-100, else signal-config-default should be returned
	 * @param Signal
	 * @return
	 */
	private String getConfigFileName(Integer Signal) {
		return "signalConfig-1-100.json";
	}
	
	/**
	 * returns default config file name
	 * @return
	 */
	private String getDefaultFileName() {
		return "signalConfig-default.json";
	}

}

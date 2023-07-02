package com.trade.command;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Each Command object contains
 * name - matching method in @Algo class
 * Type - Adaptor class to be applied. "algo" -> @AlgoAdapter class
 * params - paramaters that need to be applied to the target method of @Algo class
 * @author Sasi Rekha
 *
 */

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class Command {
	
	@JsonProperty("name")
	private String name;
	@JsonProperty("type")
	private String type;
	@JsonProperty("params")
	private Map<String, Object> params;
	

}

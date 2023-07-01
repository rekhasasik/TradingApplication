package com.trade.signal.controller;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.trade.signal.handler.ISignalHandler;

/**
 * Rest Controller classs the deals with HTTP Requests of Signal
 * @author Sasi Rekha
 *
 */

@RestController
@RequestMapping("/signal")
public class SignalController {
	
	@Autowired
	@Qualifier("process")
	private ISignalHandler signalProcessor;
	
	/**
	 * Created this method for testing the controller
	 * @return
	 */
	@GetMapping("/ping")
	public String ping() {
		return "pong";
	}
	
	/**
	 * Post method that process a signal with provided signalId with an optional request body
	 * @param signalId
	 * @return Success in case smooth Execution
	 * else return Internal Server Error with relevant Exception message
	 */
	@PostMapping(value="/{signalId}", produces = TEXT_PLAIN_VALUE)
	public ResponseEntity<String> processSignal(@PathVariable(value = "signalId") Integer signalId,
			@RequestBody(required=false) Map<String, Object> body) {
		try {
			signalProcessor.handleSignal(signalId, body);
			return new ResponseEntity<>("Success", HttpStatus.OK);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
		

		
	}

}

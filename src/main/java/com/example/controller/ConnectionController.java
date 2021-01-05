package com.example.controller;

import java.util.Collections;
import java.util.HashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.constants.ConnectionConstants;
import com.example.service.ConnectionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for city-connection application
 */
@RestController
@Api(value = "City connection application")
public class ConnectionController {
	
	private final Log LOG = LogFactory.getLog(getClass());
	
	@Autowired
	private ConnectionService connectionService;
	
    @ApiOperation(value = "To find a connection between two cities",
            notes = "Returns yes if cites connected otherwise returns no",
            response = String.class)
    @GetMapping("/connected")
    public String isConnected(
        @ApiParam(name = "origin", value = "Origin City name", required = true) @RequestParam String origin, 
        @ApiParam(name = "destination", value = "Destination City name", required = true) @RequestParam String destination) {

      if(StringUtils.isEmpty(origin) || StringUtils.isEmpty(destination)) {
        LOG.info(String.format("City is Empty: %s, %s", origin, destination));
        return ConnectionConstants.NO;
      }

      return connectionService.isConnected(origin.toLowerCase(), "", destination.toLowerCase(), new HashSet<>(Collections.singleton(origin)));
    }
    
    @ApiOperation(value = "To find a connection between two cities with a maximum of one stop",
            notes = "Returns yes if cites connected otherwise returns no",
            response = String.class)
    @GetMapping("/connectedWithOneStop")
    public String isConnectedWithOneStop(
        @ApiParam(name = "origin", value = "Origin City name", required = true) @RequestParam String origin, 
        @ApiParam(name = "destination", value = "Destination City name", required = true) @RequestParam String destination) {

      if(StringUtils.isEmpty(origin) || StringUtils.isEmpty(destination)) {
        LOG.info(String.format("City is Empty: %s, %s", origin, destination));
        return ConnectionConstants.NO;
      }

      return connectionService.isConnectedWithOneStop(origin, destination);
    }
    
    @ApiOperation(value = "To list all the possible connections between the cities",
            notes = "Returns HTML as a String",
            response = String.class)
    @GetMapping("/")
    public String availableConnections() {
          return connectionService.availableConnections();
    }
	
}

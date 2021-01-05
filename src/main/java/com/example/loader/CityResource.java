package com.example.loader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * City Resource class to load the cities from file
 */
@Component
public class CityResource {
    private final Log LOG = LogFactory.getLog(getClass());

    private Map<String, List<String>> roadMap = new HashMap<>();
	
    @Autowired
    private ResourceLoader resourceLoader;
	
    @Value("${data.file:classpath:city.txt}")
    private String city;
    
    @PostConstruct
    private void loadRoad() {
        Resource resource = resourceLoader.getResource(city);

        try(InputStream inputStream = resource.getInputStream(); 
            Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNext()) { 
                String road = scanner.nextLine();

                if(StringUtils.isEmpty(road)) {
                  continue;
                }

                loadRoad(road);  			
            }

        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }
    
    private void loadRoad(String road) {
        LOG.info(String.format("Road from File: %s", road));		
        String[] cityArray = road.split(",");
        String origin = cityArray[0].trim().toLowerCase();
        String destination = cityArray[1].trim().toLowerCase();

        List<String> connectingCitiesWithOneStop = roadMap.get(origin);
        addDestination(origin, destination, connectingCitiesWithOneStop);
        connectingCitiesWithOneStop = roadMap.get(destination);
        addDestination(destination, origin, connectingCitiesWithOneStop);		
    }
    
    private void addDestination(String origin, String destination, List<String> connectingCities) {
        if(connectingCities == null) {
            connectingCities = new ArrayList<>();
            connectingCities.add(destination);			
        } else if(!connectingCities.contains(destination)) {
            connectingCities.add(destination);	
        }		

        roadMap.put(origin, connectingCities);
        LOG.info(String.format("Road Map => City: %s - %s", origin, roadMap.get(origin).toString()));  
    }

	public Map<String, List<String>> getRoadMap() {
		    return roadMap;
	}    

}

package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.constants.ConnectionConstants;
import com.example.loader.CityResource;

/**
 * Connection Service class to find the connection between the cities
 */
@Service
public class ConnectionService implements IConnectionService {

	private final Log LOG = LogFactory.getLog(getClass());

	@Autowired
	private CityResource cityResource;

	@Override
	public String isConnected(String origin, String connectingCity, String destination, Set<String> visitedCities) {
		List<String> connectingCities = new ArrayList<>();		
		
		if (StringUtils.isEmpty(connectingCity)) {
			LOG.info(String.format("Cities: %s, %s", origin, destination));
			connectingCities = cityResource.getRoadMap().get(origin);
		} else {
			LOG.info(String.format("Cities: %s, %s", connectingCity, destination));
			visitedCities.add(connectingCity);
			connectingCities = cityResource.getRoadMap().get(connectingCity.toLowerCase());	
		}

		if (CollectionUtils.isEmpty(connectingCities)) {
			return ConnectionConstants.NO;
		}

		if (connectingCities.contains(destination)) {
			return ConnectionConstants.YES;
		} else {
			return isConnected(origin, connectingCities, destination, visitedCities);
		}
	}
	
	private String isConnected(String origin, List<String> connectingCities, String destination, Set<String> visitedCities) {
		List<String> connected = connectingCities.stream()
				.filter(connectingCity -> !visitedCities.contains(connectingCity) && ConnectionConstants.YES.equals(isConnected(origin, connectingCity, destination, visitedCities)))
				.collect(Collectors.toList());
		
		return CollectionUtils.isEmpty(connected) ? ConnectionConstants.NO : ConnectionConstants.YES; 
	}

	@Override
	public String isConnectedWithOneStop(String origin, String destination) {
		LOG.info(String.format("Cities: %s, %s", origin, destination));
		List<String> connectingCities = cityResource.getRoadMap().get(origin.toLowerCase());

		if (null == connectingCities) {
			return ConnectionConstants.NO;
		}

		if (connectingCities.contains(destination.toLowerCase())) {
			return ConnectionConstants.YES;
		} else {
			List<String> cityList = new ArrayList<String>();
			cityList.add(origin.toLowerCase());
			cityList.add(destination.toLowerCase());
			return isConnected(cityList);
		}
	}
	
	private String isConnected(List<String> cityList) {
		String connection = cityResource.getRoadMap().entrySet().stream()
				.filter(city -> isConnected(city.getValue(), cityList)).map(map -> map.getKey())
				.collect(Collectors.joining());

		if (connection.isEmpty()) {
			return ConnectionConstants.NO;
		} else {
			LOG.info(String.format("Cities: %s connected via %s", cityList.toString(), connection));
			return ConnectionConstants.YES;
		}
	}

	private boolean isConnected(List<String> connectingCities, List<String> cityList) {
		return connectingCities.containsAll(cityList);
	}

	@Override
	public String availableConnections() {
		StringBuilder html = new StringBuilder();

		html.append("<html><head><title>Available City Connections</title></head><body>")
				.append("<h2>Connection List</h2>");

		cityResource.getRoadMap().forEach((k, v) -> {
			html.append(k).append(" -> ").append(v.toString());
			html.append("</br>");
		});

		html.append("</body></html>");
		return html.toString();
	}

}

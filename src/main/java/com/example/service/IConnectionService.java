package com.example.service;

import java.util.Set;

public interface IConnectionService {

	String isConnected(String origin, String connectingCity, String destination, Set<String> visitedCities);

	String availableConnections();

	String isConnectedWithOneStop(String origin, String destination);

}

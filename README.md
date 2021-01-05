# city-connection

This application determines if two cities are connected. Two cities are considered connected if there’s a series of roads that can be traveled from one city to another.

List of roads is available in 'city.txt' file. The file contains a list of city pairs (one pair per line, comma separated), which indicates that there’s a road between those cities.

# For Example:

Boston, New York
Philadelphia, Newark
Newark, Boston
Trenton, Albany
Edison, Boston

# To Build the application: Check out the project and run the below command
mvn clean install

# To Run the application
mvn spring-boot:run

# Using Java command line
java -jar target\city-connection-0.0.1-SNAPSHOT.jar

# To execute test cases: Run the below command
mvn verify

# Available endpoints
http://localhost:9090/connected?origin=city1&destination=city2
Response: no

http://localhost:9090/connected?origin=Boston&destination=Newark
Response: yes

http://localhost:9090/connected?origin=Boston&destination=Philadelphia
Response: yes

http://localhost:9090/connected?origin=Philadelphia&destination=Albany
Response: no

http://localhost:9090/connected?origin=Philadelphia&destination=Edison
Response: yes

http://localhost:9090/connectedWithOneStop?origin=city1&destination=city2
Response: no

http://localhost:9090/connectedWithOneStop?origin=Boston&destination=Newark
Response: yes

http://localhost:9090/connectedWithOneStop?origin=Boston&destination=Philadelphia
Response: yes

http://localhost:9090/connectedWithOneStop?origin=Philadelphia&destination=Albany
Response: no

http://localhost:9090/
Response: List of available connections between the cities in HTML

# Swagger
http://localhost:9090/swagger-ui.html#/

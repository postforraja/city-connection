package com.example.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.constants.ConnectionConstants;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class ConnectionControllerTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void isConnected() throws Exception {
        String body = template.getForObject("/connected?origin=city1&destination=city2", String.class);
        Assert.assertEquals(ConnectionConstants.NO, body);
    }
    
    @Test
    public void isConnected1() throws Exception {
        String body = template.getForObject("/connected?origin=Boston&destination=Newark", String.class);
		Assert.assertEquals(ConnectionConstants.YES, body);
    }
    
    @Test
    public void isConnected2() throws Exception {
        String body = template.getForObject("/connected?origin=Boston&destination=Philadelphia", String.class);
        Assert.assertEquals(ConnectionConstants.YES, body);
    }
    
    @Test
    public void isConnected3() throws Exception {
        String body = template.getForObject("/connected?origin=Philadelphia&destination=Albany", String.class);
        Assert.assertEquals(ConnectionConstants.NO, body);
    }
    
    @Test
    public void isConnected4() throws Exception {
        String body = template.getForObject("/connected?origin=Philadelphia&destination=Edison", String.class);
        Assert.assertEquals(ConnectionConstants.YES, body);
    }
    
    @Test
    public void isConnected5() throws Exception {
        String body = template.getForObject("/connected?origin=&destination=Edison", String.class);
        Assert.assertEquals(ConnectionConstants.NO, body);
    }
    
    @Test
    public void isConnectedWithOneStop() throws Exception {
        String body = template.getForObject("/connectedWithOneStop?origin=city1&destination=city2", String.class);
        Assert.assertEquals(ConnectionConstants.NO, body);
    }
    
    @Test
    public void isConnectedWithOneStop1() throws Exception {
        String body = template.getForObject("/connectedWithOneStop?origin=Boston&destination=Newark", String.class);
        Assert.assertEquals(ConnectionConstants.YES, body);
    }
    
    @Test
    public void isConnectedWithOneStop2() throws Exception {
        String body = template.getForObject("/connectedWithOneStop?origin=Boston&destination=Philadelphia", String.class);
        Assert.assertEquals(ConnectionConstants.YES, body);
    }
    
    @Test
    public void isConnectedWithOneStop3() throws Exception {
        String body = template.getForObject("/connectedWithOneStop?origin=Philadelphia&destination=Albany", String.class);
        Assert.assertEquals(ConnectionConstants.NO, body);
    }
    
    @Test
    public void isConnectedWithOneStop4() throws Exception {
        String body = template.getForObject("/connectedWithOneStop?origin=&destination=Albany", String.class);
        Assert.assertEquals(ConnectionConstants.NO, body);
    }
    
    @Test
    public void availableConnections() throws Exception {
        String body = template.getForObject("/", String.class);
        Assert.assertNotNull(body);
    }

}

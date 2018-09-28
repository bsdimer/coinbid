package com.madbid.messaging.config;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.broker.jmx.ManagementContext;
import org.apache.activemq.hooks.SpringContextHook;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.util.Collections;

/**
 * Created by dimer.
 */

@Configuration
public class AppConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public BrokerService broker() throws Exception {
        final BrokerService broker = new BrokerService();
        broker.addConnector("tcp://localhost:61616");
        broker.setPersistent(false);
        broker.setShutdownHooks(Collections.<Runnable>singletonList(new SpringContextHook()));

        /*final ActiveMQTopic topic = new ActiveMQTopic("/topic");
        broker.setDestinations(new ActiveMQDestination[]{topic});*/

        TransportConnector connector = new TransportConnector();
        connector.setName("stomp");
        connector.setUri(new URI("stomp://0.0.0.0:61613?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"));
        broker.addConnector(connector);

        final ManagementContext managementContext = new ManagementContext();
        managementContext.setCreateConnector(true);
        broker.setManagementContext(managementContext);

        return broker;
    }
}


  /*  public void onStartup(ServletContext servletContext) throws ServletException {
        BrokerService broker = new BrokerService();
        // Configure the broker
        try {
            // Stop the broker after installation
            broker.stop();
            broker.waitUntilStopped();
*//*
            // Initialize broker
            broker.setBrokerName("madbid");
            broker.setPersistance(false);

            // Crate and add STOMP connector
            TransportConnector connector = new TransportConnector();
            connector.setName("stomp");
            connector.setUri(new URI("stomp://0.0.0.0:61613?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"));
            broker.addConnector(connector);

            // Start the broker
            broker.start(true);*//*
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}*/



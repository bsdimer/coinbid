package com.madbid.web.jms;

import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.util.Assert;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

/**
 * Created by dimer on 9/17/14.
 */
public class JMSDestinationResolver extends DynamicDestinationResolver {

    @Override
    public Destination resolveDestinationName(Session session, String destinationName, boolean pubSubDomain)
            throws JMSException {
        try {
            Assert.notNull(session, "Session must not be null");
            Assert.notNull(destinationName, "Destination name must not be null");
        } catch (IllegalArgumentException e) {
            throw new JMSException(e.getMessage());
        }
        if (destinationName.indexOf("/topic/") == 0) {
            return resolveTopic(session, destinationName.substring(7));
        } else {
            return resolveQueue(session, destinationName.substring(7));
        }
    }
}

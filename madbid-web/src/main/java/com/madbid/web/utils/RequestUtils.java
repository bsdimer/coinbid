package com.madbid.web.utils;

import javax.management.*;
import javax.servlet.http.HttpServletRequest;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by nikolov.
 */
public final class RequestUtils {

    //get request headers
    public static Map<String, String> getHeadersInfo(HttpServletRequest request) {

        Map<String, String> map = new HashMap<String, String>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    public static List<String> getEndPoints() throws MalformedObjectNameException,
            NullPointerException, AttributeNotFoundException,
            InstanceNotFoundException, MBeanException, ReflectionException, UnknownHostException {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        Set<ObjectName> objs = mbs.queryNames(new ObjectName("*:type=Connector,*"),
                Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
        String hostname = InetAddress.getLocalHost().getHostName();
        InetAddress[] addresses = InetAddress.getAllByName(hostname);
        ArrayList<String> endPoints = new ArrayList<String>();
        for (Iterator<ObjectName> i = objs.iterator(); i.hasNext();) {
            ObjectName obj = i.next();
            String scheme = mbs.getAttribute(obj, "scheme").toString();
            String port = obj.getKeyProperty("port");
            for (InetAddress addr : addresses) {
                String host = addr.getHostAddress();
                if(host.indexOf(':') != -1) {
                    continue;
                }
                String ep = scheme + "://" + host + ":" + port;
                endPoints.add(ep);
            }
        }
        return endPoints;
    }
}

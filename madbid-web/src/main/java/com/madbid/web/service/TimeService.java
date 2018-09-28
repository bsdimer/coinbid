package com.madbid.web.service;

import com.madbid.web.utils.PropertiesUtils;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by dimer on 9/16/14.
 */
@Service
public class TimeService {

    public static final String OPENTIME_PROPERTY = "workinghour.start";
    public static final String CLOSETIME_PROPERTY = "workinghour.close";

    @Inject
    private PropertiesUtils propertiesUtil;

    public boolean isWorkingTime() {
        // Check whether now is in working hours.
        try {
            if (propertiesUtil.getProperties().getProperty("working.force").indexOf("true") == 0) {
                return true;
            }
        } catch (NullPointerException ex) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.getHourOfDay() > getOpenHour() && now.getHourOfDay() < getCloseHour()) {
            return true;
        }
        return false;
    }

    public int getOpenHour() {
        return Integer.parseInt((String) propertiesUtil.getProperties().get(OPENTIME_PROPERTY));
    }

    public int getCloseHour() {
        return Integer.parseInt((String) propertiesUtil.getProperties().get(CLOSETIME_PROPERTY));
    }

}

package com.prueba.sintad.aggregates.constants;

import java.sql.Timestamp;

public class Constants {
    public static final Boolean STATUS_ACTIVE = true;
    public static final Boolean STATUS_INACTIVE = false;
    public static final String STATUS_OK = "200";
    public static final String STATUS_CREATED = "201";
    public static final String STATUS_DELETED = "204";
    public static final String STATUS_BAD_REQUEST = "400";
    public static final String STATUS_NOT_FOUND = "404";
    public static final String STATUS_INTERNAL_SERVER_ERROR = "500";
    public static Timestamp getTimestamp(){
        long currentTime = System.currentTimeMillis();
        return new Timestamp(currentTime);
    }
}

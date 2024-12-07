package com.prueba.sintad.aggregates.constants;

import java.sql.Timestamp;

public class Constants {
    public static final Boolean STATUS_ACTIVE = true;
    public static final Boolean STATUS_INACTIVE = false;
    public static final String OPERATION_SUCCESS= "Operación realizada con éxito";

    public static final String STATUS_OK = "200";
    public static final String STATUS_CREATED = "201";
    public static final String STATUS_NO_CONTENT = "204";
    public static final String STATUS_ACCEPTED = "202";

    public static final String STATUS_BAD_REQUEST = "400";
    public static final String STATUS_NOT_FOUND = "404";
    public static final String STATUS_INTERNAL_SERVER_ERROR = "500";
    public static final String STATUS_NOT_ACCEPTABLE = "406";

    public static final String MESSAGE_OK = "OK";
    public static final String TIPO_DOCUMENTO_DNI= "DNI";
    public static final String TIPO_DOCUMENTO_RUC= "RUC";

    public static final String NUM_PAG_BY_DEFECT="0";
    public static final String SIZE_PAG_BY_DEFECT="10";
    public static final String ORDER_BY_DEFECT_ALL="id";
    public static final String ORDER_DIRECT_BY_DEFECT="0";
    public static Timestamp getTimestamp(){
        long currentTime = System.currentTimeMillis();
        return new Timestamp(currentTime);
    }

    public static StringBuilder parametersForLogger(int pageNumber, int pageSize, String orderBy, String sortDir){
        StringBuilder parameters = new StringBuilder();
        parameters.append("pageNumber=").append(pageNumber);
        parameters.append("&pageSize=").append(pageSize);
        parameters.append("&orderBy=").append(orderBy);
        parameters.append("&sortDir=").append(sortDir);
        return parameters;
    }
}

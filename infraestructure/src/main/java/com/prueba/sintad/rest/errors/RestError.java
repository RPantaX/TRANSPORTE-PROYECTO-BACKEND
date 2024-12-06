package com.prueba.sintad.rest.errors;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
public class RestError implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response){
        try{
            String getBody=this.getBody(response);
            log.error("ERROR EN FEIGN DECODER");
            return new RuntimeException(getBody, new Throwable(getBody));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private String getBody(Response response) throws IOException {
        Response.Body body= response.body();
        int codigo = response.status();
        log.error("EL CODIGO QUE DEVOLVIO EL API ES: "+codigo);
        if(!Objects.isNull(body)){
            InputStream inputStream = body.asInputStream();
            final String bodyError= StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            inputStream.close();
            return bodyError;
        } else{
            return "SIN CONTENIDO";
        }
    }
}

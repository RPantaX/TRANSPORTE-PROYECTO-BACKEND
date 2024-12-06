package com.prueba.sintad.rest.client;

import com.prueba.sintad.aggregates.response.rest.ResponseSunat;
import com.prueba.sintad.rest.errors.RestError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "client-sunat", url = "https://api.apis.net.pe/v2/sunat", configuration = RestError.class)
public interface SunatClient {
    @GetMapping("/ruc")
    ResponseSunat getInfoSunat(@RequestParam("numero") String numero,
                               @RequestHeader("Authorization") String authorizationHeader);

}

package com.gestionHopital.serv_utilisateur.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ErrorHttpResponse {

    public static ResponseEntity<Object> response(HttpStatus status, String message, Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status.value());
        map.put("message", message);
        if (data != null)
        {
            if (status == HttpStatus.BAD_REQUEST)
                map.put("error", data);
            else
                map.put("data", data);
        }
        return new ResponseEntity<>(map, status);
    }
}

package com.annette.spring.project.online_store.service;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseService {

    @SuppressWarnings("unchecked")
    public Map<String, Object> jsonToMap(String json) {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> resultMap = new LinkedHashMap<>();

        try {
            resultMap = objectMapper.readValue(json, LinkedHashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return resultMap;

    }

}

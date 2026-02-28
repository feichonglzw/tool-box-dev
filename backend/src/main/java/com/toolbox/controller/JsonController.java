package com.toolbox.controller;

import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.*;
import java.util.*;

@RestController
@RequestMapping("/api/json")
public class JsonController {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectMapper prettyMapper = new ObjectMapper()
        .enable(SerializationFeature.INDENT_OUTPUT);

    @PostMapping("/format")
    public Map<String, Object> format(@RequestBody Map<String, Object> request) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            String pretty = prettyMapper.writeValueAsString(request.get("data"));
            result.put("success", true);
            result.put("formatted", pretty);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        
        return result;
    }

    @PostMapping("/compress")
    public Map<String, Object> compress(@RequestBody Map<String, Object> request) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            String compact = mapper.writeValueAsString(request.get("data"));
            result.put("success", true);
            result.put("compressed", compact);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        
        return result;
    }

    @PostMapping("/validate")
    public Map<String, Object> validate(@RequestBody Map<String, Object> request) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            mapper.readTree(mapper.writeValueAsString(request.get("data")));
            result.put("valid", true);
        } catch (Exception e) {
            result.put("valid", false);
            result.put("error", e.getMessage());
        }
        
        return result;
    }
}
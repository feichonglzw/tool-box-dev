package com.toolbox.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/uuid")
public class UuidController {

    @GetMapping("/generate")
    public Map<String, Object> generate(@RequestParam(defaultValue = "1") int count) {
        List<String> uuids = new ArrayList<>();
        for (int i = 0; i < Math.min(count, 100); i++) {
            uuids.add(UUID.randomUUID().toString());
        }
        Map<String, Object> result = new HashMap<>();
        result.put("uuids", uuids);
        result.put("count", uuids.size());
        return result;
    }
}
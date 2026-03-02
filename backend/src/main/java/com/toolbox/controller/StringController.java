package com.toolbox.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/string")
public class StringController {

    @GetMapping("/quote")
    public Map<String, Object> quote(@RequestParam String text,
                                     @RequestParam(defaultValue = "'") String quote,
                                     @RequestParam(defaultValue = "true") boolean addComma) {
        String[] lines = text.split("\n");
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (!line.isEmpty()) {
                sb.append(quote).append(line).append(quote);
                if (addComma && i < lines.length - 1) {
                    sb.append(",");
                }
                if (i < lines.length - 1) {
                    sb.append("\n");
                }
            }
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("input", text);
        result.put("output", sb.toString());
        result.put("quote", quote);
        result.put("addComma", addComma);
        return result;
    }
}
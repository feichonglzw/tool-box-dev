package com.toolbox.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.Base64;

@RestController
@RequestMapping("/api/base64")
public class Base64Controller {

    @GetMapping("/encode")
    public Map<String, Object> encode(@RequestParam String text) {
        String encoded = Base64.getEncoder().encodeToString(text.getBytes());
        Map<String, Object> result = new HashMap<>();
        result.put("input", text);
        result.put("output", encoded);
        return result;
    }

    @GetMapping("/decode")
    public Map<String, Object> decode(@RequestParam String text) {
        try {
            String decoded = new String(Base64.getDecoder().decode(text));
            Map<String, Object> result = new HashMap<>();
            result.put("input", text);
            result.put("output", decoded);
            return result;
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("error", "Invalid Base64 input");
            return result;
        }
    }
}
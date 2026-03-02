package com.toolbox.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.net.*;
import java.io.*;

@RestController
@RequestMapping("/api/url")
public class UrlController {

    private static final Map<String, String> URL_MAP = new HashMap<>();
    private static final Map<String, String> REVERSE_MAP = new HashMap<>();
    private static int COUNTER = 1000;

    @PostMapping("/shorten")
    public Map<String, Object> shorten(@RequestBody Map<String, String> body) {
        Map<String, Object> result = new HashMap<>();
        String originalUrl = body.get("url");
        
        if (originalUrl == null || originalUrl.isEmpty()) {
            result.put("error", "请提供 URL");
            return result;
        }
        
        try {
            // 简单生成短码
            String shortCode = "t" + (++COUNTER);
            URL_MAP.put(shortCode, originalUrl);
            REVERSE_MAP.put(originalUrl, shortCode);
            
            result.put("shortUrl", "http://t.box/" + shortCode);
            result.put("originalUrl", originalUrl);
            result.put("success", true);
        } catch (Exception e) {
            result.put("error", e.getMessage());
        }
        
        return result;
    }
    
    @GetMapping("/expand")
    public Map<String, Object> expand(@RequestParam String code) {
        Map<String, Object> result = new HashMap<>();
        
        String originalUrl = URL_MAP.get(code);
        if (originalUrl != null) {
            result.put("originalUrl", originalUrl);
            result.put("success", true);
        } else {
            result.put("error", "短链接不存在");
        }
        
        return result;
    }
    
    @GetMapping("/list")
    public Map<String, Object> list() {
        Map<String, Object> result = new HashMap<>();
        result.put("urls", URL_MAP);
        return result;
    }
}
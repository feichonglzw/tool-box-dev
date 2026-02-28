package com.toolbox.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/password")
public class PasswordController {

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()_+-=[]{}|;:,.<>?";

    @GetMapping("/generate")
    public Map<String, Object> generate(
            @RequestParam(defaultValue = "16") int length,
            @RequestParam(defaultValue = "true") boolean lowercase,
            @RequestParam(defaultValue = "true") boolean uppercase,
            @RequestParam(defaultValue = "true") boolean digits,
            @RequestParam(defaultValue = "false") boolean special) {
        
        String chars = "";
        if (lowercase) chars += LOWERCASE;
        if (uppercase) chars += UPPERCASE;
        if (digits) chars += DIGITS;
        if (special) chars += SPECIAL;
        
        if (chars.isEmpty()) chars = LOWERCASE + DIGITS;
        
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("password", password.toString());
        result.put("length", length);
        return result;
    }
}
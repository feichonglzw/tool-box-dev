package com.toolbox.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.io.*;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {

    private static final String CONFIG_FILE = System.getProperty("user.home") + "/.toolbox/config";

    @GetMapping
    public Map<String, Object> get() {
        Map<String, Object> result = new HashMap<>();
        File f = new File(CONFIG_FILE);
        if (f.exists()) {
            try {
                Properties props = new Properties();
                props.load(new FileInputStream(f));
                Map<String, String> settings = new HashMap<>();
                props.forEach((k, v) -> settings.put(k.toString(), v != null && !v.toString().isEmpty() ? "***" : ""));
                result.put("settings", settings);
            } catch (Exception e) {
                result.put("error", e.getMessage());
            }
        } else {
            result.put("settings", new HashMap<>());
        }
        return result;
    }

    @PostMapping
    public Map<String, Object> save(@RequestBody Map<String, String> body) {
        Map<String, Object> result = new HashMap<>();
        try {
            File dir = new File(CONFIG_FILE).getParentFile();
            if (!dir.exists()) dir.mkdirs();
            
            // 读取现有配置
            Properties props = new Properties();
            File cf = new File(CONFIG_FILE);
            if (cf.exists()) {
                props.load(new FileInputStream(cf));
            }
            
            // 保存 AI 相关配置
            if (body.containsKey("ai_provider")) {
                props.setProperty("ai_provider", body.get("ai_provider"));
            }
            if (body.containsKey("ai_api_key") && !body.get("ai_api_key").equals("***")) {
                props.setProperty("ai_api_key", body.get("ai_api_key"));
            }
            if (body.containsKey("ai_model")) {
                props.setProperty("ai_model", body.get("ai_model"));
            }
            
            props.store(new FileOutputStream(CONFIG_FILE), "ToolBox Settings");
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        return result;
    }
}
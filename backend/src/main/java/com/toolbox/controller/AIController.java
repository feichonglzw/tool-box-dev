package com.toolbox.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.net.*;
import java.io.*;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private static final String CONFIG_FILE = System.getProperty("user.home") + "/.toolbox/config";

    private Map<String, String> getConfig() {
        Map<String, String> cfg = new HashMap<>();
        try {
            File f = new File(CONFIG_FILE);
            if (f.exists()) {
                Properties props = new Properties();
                props.load(new FileInputStream(f));
                cfg.put("provider", props.getProperty("ai_provider", "openai"));
                cfg.put("api_key", props.getProperty("ai_api_key", ""));
                cfg.put("model", props.getProperty("ai_model", ""));
            }
        } catch (Exception e) {}
        return cfg;
    }

    @PostMapping("/chat")
    public Map<String, Object> chat(@RequestBody Map<String, String> body) {
        Map<String, Object> result = new HashMap<>();
        Map<String, String> cfg = getConfig();
        String provider = cfg.getOrDefault("provider", "openai");
        String apiKey = cfg.getOrDefault("api_key", "");
        
        if (apiKey.isEmpty()) {
            result.put("error", "请先在 Settings 中设置 API Key");
            return result;
        }
        
        try {
            String url, json;
            switch (provider) {
                case "zhipu":  // 智谱AI (ChatGLM)
                    url = "https://open.bigmodel.cn/api/paas/v4/chat/completions";
                    json = "{\"model\":\"" + (cfg.get("model").isEmpty() ? "glm-4" : cfg.get("model")) + 
                           "\",\"messages\":[{\"role\":\"user\",\"content\":\"" + escape(body.get("message")) + "\"}]}";
                    apiKey = "Bearer " + apiKey;
                    break;
                case "baidu":  // 百度 (ERNIE)
                    url = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/" + (cfg.get("model").isEmpty() ? "ernie-lite-8k" : cfg.get("model"));
                    json = "{\"messages\":[{\"role\":\"user\",\"content\":\"" + escape(body.get("message")) + "\"}]}";
                    break;
                case "aliyun":  // 阿里 (Qwen)
                    url = "https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions";
                    json = "{\"model\":\"" + (cfg.get("model").isEmpty() ? "qwen-turbo" : cfg.get("model")) + 
                           "\",\"messages\":[{\"role\":\"user\",\"content\":\"" + escape(body.get("message")) + "\"}]}";
                    apiKey = "Bearer " + apiKey;
                    break;
                case "moonshot":  // 月之暗面 (Kimi)
                    url = "https://api.moonshot.cn/v1/chat/completions";
                    json = "{\"model\":\"" + (cfg.get("model").isEmpty() ? "moonshot-v1-8k" : cfg.get("model")) + 
                           "\",\"messages\":[{\"role\":\"user\",\"content\":\"" + escape(body.get("message")) + "\"}]}";
                    apiKey = "Bearer " + apiKey;
                    break;
                case "01ai":  // 零一万物 (Yi)
                    url = "https://api.01.ai/v1/chat/completions";
                    json = "{\"model\":\"" + (cfg.get("model").isEmpty() ? "yi-spark" : cfg.get("model")) + 
                           "\",\"messages\":[{\"role\":\"user\",\"content\":\"" + escape(body.get("message")) + "\"}]}";
                    apiKey = "Bearer " + apiKey;
                    break;
                default:  // OpenAI
                    url = "https://api.openai.com/v1/chat/completions";
                    json = "{\"model\":\"" + (cfg.get("model").isEmpty() ? "gpt-3.5-turbo" : cfg.get("model")) + 
                           "\",\"messages\":[{\"role\":\"user\",\"content\":\"" + escape(body.get("message")) + "\"}]}";
                    apiKey = "Bearer " + apiKey;
            }
            
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", apiKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) response.append(line);
            
            String resp = response.toString();
            String reply = extractReply(provider, resp);
            result.put("reply", reply);
            result.put("success", true);
        } catch (Exception e) {
            result.put("error", e.getMessage());
        }
        return result;
    }
    
    private String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }
    
    private String extractReply(String provider, String resp) {
        try {
            if (provider.equals("zhipu")) {
                int i = resp.indexOf("\"content\":\"");
                if (i > 0) {
                    int j = resp.indexOf("\"", i + 11);
                    return resp.substring(i + 11, j).replace("\\n", "\n");
                }
            } else if (provider.equals("baidu")) {
                int i = resp.indexOf("\"result\":\"");
                if (i > 0) {
                    int j = resp.indexOf("\"", i + 10);
                    return resp.substring(i + 10, j).replace("\\n", "\n");
                }
            } else {
                int i = resp.indexOf("\"content\":\"");
                if (i > 0) {
                    int j = resp.indexOf("\"", i + 11);
                    return resp.substring(i + 11, j).replace("\\n", "\n");
                }
            }
        } catch (Exception e) {}
        return resp;
    }
}
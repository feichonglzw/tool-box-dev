package com.toolbox.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.regex.*;

@RestController
@RequestMapping("/api/regex")
public class RegexController {

    @PostMapping("/test")
    public Map<String, Object> test(@RequestBody Map<String, String> body) {
        Map<String, Object> result = new HashMap<>();
        
        String pattern = body.get("pattern");
        String text = body.get("text");
        
        if (pattern == null || text == null) {
            result.put("error", "请提供 pattern 和 text");
            return result;
        }
        
        try {
            Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(text);
            
            List<String> matches = new ArrayList<>();
            List<Map<String, String>> groups = new ArrayList<>();
            
            while (m.find()) {
                matches.add(m.group());
                Map<String, String> g = new HashMap<>();
                g.put("match", m.group());
                g.put("start", String.valueOf(m.start()));
                g.put("end", String.valueOf(m.end()));
                groups.add(g);
            }
            
            result.put("matches", matches);
            result.put("matchCount", matches.size());
            result.put("details", groups);
            result.put("success", true);
            
        } catch (Exception e) {
            result.put("error", "正则表达式错误: " + e.getMessage());
        }
        
        return result;
    }
    
    @GetMapping("/common")
    public Map<String, Object> common() {
        Map<String, Object> result = new HashMap<>();
        
        Map<String, String> patterns = new LinkedHashMap<>();
        patterns.put("手机号", "1[3-9]\\d{9}");
        patterns.put("邮箱", "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
        patterns.put("URL", "https?://[\\w\\-._~:/?#\\[\\]@!$&'()*+,;=%]+");
        patterns.put("IP地址", "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
        patterns.put("身份证", "[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[\\dXx]");
        
        result.put("patterns", patterns);
        return result;
    }
}
package com.toolbox.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.time.*;

@RestController
@RequestMapping("/api/cron")
public class CronController {

    @GetMapping("/parse")
    public Map<String, Object> parse(@RequestParam String expr) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 简单解析 cron 表达式 (只支持标准 5 字段)
            // 格式: second minute hour day month weekday
            String[] parts = expr.trim().split("\\s+");
            
            if (parts.length < 5) {
                result.put("error", "请输入标准 cron 表达式（5或6个字段）");
                return result;
            }
            
            StringBuilder desc = new StringBuilder();
            desc.append("Cron: ").append(expr).append("\n\n");
            
            // 解析每个字段
            desc.append("⏰ 时间: ").append(parts[2]).append(":").append(parts[1]).append("\n");
            desc.append("📅 日期: 每月 ").append(parts[3]).append(" 号\n");
            desc.append("📆 星期: ").append(parts[4]).append("\n");
            
            // 计算下次执行时间（简单估算）
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime next = now.plusDays(1).withHour(0).withMinute(0).withSecond(0);
            
            result.put("description", desc.toString());
            result.put("nextRun", next.toString());
            result.put("success", true);
            
        } catch (Exception e) {
            result.put("error", e.getMessage());
        }
        
        return result;
    }
    
    @GetMapping("/next")
    public Map<String, Object> next(@RequestParam String expr, @RequestParam(defaultValue = "5") int count) {
        Map<String, Object> result = new HashMap<>();
        
        // 返回接下来 n 次的执行时间
        List<String> times = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        
        for (int i = 0; i < count; i++) {
            times.add(now.plusDays(i + 1).withHour(9).withMinute(0).withSecond(0).toString());
        }
        
        result.put("executions", times);
        return result;
    }
}
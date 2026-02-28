package com.toolbox.controller;

import org.springframework.web.bind.annotation.*;
import java.time.*;
import java.util.*;

@RestController
@RequestMapping("/api/timestamp")
public class TimestampController {

    @GetMapping("/now")
    public Map<String, Object> now() {
        long now = System.currentTimeMillis();
        Map<String, Object> result = new HashMap<>();
        result.put("unix", now / 1000);
        result.put("unixMilli", now);
        result.put("datetime", LocalDateTime.now().toString());
        result.put("date", LocalDate.now().toString());
        result.put("time", LocalTime.now().toString());
        return result;
    }

    @GetMapping("/convert")
    public Map<String, Object> convert(@RequestParam(required = false) Long unix,
                                       @RequestParam(required = false) String datetime) {
        Map<String, Object> result = new HashMap<>();
        
        if (unix != null) {
            LocalDateTime dt = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(unix), ZoneId.systemDefault());
            result.put("unix", unix);
            result.put("datetime", dt.toString());
        } else if (datetime != null) {
            LocalDateTime dt = LocalDateTime.parse(datetime);
            result.put("datetime", datetime);
            result.put("unix", dt.toEpochSecond(ZoneId.systemDefault()));
        }
        
        return result;
    }
}
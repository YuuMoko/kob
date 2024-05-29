package com.kob.backend.controller.pk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController()
@RequestMapping("/pk/")
public class BotinfoController {

    @RequestMapping("getbotinfo/")
    public List<Map<String, String>> getBotInfo(){
        List<Map<String, String>> list = new LinkedList<>();
        Map<String, String> bot1 = new HashMap<>();
        bot1.put("name", "tiger");
        bot1.put("ratring", "1500");

        Map<String, String> bot2 = new HashMap<>();
        bot2.put("name", "apple");
        bot2.put("ratring", "1800");

        list.add(bot1);
        list.add(bot2);

        return list;
    }
}

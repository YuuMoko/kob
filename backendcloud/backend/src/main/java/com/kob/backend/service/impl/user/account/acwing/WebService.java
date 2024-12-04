package com.kob.backend.service.impl.user.account.acwing;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class WebService implements com.kob.backend.service.user.account.acwing.WebService {
    @Override
    public JSONObject applyCode() {
        return null;
    }

    @Override
    public JSONObject receiveCode(String code, String state) {
        return null;
    }
}

package com.kob.backend.service.user.account;

import java.util.Map;

public interface UpdateAccountService {

    Map<String, String> updateUsername (Integer id, String username);
    Map<String, String> updatePhoto (Integer id, String photo);

}

package com.kob.backend.service.user.account;

import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface UpdateAccountService {

    Map<String, String> updateUsername (Integer id, String username);
    Map<String, String> updatePhoto (Integer id, String photo);
    Map<String, String> uploadPhoto(MultipartFile file);
    Map<String, String> updatePassword(String oldPassword, String newPassword, String confirmedPassword);

}

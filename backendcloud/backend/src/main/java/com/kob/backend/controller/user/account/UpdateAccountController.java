package com.kob.backend.controller.user.account;

import com.kob.backend.service.user.account.UpdateAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class UpdateAccountController {

    @Autowired
    UpdateAccountService updateService;

    @PostMapping("/api/user/account/update/username/")
    public Map<String, String> updateUsername(@RequestParam Map<String, String> data) {
        Integer id = Integer.parseInt(data.get("id"));
        String name = data.get("username");

        return updateService.updateUsername(id, name);
    }

    @PostMapping("/api/user/account/update/photo/")
    public Map<String, String> updatePhoto(@RequestParam Map<String, String> data) {
        Integer id = Integer.parseInt(data.get("id"));
        String photo = data.get("photo");
        return updateService.updatePhoto(id, photo);
    }

    @PostMapping("/api/user/account/upload/photo/")
    public Map<String, String> uploadPhoto(@RequestParam("file") MultipartFile file) {
        return updateService.uploadPhoto(file);
    }

    @PostMapping("/api/user/account/update/password/")
    public Map<String, String> updatePassword(@RequestParam Map<String, String> data) {
        String oldPassword = data.get("old_password");
        String newPassword = data.get("new_password");
        String confirmedPassword = data.get("confirmed_password");
        return updateService.updatePassword(oldPassword, newPassword, confirmedPassword);
    }

}

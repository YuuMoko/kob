package com.kob.backend.controller.user.account;

import com.kob.backend.service.user.account.UpdateAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
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

}

package com.codegym.controller;

import com.codegym.model.InfoUser;
import com.codegym.model.dto.UserDetailDTO;
import com.codegym.service.InfoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class UserDetailController {
    @Autowired
    InfoUserService infoUserService;
    @GetMapping ("/userdetail/{id}")
    public UserDetailDTO findUserDetail(@PathVariable("id") Long id){
        return infoUserService.findInforUserById(id);
    }
    @PutMapping("userdetail")
    public void updateUserDetail(InfoUser infoUser){
        infoUserService.updateInfoUser(infoUser);
    }
}

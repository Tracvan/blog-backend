package com.codegym.service;

import com.codegym.model.InfoUser;
import com.codegym.model.User;
import com.codegym.model.dto.UserDTO;
import com.codegym.model.dto.UserDetailDTO;

public interface InfoUserService {
    public UserDetailDTO findInforUserById(Long id);
    public  void updateInfoUser(InfoUser infoUser);
    UserDetailDTO findInfoUserByUser(User user);

    void save(InfoUser infoUser);

}

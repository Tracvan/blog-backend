package com.codegym.service;

import com.codegym.model.InfoUser;
import com.codegym.model.dto.UserDetailDTO;

public interface InfoUserService {
    public UserDetailDTO findInforUserById(Long id);
}

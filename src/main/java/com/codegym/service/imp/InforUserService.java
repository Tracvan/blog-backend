package com.codegym.service.imp;

import com.codegym.model.InfoUser;
import com.codegym.model.User;
import com.codegym.model.dto.UserDTO;
import com.codegym.model.dto.UserDetailDTO;
import com.codegym.repository.InfoUserRepository;
import com.codegym.service.InfoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InforUserService implements InfoUserService {
    @Autowired
    InfoUserRepository infoUserRepository;
    public UserDetailDTO findInforUserById(Long id) {
        return infoUserRepository.findUserDetailById(id);
    }

    @Override
    public void updateInfoUser(InfoUser infoUser) {
        infoUserRepository.save(infoUser);
    }

    @Override
    public UserDetailDTO findInfoUserByUser(User user) {
        return infoUserRepository.findUserDetailById(user.getId());
    }

    @Override
    public void save(InfoUser infoUser) {
        infoUserRepository.save(infoUser);
    }


}

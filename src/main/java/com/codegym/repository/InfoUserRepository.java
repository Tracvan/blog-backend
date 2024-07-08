package com.codegym.repository;

import com.codegym.model.InfoUser;

import com.codegym.model.dto.UserDetailDTO;
import com.codegym.model.dto.UserDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface InfoUserRepository extends JpaRepository<InfoUser, Long> {


    @Query("SELECT new com.codegym.model.dto.UserDTO(i.user.id, i.user.username, i.user.date, i.avatar, i.fullName, i.status) FROM InfoUser i")

    List<UserDTO> findAllUsers();
    @Modifying
    @Transactional
    @Query("UPDATE InfoUser i SET i.status = 'Khóa' WHERE i.user.id = :id")
    void lockUserById(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE InfoUser i SET i.status = 'Hoạt động' WHERE i.user.id = :id")
    void unlockUserById(Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM InfoUser i WHERE i.user.id = :id")
    void deleteUserById(Long id);

    @Query("SELECT new com.codegym.model.dto.UserDetailDTO(i.user.id, i.user.username, i.user.email, i.avatar, i.user.date, i.fullName, i.address, i.phonenumber, i.status) FROM InfoUser i WHERE i.user.id = :id")
    UserDetailDTO findUserDetailById(Long id);
}


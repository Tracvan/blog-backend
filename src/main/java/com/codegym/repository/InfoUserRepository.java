package com.codegym.repository;

import com.codegym.model.InfoUser;

import com.codegym.model.User;
import com.codegym.model.dto.UserDetailDTO;
import com.codegym.model.dto.UserDTO;
import com.codegym.model.dto.UserProfileUpdateDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface InfoUserRepository extends JpaRepository<InfoUser, Long> {


    @Query("SELECT new com.codegym.model.dto.UserDTO(i.user.id, i.user.username, i.user.date, i.avatar, i.fullName, i.status) FROM InfoUser i")

    List<UserDTO> findAllUsers();
    @Modifying
    @Transactional
    @Query("UPDATE InfoUser i SET i.status = 'Lock' WHERE i.user.id = :id")
    void lockUserById(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE InfoUser i SET i.status = 'Active' WHERE i.user.id = :id")
    void unlockUserById(Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM InfoUser i WHERE i.user.id = :id")
    void deleteUserById(Long id);

    @Query("SELECT new com.codegym.model.dto.UserDetailDTO(i.user.id, i.user.username, i.user.email, i.avatar, i.user.date, i.fullName, i.address, i.phonenumber, i.status) FROM InfoUser i WHERE i.user.id = :id")
    UserDetailDTO findUserDetailById(Long id);

    @Query("SELECT new com.codegym.model.dto.UserProfileUpdateDTO(i.user.id, i.user.username, i.user.email, i.avatar, i.fullName, i.address, i.phonenumber) FROM InfoUser i WHERE i.user.id = :id")
    UserProfileUpdateDTO findInfoUserById(Long id);

    @Query("SELECT new com.codegym.model.dto.UserDetailDTO(i.user.id, i.user.username, i.user.email, i.avatar, i.user.date, i.fullName, i.address, i.phonenumber, i.status) " +
            "FROM InfoUser i " +
            "WHERE i.user.username LIKE %:username%")
    List<UserDetailDTO> searchUsers(@Param("username") String username);

}


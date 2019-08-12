package com.sunyt.loginToken.dao;

import com.sunyt.loginToken.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;

@Repository
public interface UserDao extends JpaRepository<User, Id> {

    User findByUsernameAndPassword(String userName, String password);
}

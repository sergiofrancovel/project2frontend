package com.revature.dao;

import com.revature.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("from User u where u.email = :email")
    User getUserByEmail(@Param("email") String email);

    @Modifying
    @Query("update User u  set u.email = :email where u.id = :userId")
    void updateEmail(@Param("userId") Integer userId, @Param("email") String email);
}
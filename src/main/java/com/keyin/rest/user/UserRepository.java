package com.keyin.rest.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User, Long>{

    User findByEmail(String email);

    // Find users by user tag //
    List<User> findByUserTag(String userTag);

    // Find users by user tag (case-insensitive) //
    List<User> findByUserTagIgnoreCase(String userTag);

}
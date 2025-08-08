package com.keyin.rest.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User, Long>{

    User findByEmail(String email);


    // Queries for preferred tags //

    @Query("SELECT u FROM User u JOIN u.preferredTags t WHERE t = :tag")
    List<User> findByUserTag(@Param("tag") String preferredTag);

    @Query("SELECT u FROM User u JOIN u.preferredTags t WHERE LOWER(t) = LOWER(:tag)")
    List<User> findByUserTagIgnoreCase(@Param("tag") String preferredTag);

    @Query("SELECT DISTINCT t FROM User u JOIN u.preferredTags t")
    List<String> findAllDistinctTags();
}
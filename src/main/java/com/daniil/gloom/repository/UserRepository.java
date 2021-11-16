package com.daniil.gloom.repository;

import com.daniil.gloom.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Modifying
    @Query("UPDATE User SET username=?1, password=?2, email=?3 WHERE id=?4")
    void update(String username, String password, String email, long id);

}

package com.wordrace.repository;

import com.wordrace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByNickName(String nickName);

    Optional<User> findUserByEmail(String email);


}

package com.krishan.school.timetable.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishan.school.timetable.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
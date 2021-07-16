package com.hak.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hak.blog.model.User;

//자동으로 bean에 등록됨
public interface UserRepository extends JpaRepository<User, Integer> {

}

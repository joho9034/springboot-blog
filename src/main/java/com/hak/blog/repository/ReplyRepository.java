package com.hak.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hak.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

}

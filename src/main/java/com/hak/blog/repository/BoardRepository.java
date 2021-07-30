package com.hak.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hak.blog.model.Board;

//자동으로 bean에 등록됨
public interface BoardRepository extends JpaRepository<Board, Integer> {

}
package com.hak.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hak.blog.model.Board;
import com.hak.blog.model.Reply;
import com.hak.blog.model.User;
import com.hak.blog.repository.BoardRepository;
import com.hak.blog.repository.ReplyRepository;

@Service
public class BoardService {

	private BoardRepository boardRepository;
	
	private ReplyRepository replyRepository;
	
	public BoardService(BoardRepository boardRepository, ReplyRepository replyRepository) {
		this.boardRepository = boardRepository;
		this.replyRepository = replyRepository;
	}
	
	@Transactional
	public void save(Board board, User user) {
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	@Transactional(readOnly =true)
	public Page<Board> findAll(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly =true)
	public Board detail(int id) {
		return boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을 수 없습니다.");
		});
	}
	
	@Transactional
	public void delete(int id) {
//		System.out.println("############## ID: " + id);
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void update(int id, Board reqBoard) {
//		System.out.println("############## ID: " + id);
		Board board = boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 찾기 실패: 아이디를 찾을 수 없습니다.");
		}); //영속화 연결
		board.setTitle(reqBoard.getTitle());
		board.setContent(reqBoard.getContent());
		//해당 함수 종료 시(Service가 종료될 때) 트랜잭션이 종료됨. 이 때 더티체킹 - 자동업데이트(db flush 발생)가 됨.
	}
	
	@Transactional
	public void replySave(int boardId, User user, Reply requestReply) {
		Board board = boardRepository.findById(boardId).orElseThrow(() -> {
			return new IllegalArgumentException("댓글 쓰기 살패: 게시글 id를 찾을 수 없습니다.");
		});
		
		requestReply.setUser(user);
		requestReply.setBoard(board);
		
		replyRepository.save(requestReply);
	}
	
	@Transactional
	public void replyDelete(int replyId) {
		replyRepository.deleteById(replyId);
	}
	
}

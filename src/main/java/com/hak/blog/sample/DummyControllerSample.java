package com.hak.blog.sample;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hak.blog.model.RoleType;
import com.hak.blog.model.User;
import com.hak.blog.repository.UserRepository;

@RestController
public class DummyControllerSample {

	private UserRepository userRepository;
	
	public DummyControllerSample(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
	}
	
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction=Sort.Direction.DESC) Pageable pageable) {
		Page<User> pagingUser = userRepository.findAll(pageable);
		List<User> users = pagingUser.getContent();
		return users;
	}
	
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
//		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
//			@Override
//			public IllegalArgumentException get() {
//				return new IllegalArgumentException("해당 유저는 없습니다. id: " + id);
//			}
//		});
		
		User user = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당 유저는 없습니다. id: " + id);
		});

		return user;
	}
	
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("username: " + user.getUsername());
		System.out.println("password: " + user.getPassword());
		System.out.println("email: " + user.getEmail());
		user.setRole(RoleType.USER);
		userRepository.save(user);
		
		return "회원가입이 완료되었습니다.";
	}
	
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User update(@PathVariable int id, @RequestBody User reqUser) {
		System.out.println("id: " + id);
		System.out.println("password: " + reqUser.getPassword());
		System.out.println("email: " + reqUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당 유저는 없습니다. id: " + id);
		});
		user.setPassword(reqUser.getPassword());
		user.setEmail(reqUser.getEmail());
//		userRepository.save(reqUser);
		return user;
	}
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
			return "삭제 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		return "삭제되었습니다. id: " + id;
	}
	
}

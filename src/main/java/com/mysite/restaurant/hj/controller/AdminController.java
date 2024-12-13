package com.mysite.restaurant.hj.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.restaurant.hj.dto.UserDTO;
import com.mysite.restaurant.hj.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class AdminController {

	private final UserService userService;
	
//	회원 관리
	@GetMapping("/membership")
	public ResponseEntity<List<UserDTO>> getMembers(@RequestParam(value = "keyword", required = false) String keyword) {
		List<UserDTO> members;
		if (keyword == null || keyword.isEmpty()) {
			members = userService.getAllMembers();
		} else {
			members = userService.searchMembers(keyword);
		}
		return new ResponseEntity<>(members, HttpStatus.OK);
	}
	
//	유저 권한 관리
	@PutMapping("/membership/{userId}")
	public ResponseEntity<UserDTO> updateMember(@PathVariable("userId") Long userId, @RequestBody UserDTO user) {
		UserDTO updateMember = userService.updateMemberType(userId, user);
		return new ResponseEntity<>(updateMember, HttpStatus.OK);
	}
	
//	회원 삭제
	@DeleteMapping("/membership/{userId}")
	public ResponseEntity<Void> deleteMember(@PathVariable("userId") Long userId) {
		userService.deleteMemberById(userId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

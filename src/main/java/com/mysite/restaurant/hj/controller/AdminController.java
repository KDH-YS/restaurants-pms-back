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
	public ResponseEntity<List<UserDTO>> getMembers(@RequestParam(required = false) String keyword) {
		List<UserDTO> members;
		if (keyword == null || keyword.isEmpty()) {
			members = userService.getAllMembers(); // getAllMemgers 메서드 추가 필요
		} else {
			members = userService.searchMembers(keyword); // searchMembers 메서드 추가 필요
		}
		return new ResponseEntity<>(members, HttpStatus.OK);
	}
	
//	유저 권한 관리
	@PutMapping("/membership/{id}")
	public ResponseEntity<UserDTO> updateMember(@PathVariable Long id, @RequestBody UserDTO user) {
		UserDTO updateMember = userService.updateMemberType(id, user); // 서비스에 updateMemberType 메서드 추가
		return new ResponseEntity<>(updateMember, HttpStatus.OK);
	}
	
//	회원 삭제
	@DeleteMapping("/membership/{id}")
	public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
		userService.deleteMemberById(id); // 서비스에 deletMemberById 메서드 추가
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

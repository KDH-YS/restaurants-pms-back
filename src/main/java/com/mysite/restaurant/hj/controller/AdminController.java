package com.mysite.restaurant.hj.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.mysite.restaurant.hj.dto.UserDTO;
import com.mysite.restaurant.hj.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

	private final AdminService adminService;
	
//	회원 관리
	@GetMapping("/membership")
	public ResponseEntity<List<UserDTO>> getMembers(@RequestParam(value = "keyword", required = false) String keyword) {
		List<UserDTO> members;
		if (keyword == null || keyword.isEmpty()) {
			members = adminService.getAllMembers();
		} else {
			members = adminService.searchMembers(keyword);
		}
		return new ResponseEntity<>(members, HttpStatus.OK);
	}
	
//	유저 권한 관리
	@PutMapping("/membership/{userId}")
	public ResponseEntity<UserDTO> updateMember(@PathVariable("userId") Long userId, @RequestBody UserDTO user) {
		UserDTO updateMember = adminService.updateMemberType(userId, user);
		return new ResponseEntity<>(updateMember, HttpStatus.OK);
	}
	
//	회원 삭제
	@DeleteMapping("/membership/{userId}")
	public ResponseEntity<Void> deleteMember(@PathVariable("userId") Long userId) {
		adminService.deleteMemberById(userId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

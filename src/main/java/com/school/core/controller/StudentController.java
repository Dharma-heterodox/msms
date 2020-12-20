package com.school.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.school.core.dto.AuthorizeDto;
import com.school.core.dto.ResponseObj;
import com.school.core.dto.StudentDto;
import com.school.core.service.StudentService;
import com.school.core.service.UserRequestService;

@RestController
@RequestMapping("/{schoolId}/student")
public class StudentController {

	@Autowired
	StudentService studentService;
	
	@Autowired
	UserRequestService userReqService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public StudentDto createStudent(@PathVariable("schoolId") Long schoolId,
			@RequestBody StudentDto studentDto)throws Exception {
		return studentService.createStudent(schoolId, studentDto);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<StudentDto> getAllStudentBySchoolId(@PathVariable("schoolId") Long schoolId) {
		return studentService.getAllStudentBySchoolId(schoolId);
	}
	
	@PostMapping(value="/upload")
	@ResponseStatus(HttpStatus.OK)
	public boolean upload(@PathVariable("schoolId") Long schoolId, @RequestParam("file") MultipartFile file) throws Exception {
		return studentService.upload(schoolId, file);
	}
	
	@PostMapping(value="/approve")
	@ResponseStatus(HttpStatus.OK)
	public int approve(@PathVariable("schoolId") Long schoolId, @RequestBody AuthorizeDto dto)throws Exception{
		return userReqService.createStudentParentAcc(dto.getIds(), schoolId);
	}
}

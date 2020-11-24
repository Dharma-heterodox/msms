package com.school.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ResponseObj createStudent(@PathVariable("schoolId") Long schoolId,
			@RequestBody StudentDto studentDto)throws Exception {
		return new ResponseObj(studentService.createStudent(schoolId, studentDto),HttpStatus.OK);
	}

	@GetMapping
	public ResponseObj getAllStudentBySchoolId(@PathVariable("schoolId") Long schoolId) {
		return new ResponseObj(studentService.getAllStudentBySchoolId(schoolId),HttpStatus.OK);
	}
	
	@PostMapping(value="/upload")
	public ResponseObj upload(@PathVariable("schoolId") Long schoolId, @RequestParam("file") MultipartFile file) throws Exception {
		return new ResponseObj(studentService.upload(schoolId, file),HttpStatus.OK);
	}
	
	@PostMapping(value="/approve")
	public ResponseObj approve(@PathVariable("schoolId") Long schoolId, @RequestBody AuthorizeDto dto)throws Exception{
		return new ResponseObj(userReqService.createStudentParentAcc(dto.getIds(), schoolId),HttpStatus.OK);
	}
}

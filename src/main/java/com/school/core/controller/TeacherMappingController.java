package com.school.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.school.core.dto.ResponseObj;
import com.school.core.dto.TeacherMappingDto;
import com.school.core.service.TeacherMappingService;

@RestController
@RequestMapping("/school/{schoolId}/teacherMapping")
public class TeacherMappingController {

	@Autowired
	TeacherMappingService teacherMappingService;
	
	@PostMapping
	public ResponseObj createTeacherMapping(@PathVariable("schoolId") Long schoolId,
			@RequestBody TeacherMappingDto teacherMappingDto) {
		return new ResponseObj(teacherMappingService.createTeacherMapping(schoolId, teacherMappingDto),HttpStatus.OK);
	}

	@GetMapping
	public ResponseObj getAllTeacherMappingBySchoolId(@PathVariable("schoolId") Long schoolId) {
		return new ResponseObj(teacherMappingService.getAllTeacherMappingBySchoolId(schoolId),HttpStatus.OK);
	}
	
	@GetMapping(value="/student/{studentId}")
	public ResponseObj getAllTeacherMappingBySchoolId(@PathVariable("schoolId") Long schoolId, @PathVariable("studentId") Long studentId) {
		return new ResponseObj(teacherMappingService.getAllTeacherMappingByStudentId(schoolId, studentId, null),HttpStatus.OK);
	}
	
	@GetMapping(value="/student/{studentId}/{academicYear}")
	public ResponseObj getAllTeacherMappingBySchoolId(@PathVariable("schoolId") Long schoolId
			, @PathVariable("studentId") Long studentId, @PathVariable("academicYear") String academicYear) {
		return new ResponseObj(teacherMappingService.getAllTeacherMappingByStudentId(schoolId, studentId, null),HttpStatus.OK);
	}
}

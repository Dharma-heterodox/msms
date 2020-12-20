package com.school.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.school.core.dto.ParentDto;
import com.school.core.dto.ResponseObj;
import com.school.core.dto.StudentDto;
import com.school.core.service.ParentService;

@RestController
@RequestMapping("/parent")
public class ParentController {

	@Autowired
	private ParentService parentService;
	
	@PostMapping(value="/{parentId}/student")
	@ResponseStatus(HttpStatus.OK)
	public ParentDto addStudent(@PathVariable("parentId") Long parentId, @RequestBody StudentDto studentDto) {
		return parentService.addStudent(parentId, studentDto);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/{studentId}", method = RequestMethod.GET)
	public List<ParentDto> getAllParentByStudentId(@PathVariable("schoolId") Long schoolId, @PathVariable("studentId") Long studentId) {
		return parentService.getAllParentByStudentId(studentId);
	}
}

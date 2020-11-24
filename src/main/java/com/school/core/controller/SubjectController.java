package com.school.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.core.dto.ResponseObj;
import com.school.core.dto.SubjectDto;
import com.school.core.service.SubjectService;

@RestController
@RequestMapping("/school/{schoolId}/subject")
public class SubjectController {

	@Autowired
	SubjectService subjectService;
	
	@PostMapping
	public ResponseObj createSubject(@PathVariable("schoolId") Long schoolId,
			@RequestBody SubjectDto subjectDto) {
		return new ResponseObj(subjectService.createSubject(schoolId, subjectDto),HttpStatus.OK);
	}

	@GetMapping
	public ResponseObj getAllSubjectBySchoolId(@PathVariable("schoolId") Long schoolId) {
		return new ResponseObj(subjectService.getAllSubjectsBySchoolId(schoolId),HttpStatus.OK);
	}
}

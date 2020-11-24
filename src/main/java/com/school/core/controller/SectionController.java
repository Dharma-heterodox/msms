package com.school.core.controller;

import javax.validation.Valid;

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
import com.school.core.dto.SectionDto;
import com.school.core.service.SectionService;

@RestController
@RequestMapping("/school/{schoolId}/section")
public class SectionController {

	@Autowired
	private SectionService sectionService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseObj createSection(@PathVariable("schoolId") Long schoolId,
			@Valid @RequestBody SectionDto sectionDto) {
		return new ResponseObj(sectionService.createSection(schoolId, sectionDto),HttpStatus.OK);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseObj getAllSectionBySchoolId(@PathVariable("schoolId") Long schoolId) {
		return new ResponseObj(sectionService.getAllSectionBySchoolId(schoolId),HttpStatus.OK);
	}
}
